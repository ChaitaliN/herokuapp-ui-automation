package context;

import org.openqa.selenium.WebDriver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    public static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();

    public Properties load (String propFileName){

        InputStream inputstream;
        Properties prop = new Properties();
        try {
            inputstream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if(inputstream!=null){
                prop.load(inputstream);
                inputstream.close();
            } else {
                throw new FileNotFoundException("Property file " +propFileName + " "+ "does not exist");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public static WebDriver getDriver(){
        return threadLocalDriver.get();
    }
    public static void setDriver(WebDriver driver){
        threadLocalDriver.set(driver);
    }
}
