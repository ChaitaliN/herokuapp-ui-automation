package webpage;

import context.AppContext;
import org.openqa.selenium.WebDriver;

public class Home {
     AppContext appContext;
     WebDriver driver;
    public Home(AppContext appContext) {
        this.appContext = appContext;
        driver = appContext.getDriver();
        driver.get(appContext.config().getProperty("url"));
        System.out.println(driver.getTitle());
    }

}
