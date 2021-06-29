package helpers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.UUID;

public class StringMaker {

    public static String createRandomUuid() {
        return UUID.randomUUID().toString();
    }

    public static String getStackTraceAsString(Exception e) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        return result.toString();
    }
}
