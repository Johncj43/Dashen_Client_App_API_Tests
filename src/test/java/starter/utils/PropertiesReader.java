package starter.utils;

import groovy.beans.PropertyReader;

import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    private PropertiesReader() {
    }

    private static final String PARAMETER_PROPERTIES = "/parameter.properties";

    public static String getParameterProperties(String variable) {
        Properties prop = new Properties();
        {
            try {
                prop.load(PropertyReader.class.getResourceAsStream(PARAMETER_PROPERTIES));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return prop.get(variable).toString();
    }

}
