package tests;

import context.AppContext;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class BaseTest {

    AppContext appContext;
    @Test
    public void setUP(ITestContext context){
        appContext = (AppContext) context.getAttribute("appContext");

    }

}
