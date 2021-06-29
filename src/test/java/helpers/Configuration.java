package helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private static Properties prop = new Properties();

    static {
        try {
            InputStream input = Configuration.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);
            input.close();
        }
        catch (IOException e) {
            throw new ExceptionInInitializerError("Cannot read parameters from file.\n " + StringMaker.getStackTraceAsString(e) );
        }
    }

    public static String getApiUri() { return prop.getProperty("API_URI"); }

}
