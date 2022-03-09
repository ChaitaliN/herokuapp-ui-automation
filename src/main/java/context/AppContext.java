package context;

import org.openqa.selenium.WebDriver;

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
}
