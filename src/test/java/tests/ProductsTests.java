package tests;

import context.AppContext;
import dataProvider.ProductProvider;
import dataset.CreateDataSet;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;
import webpage.Login;
import webpage.Products;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

public class ProductsTests {

    AppContext appContext;
    Logger log;

    @Test(dataProvider = "createProduct", dataProviderClass = ProductProvider.class, description = "Create product")
    public void createProduct(ITestContext context, CreateDataSet data) {

        // Get appContext from TestEvent
        appContext = (AppContext) context.getAttribute("appContext");

        // Define logger
        log = appContext.getLogger(ProductsTests.class);
        log.info("Thread:" + Thread.currentThread().getId() + data.toString());

        // Pre-requisite Login
        Login login = new Login(appContext);
        login.loginWithValidCredentials();

        // Navigate to Products page
        Products products = new Products(appContext);
        products.navigateToNewProductPage();

        if (data.getPositiveTest()) {
            // Verity Product title max length
            Assert.assertEquals(products.verifyProductTitleMaxLength(), "40", "Expected length is not 40");

            // Verity Product sku max length
            Assert.assertEquals(products.verifyProductSkuMaxLength(), "10", "Expected length is not 10");

            // Create Product
            String productCreatedMessage = products.createProduct(data.getProductTitle(), data.getProductSku(), data.getProductDescription());
            Assert.assertEquals(productCreatedMessage, data.getExpectedResult(), "Product is not created");

            // Delete Product
            products.deleteProduct();
        } else {

            // Verify error messages for empty fields
            String errorMessage = products.createProduct(data.getProductTitle(), data.getProductSku(), data.getProductDescription());
            if (data.getProductTitle().isEmpty() || data.getProductSku().isEmpty() || data.getProductDescription().isEmpty()) {
                Assert.assertEquals(errorMessage, data.getExpectedResult(), "Error message doesn't match");
            }
        }

    }

    @Test(dataProvider = "updateProduct",dataProviderClass = ProductProvider.class, description = "Update product")
    public void updateProduct(ITestContext context,CreateDataSet data){

        // Get appContext from TestEvent
        appContext = (AppContext) context.getAttribute("appContext");

        // Define logger
        log = appContext.getLogger(ProductsTests.class);
        log.info("Thread:" + Thread.currentThread().getId() + data.toString());

        // Pre-requisite Login and create product
        Login login = new Login(appContext);
        login.loginWithValidCredentials();

        // Create product
        Products products = new Products(appContext);
        products.navigateToNewProductPage();
        products.createProduct(data.getProductTitle(), data.getProductSku(), data.getProductDescription());

        // Verify Update Product
        // Verify edit product page opened successfully
        Assert.assertTrue(products.clickEditProductButton().contains("Edit Product"),"Edit Product title is missing");

        // Update product details
        String productUpdatedMessage = products.updateProduct(data.getProductTitle(), data.getProductSku(), data.getProductDescription());
        Assert.assertEquals(productUpdatedMessage,data.getExpectedResult(), "Product is not updated successfully");

        // clean up
        products.deleteProduct();
    }

    @Test(dataProvider = "deleteProduct",dataProviderClass = ProductProvider.class, description = "Delete product")
    public void deleteProduct(ITestContext context,CreateDataSet data){

        // Get appContext from TestEvent
        appContext = (AppContext) context.getAttribute("appContext");

        // Define logger
        log = appContext.getLogger(ProductsTests.class);
        log.info("Thread:" + Thread.currentThread().getId() + data.toString());

        // Pre-requisite Login and create product
        Login login = new Login(appContext);
        login.loginWithValidCredentials();

        // Create product
        Products products = new Products(appContext);
        products.navigateToNewProductPage();
        products.createProduct(data.getProductTitle(),data.getProductSku(),data.getProductDescription());

        // Delete product
        products.deleteProduct();
        Assert.assertEquals(products.verifyProductDeletedConfirmationMessage(),data.getExpectedResult(),"Product is not deleted successfully");

    }
    @Test(dataProvider = "listProduct",dataProviderClass = ProductProvider.class, description = "Delete product")
    public void list(ITestContext context,CreateDataSet data){

        // Get appContext from TestEvent
        appContext = (AppContext) context.getAttribute("appContext");

        // Define logger
        log = appContext.getLogger(ProductsTests.class);
        log.info("Thread:" + Thread.currentThread().getId() + data.toString());

        // Pre-requisite Login and create product
        Login login = new Login(appContext);
        login.loginWithValidCredentials();

        // Create product
        Products products = new Products(appContext);
        products.navigateToNewProductPage();
        products.createProduct(data.getProductTitle(),data.getProductSku(),data.getProductDescription());

        // list product
       List <WebElement > matchedProductFromList = products.listProduct(data.getProductTitle());

       // Assert Product sku and description in product list
       Assert.assertTrue(matchedProductFromList.get(0).getText().contains(data.getProductSku()));
       Assert.assertTrue(matchedProductFromList.get(0).getText().contains(data.getProductDescription()));

       // Assert view link on product list
        Assert.assertTrue(products.verifyViewLink().contains(data.getProductTitle()));

        // Assert edit link on product list
        Assert.assertTrue(products.verifyEditLink(data.getProductTitle()).contains("Edit Product"),"Edit link is not working");

        // Assert delete link on product list and delete product as part of clean up
        Assert.assertTrue(products.verifyDeleteLink(data.getProductTitle()).contains("Are you sure you want to delete this?"),"Delete link is not working");
    }

}
