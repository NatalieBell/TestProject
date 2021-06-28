package performers;

import helpers.AsynchronousRequest;
import helpers.Requester;
import jsonschemas.OutputResult;

public class MethodToCheckDataFromFile {

    private static final String endpoint = "/check_data_file";
    private static final String resultEndpoint = "/check/result/";

    public static OutputResult checkFileData(String path) throws Exception {
        AsynchronousRequest request = new AsynchronousRequest();
        request.put("path_to_file_data", path);
        return perform(request);
    }

    private static OutputResult perform(AsynchronousRequest request) throws Exception {
        String response = Requester.asyncRequest(endpoint, resultEndpoint, request);
        return OutputResult.parse(response);
    }
}
