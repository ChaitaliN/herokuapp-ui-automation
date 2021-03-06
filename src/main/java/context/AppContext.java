package context;

import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;
import java.util.Properties;

public class AppContext {

    private String propFileName = "test.config.properties";
    private Properties config;
    private WebDriver driver;

    public AppContext(String env){

        config = new Configuration().load("properties/" +env + "/" +propFileName);
    }

    public Properties config(){
        return config;
    }

    public WebDriver getDriver() {
        return Configuration.getDriver();
    }

    public void setDriver(WebDriver driver) {
        Configuration.setDriver(driver);
    }

    public Logger getLogger(Class pClass) {
        return Logger.getLogger(pClass);
    }
}
