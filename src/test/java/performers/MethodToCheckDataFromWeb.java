package performers;

import helpers.AsynchronousRequest;
import helpers.Requester;
import jsonschemas.OutputResult;

public class MethodToCheckDataFromWeb {

    private static final String endpoint = "/check_data_web";
    private static final String resultEndpoint = "/check/result/";

    public static OutputResult checkWebData(String path) throws Exception {
        AsynchronousRequest request = new AsynchronousRequest();
        request.put("path_to_web_data", path);
        return perform(request);
    }

    private static OutputResult perform(AsynchronousRequest request) throws Exception {
        String response = Requester.asyncRequest(endpoint, resultEndpoint, request);
        return OutputResult.parse(response);
    }
}
