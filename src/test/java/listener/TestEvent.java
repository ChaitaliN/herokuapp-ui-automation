package listener;

import context.AppContext;
import context.Configuration;
import context.LocalWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestEvent implements ITestListener {

    AppContext appContext;
    String env,browser;

    @Override
    public void onTestStart(ITestResult result) {
        // Get env details from xml file
        env = result.getMethod().getXmlTest().getLocalParameters().get("env");

        // Get browser name from xml file
        browser = result.getMethod().getXmlTest().getLocalParameters().get("browser");
        appContext = new AppContext(env);

        // set browser
        Configuration.setDriver(LocalWebDriver.launchDriver(browser));

        // Pass context to test method
        result.getTestContext().setAttribute("appContext",appContext);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (appContext.getDriver() != null) {
            appContext.getDriver();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (appContext.getDriver() != null) {
            appContext.getDriver().quit();
        }
    }
}
