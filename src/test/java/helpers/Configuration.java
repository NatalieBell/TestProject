package helpers;

import java.util.Properties;

public class Configuration {

    private static Properties prop = new Properties();


    public static String getApiUri() { return prop.getProperty("API_URI"); }

}
