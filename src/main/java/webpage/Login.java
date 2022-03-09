package webpage;

import context.AppContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login extends Home {
    WebDriver driver;

    public Login(AppContext appContext){
        super(appContext);
        this.driver = appContext.getDriver();
        PageFactory.initElements(driver,this);
    }

    @FindBy(id="admin_user_email")
    WebElement email;

    @FindBy(id="admin_user_password")
    WebElement password;

    @FindBy(xpath="//input[@value='Login']")
    WebElement loginButton;

    @FindBy(css= ".flash.flash_notice")
    WebElement signedInText;

    String emailValue = appContext.config().getProperty("email");
    String passwordValue = appContext.config().getProperty("password");

    public void enterEmail(){
        email.clear();
        email.sendKeys(emailValue);
    }

    public void enterPassword(){
        password.clear();
        password.sendKeys(passwordValue);
    }

    public void clickLoginButton(){
        loginButton.submit();
    }

    public void loginWithValidCredentials(){
        enterEmail();
        enterPassword();
        clickLoginButton();
    }

    public String verifyLogin(){
        return signedInText.getText();
    }
}
