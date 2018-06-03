package cashregister.modules;

import cashregister.dao.interfaces.IProductDefinitionDao;
import cashregister.model.ProductDefinition;
import cashregister.model.ProductForSale;
import cashregister.modules.interfaces.IProductsListModule;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductListModuleTest {

    @Mock
    IProductsListModule productsListModule;

    @Mock
    IProductDefinitionDao productDefinitionDao;

    public void setUp(){
        productsListModule = new ProductsListModule(productDefinitionDao);
    }

    @Test
    public void addProductMethodIsShoppingListUpdatedCorrectlyWhenAddingProductsWithDifferentProductDefinitionTest(){
        final String barcode1 = "barcode1";
        final String barcode2 = "barcode2";
        ProductDefinition productDefinition1 = new ProductDefinition();
        productDefinition1.setPrice(110.4);
        productDefinition1.setName("product1");
        productDefinition1.setId(1);
        ProductDefinition productDefinition2 = new ProductDefinition();
        productDefinition2.setPrice(30);
        productDefinition2.setName("product2");
        productDefinition2.setId(2);
        Mockito.when(productDefinitionDao.getByBarcode(barcode1)).thenReturn(productDefinition1);
        Mockito.when(productDefinitionDao.getByBarcode(barcode2)).thenReturn(productDefinition2);

        ProductsListModule test = new ProductsListModule(productDefinitionDao);

        Assert.assertTrue(test.getShoppingList().size() == 0);
        test.addProduct(barcode1);
        Assert.assertTrue(test.getShoppingList().size() == 1);
        test.addProduct(barcode2);
        Assert.assertTrue(test.getShoppingList().size() == 2);
        Assert.assertTrue(test.getShoppingList().get(0).getPrice() == 110.4);
        Assert.assertTrue(test.getShoppingList().get(1).getName().equals("product2"));
    }

    @Test
    public void addProductMethodIsShoppingListUpdatedCorrectlyWhenAddingProductsWithTheSameIdTest(){
        final String barcode1 = "barcode1";
        final String barcode2 = "barcode2";
        ProductDefinition productDefinition1 = new ProductDefinition();
        productDefinition1.setPrice(110.4);
        productDefinition1.setName("product1");
        productDefinition1.setId(1);
        ProductDefinition productDefinition2 = new ProductDefinition();
        productDefinition2.setPrice(30);
        productDefinition2.setName("product2");
        productDefinition2.setId(1);
        Mockito.when(productDefinitionDao.getByBarcode(barcode1)).thenReturn(productDefinition1);
        Mockito.when(productDefinitionDao.getByBarcode(barcode2)).thenReturn(productDefinition2);

        ProductsListModule test = new ProductsListModule(productDefinitionDao);

        Assert.assertTrue(test.getShoppingList().size() == 0);
        test.addProduct(barcode1);
        Assert.assertTrue(test.getShoppingList().size() == 1);
        Assert.assertTrue(test.getShoppingList().get(0).getQuantity() == 1.0);
        test.addProduct(barcode2);
        Assert.assertTrue(test.getShoppingList().size() == 1);
        Assert.assertTrue(test.getShoppingList().get(0).getQuantity() == 2.0);
    }

    @Test
    public void getTotalPriceMethodIsTotalPriceBeingCalculatedCorrectly(){
        final String barcode1 = "barcode1";
        final String barcode2 = "barcode2";
        final String barcode3 = "barcode3";
        ProductDefinition productDefinition1 = new ProductDefinition();
        productDefinition1.setPrice(110.4);
        productDefinition1.setName("product1");
        productDefinition1.setId(1);
        productDefinition1.setCountable(true);
        ProductDefinition productDefinition2 = new ProductDefinition();
        productDefinition2.setPrice(30);
        productDefinition2.setName("product2");
        productDefinition2.setId(2);
        ProductDefinition productDefinition3 = new ProductDefinition();
        productDefinition3.setId(3);
        productDefinition3.setName("product3");
        productDefinition3.setPrice(123.3);
        Mockito.when(productDefinitionDao.getByBarcode(barcode1)).thenReturn(productDefinition1);
        Mockito.when(productDefinitionDao.getByBarcode(barcode2)).thenReturn(productDefinition2);
        Mockito.when(productDefinitionDao.getByBarcode(barcode3)).thenReturn(productDefinition3);

        ProductsListModule test = new ProductsListModule(productDefinitionDao);

        test.addProduct(barcode1);
        test.addProduct(barcode2);

        double returnedPrice = test.getTotalPrice();
        Assert.assertTrue(returnedPrice == (productDefinition1.getPrice() + productDefinition2.getPrice()));

        test.addProduct(barcode3);
        returnedPrice = test.getTotalPrice();
        Assert.assertTrue(returnedPrice == (productDefinition1.getPrice() + productDefinition2.getPrice() + productDefinition3.getPrice()));
    }


}