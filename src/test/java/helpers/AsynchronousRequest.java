package helpers;

import java.util.HashMap;

public class AsynchronousRequest extends HashMap<String, Object> {

    public AsynchronousRequest() {
        put("query_id", StringMaker.createRandomUuid());
    }

    public String getQueryId() {
        return get("query_id").toString();
    }
}
