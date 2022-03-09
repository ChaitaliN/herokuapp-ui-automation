package dataProvider;

import dataset.CreateDataSet;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.Iterator;

public class ProductProvider extends CSVProvider {

    @DataProvider(name = "createProduct", parallel = true)
    public Iterator<Object> create(ITestContext context) throws IOException {
        return toObject(context, "/products/createProduct/create.csv", CreateDataSet.class);
    }

    @DataProvider(name = "updateProduct", parallel = true)
    public Iterator<Object> update(ITestContext context) throws IOException {
        return toObject(context, "/products/updateProduct/update.csv", CreateDataSet.class);
    }

    @DataProvider(name = "deleteProduct", parallel = true)
    public Iterator<Object> delete(ITestContext context) throws IOException {
        return toObject(context, "/products/deleteProduct/delete.csv", CreateDataSet.class);
    }

    @DataProvider(name = "listProduct", parallel = true)
    public Iterator<Object> list(ITestContext context) throws IOException {
        return toObject(context, "/products/listProduct/list.csv", CreateDataSet.class);
    }
}
