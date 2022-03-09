package tests;

import context.AppContext;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import webpage.Home;
import webpage.Login;

public class LoginTests {
    AppContext appContext;

    @Test()
    public void Login(ITestContext context){
        appContext = (AppContext) context.getAttribute("appContext");
        Login login = new Login(appContext);
        login.loginWithValidCredentials();
        Assert.assertEquals("Signed in successfully.",login.verifyLogin(),"Unsuccessful login");
    }

}
