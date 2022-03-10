package context;

import org.openqa.selenium.WebDriver;

import java.util.Properties;

public interface Orchestrator {
    public Properties config();

    public WebDriver getDriver();

    public void setDriver(WebDriver driver);
}
