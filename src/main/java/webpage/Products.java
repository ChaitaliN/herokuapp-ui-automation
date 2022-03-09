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

    @FindBy(linkText = "Create one")
    WebElement createProductLink;

    @FindBy(linkText = "Products")
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

    @FindBy(xpath = "//tr[1]//td//a[@class='resource_id_link']")
    WebElement productIdLink;

    @FindBy(xpath = "//tr[1]//td[@class='col col-title']")
    WebElement productColumnTitle;


    @FindBy(xpath = "//tr[1]//td[@class='col col-sku']")
    WebElement productColumnSku;

    @FindBy(xpath = "//tr[1]//td[@class='col col-description']")
    WebElement productColumnDescription;

    @FindBy(xpath = "//tr[1]//td[@class='col col-created_at']")
    WebElement productColumnCreatedAt;

    @FindBy(xpath = "//tr[1]//td[@class='col col-updated_at']")
    WebElement productColumnUpdatedAt;

    @FindBy(xpath = "//h3 [contains(text(),'Product Details')]")
    WebElement productDetailsText;

    String randomString = RandomStringUtils.random(5,true, true);


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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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
    if(driver.switchTo().alert().getText().contains("Are you sure you want to delete this?")) {
        driver.switchTo().alert().accept();
    }
    }

    public String verifyProductDeletedConfirmationMessage(){
        return productDeletedMessage.getText();
    }

    List<WebElement> matchedRowFromList = null;
    WebElement viewLink;
    WebElement editLink;
    WebElement deleteLink;

    public List<WebElement> listProduct(String title){
        productsText.click();
        List<WebElement> listSize= driver.findElements(By.xpath("//tbody//tr"));
        System.out.println("size" + listSize.size());
       String beforeXpath = "//tbody//tr[";
       String afterXpath= "]//td[@class='col col-title']";
       for (int i = 1; i <= listSize.size() ; i++) {
           System.out.println("in loop");
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

    public String verifyEditLink(){
        productsText.click();
        editLink.click();
        return driver.getTitle();
    }

    public String verifyDeleteLink(){
        productsText.click();
        deleteLink.click();
        return driver.switchTo().alert().getText();
    }
}
