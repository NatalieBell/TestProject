package helpers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.awaitility.core.ConditionTimeoutException;

import java.io.IOException;
import java.util.concurrent.Callable;

import static io.restassured.RestAssured.given;
import static io.restassured.config.HttpClientConfig.httpClientConfig;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class Requester {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Requester.class);

    static {
        RestAssured.baseURI = Configuration.getApiUri();
        RestAssured.config = RestAssured.config().httpClient(httpClientConfig().reuseHttpClientInstance());
    }

    private static Response postRequest(AsynchronousRequest request, String address) {
        return given()
                .contentType("application/json")
                .body(request)
                .when()
                .post(address)
                .then()
                .extract().response();
    }

    private static Response getResponse(String address) {
        String statusAddress = address.replace("result", "status");
        logger.debug("Status address: " + RestAssured.baseURI + statusAddress);

        await().with()
                .pollDelay(200, MILLISECONDS)
                .and().pollInterval(500, MILLISECONDS)
                .and().timeout(30, SECONDS)
                .await().until(processed(statusAddress));

        logger.debug("Response address: " + RestAssured.baseURI + address);

        return given()
                .contentType("application/json")
                .when()
                .get(address);
    }

    private static Callable<Boolean> processed(String address) {
        return () -> {
            Response response = given()
                    .contentType("application/json")
                    .when()
                    .get(address);
            logger.debug("Checking status: " + response.body().asString());
            return (response.getBody().jsonPath().getInt("status") != 1);
        };
    }

    public static String asyncRequest(String endpointRequest, String endpointResponse, AsynchronousRequest body) throws Exception, IOException {
        logger.debug("Request address: " + RestAssured.baseURI + endpointRequest);
        logger.debug("Request body: " + body);

        try {
            Response postResponse = postRequest(body, endpointRequest);
            String responseBody = postResponse.asString();
            logger.debug("Response body: " + responseBody);

            if (postResponse.getStatusCode() != 200) {
                logger.debug("Request was rejected: " + responseBody);
                throw new Exception(responseBody);
            }

            String response = getResponse(endpointResponse + body.getQueryId()).asString();
            logger.debug("Response body: " + response);
            return response;
        }
        catch (ConditionTimeoutException e) {
            throw new ConditionTimeoutException(RestAssured.baseURI + endpointResponse);
        }
    }

    public static String syncRequest(String endpoint) throws IOException {
        logger.debug("Request address: " + RestAssured.baseURI + endpoint);

        String response = given()
                .contentType("application/json")
                .when()
                .get(endpoint)
                .asString();

        logger.debug("Response body: " + response);

        return response;
    }
}
