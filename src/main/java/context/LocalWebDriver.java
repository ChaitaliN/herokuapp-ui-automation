package context;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LocalWebDriver {


    public static WebDriver launchDriver(String browserName) {
        switch (browserName.toLowerCase()){
            case "chrome":
                // Install chrome if it isn't already
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
           default:
                // Install chrome if it isn't already
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
        }
    }
}
