package webpage;

import context.AppContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Products {

    WebDriver driver;

    public Products(AppContext appContext) {
        this.driver = appContext.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[normalize-space()='Products']")
    WebElement productsText;

    @FindBy(id = "page_title")
    WebElement productPageTitle;

    @FindBy(xpath = "//input[@id='product_title']")
    WebElement productTitle;

    @FindBy(id = "product_sku")
    WebElement productSku;

    @FindBy(id = "product_description")
    WebElement productDescription;

    @FindBy(id = "product_submit_action")
    WebElement createProductButton;


    @FindBy(xpath = "//a[normalize-space()='New Product']")
    WebElement newProductButton;

    @FindBy(xpath = "//div[contains(text(),'Product was successfully created.')]")
    WebElement productCreatedMessage;

    @FindBy(xpath = "//div[contains(text(),'Product was successfully updated.')]")
    WebElement productUpdatedMessage;

    @FindBy(xpath = "//div[contains(text(),'Product was successfully destroyed.')]")
    WebElement productDeletedMessage;

    @FindBy(xpath = "//li[@id='product_title_input']//p")
    WebElement productTitleErrorMsg;

    @FindBy(xpath = "//li[@id='product_sku_input']//p")
    WebElement productSkuErrorMsg;

    @FindBy(xpath = "//li[@id='product_description_input']//p")
    WebElement productDescriptionErrorMsg;

    @FindBy(linkText = "Edit Product")
    WebElement editProductButton;

    @FindBy(xpath = "//a[@data-method='delete']")
    WebElement deleteProductButton;

    @FindBy(xpath = "//h3 [contains(text(),'Product Details')]")
    WebElement productDetailsText;

    String randomString = RandomStringUtils.random(5,true, true);
    List<WebElement> matchedRowFromList = null;
    WebElement viewLink;
    WebElement editLink;
    WebElement deleteLink;
    List<WebElement> listSize;
    String beforeXpath;
    String afterXpath;

    public void navigateToNewProductPage() {
        productsText.click();
        clickNewProduct();
    }

    public void clickNewProduct() {
        newProductButton.click();
    }

    public void enterProductTitle(String title) {
        productTitle.clear();
        productTitle.sendKeys(title);
    }

    public void enterProductSku(String sku) {
        productSku.clear();
        productSku.sendKeys(sku);
    }

    public void enterProductDescription(String description) {
        productDescription.clear();
        productDescription.sendKeys(description);
    }

    public String verifyProductTitleMaxLength() {
        return productTitle.getAttribute("maxlength");
    }

    public String verifyProductSkuMaxLength() {
        return productSku.getAttribute("maxlength");
    }

    public void waitForElementVisibility(By element){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public String createProduct(String title, String sku, String description) {

        if (!title.isEmpty() && !sku.isEmpty() && !description.isEmpty()) {
            enterProductTitle(title+randomString);
            enterProductDescription(description+randomString);
            enterProductSku(sku+randomString);
            createProductButton.submit();
            return productCreatedMessage.getText();
        } else {
            enterProductTitle(title);
            enterProductDescription(description);
            enterProductSku(sku);
            createProductButton.submit();
            return verifyErrorMessages(title, sku, description);
        }
    }

    public String verifyErrorMessages(String title, String sku, String description) {

        if (title.isEmpty()) {
            return productTitleErrorMsg.getText();
        } else if (sku.isEmpty()) {
           return productSkuErrorMsg.getText();
        } else
        return productDescriptionErrorMsg.getText();
    }

    public String clickEditProductButton(){
        editProductButton.click();
        return driver.getTitle();
}

    public String updateProduct(String title, String description, String sku){
        enterProductTitle(title+randomString);
        enterProductDescription(description+randomString);
        enterProductSku(randomString+sku);
        createProductButton.submit();
        return productUpdatedMessage.getText();
    }

    public void deleteProduct(){
        waitForElementVisibility(By.xpath("//a[@data-method='delete']"));
    deleteProductButton.click();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.alertIsPresent());
    if(driver.switchTo().alert().getText().contains("Are you sure you want to delete this?")) {
        driver.switchTo().alert().accept();
    }
    waitForElementVisibility(By.xpath("//div[contains(text(),'Product was successfully destroyed.')]"));

    }

    public String verifyProductDeletedConfirmationMessage(){
        return productDeletedMessage.getText();
    }

    public List<WebElement> listProduct(String title){
        productsText.click();
        listSize = driver.findElements(By.xpath("//tbody//tr"));
        beforeXpath = "//tbody//tr[";
        afterXpath= "]//td[@class='col col-title']";
       for (int i = 1; i <= listSize.size() ; i++) {
           WebElement productTitleFromList = driver.findElement(By.xpath(beforeXpath+i+afterXpath));
            if(productTitleFromList.getText().contains(title)){
                matchedRowFromList = driver.findElements(By.xpath(beforeXpath+i+"]"));
                viewLink= driver.findElement(By.xpath("//tr["+i+"]//a[text()='View']"));
                editLink = driver.findElement(By.xpath("//tr["+i+"]//a[text()='Edit']"));
                deleteLink = driver.findElement(By.xpath("//tr["+i+"]//a[text()='Delete']"));
            }
       }
        return matchedRowFromList;
    }

    public String verifyViewLink(){
        viewLink.click();
        productDetailsText.isDisplayed();
        return productPageTitle.getText();
    }

    public String verifyEditLink(String title){
        productsText.click();
        waitForElementVisibility(By.id("page_title"));
        for (int i = 1; i <= listSize.size() ; i++) {
            WebElement productTitleFromList = driver.findElement(By.xpath(beforeXpath+i+afterXpath));
            if(productTitleFromList.getText().contains(title)){
                editLink = driver.findElement(By.xpath("//tr["+i+"]//a[text()='Edit']"));
            }
        }
        editLink.click();
        return driver.getTitle();
    }

    public String verifyDeleteLink(String title){

        productsText.click();
        waitForElementVisibility(By.id("page_title"));
        for (int i = 1; i <= listSize.size() ; i++) {
            WebElement productTitleFromList = driver.findElement(By.xpath(beforeXpath+i+afterXpath));
            if(productTitleFromList.getText().contains(title)){
                deleteLink = driver.findElement(By.xpath("//tr["+i+"]//a[text()='Delete']"));
            }
        }
        deleteLink.click();

        String deletePopupMessage = this.driver.switchTo().alert().getText();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        waitForElementVisibility(By.xpath("//div[contains(text(),'Product was successfully destroyed.')]"));
        return deletePopupMessage;
    }
}
