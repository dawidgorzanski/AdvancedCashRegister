package cashregister.modules;

import cashregister.dao.interfaces.IReceiptDao;
import cashregister.model.*;
import cashregister.modules.interfaces.IProductDefinitionModule;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PaymentModuleTest {

    @Mock
    IReceiptDao receiptDao;

    @Mock
    IProductsListModule productsListModule;

    @Mock
    IProductDefinitionModule productDefinitionModule;

    PaymentModule paymentModule;

    @Before
    public void setUp() {
        paymentModule = new PaymentModule(receiptDao);
    }

    @Test
    public void createSummaryMethodReturnsCorrectReceiptTest() {
        Receipt receipt = new Receipt();
        ProductForSale productForSale1 = new ProductForSale();
        ProductForSale productForSale2 = new ProductForSale();
        List<ProductForSale> productsForSale = new ArrayList<ProductForSale>();
        productsForSale.add(productForSale1);
        productsForSale.add(productForSale2);
        ObservableList<ProductForSale> list = FXCollections.observableArrayList(productsForSale);
        Customer customer1 = new Customer();

        Mockito.when(productsListModule.getShoppingList()).thenReturn(list);
        Mockito.when(productsListModule.getCurrentCustomer()).thenReturn(customer1);

        Receipt testReceipt = paymentModule.createSummary(productsListModule);

        Assert.assertEquals(testReceipt.getProductForSales(), list);
        Assert.assertEquals(testReceipt.getCustomer(), customer1);
    }

    @Test
    public void createSummaryMethodIsSavingReceiptToDatabaseTest() {
        Receipt receipt1 = new Receipt();
        Receipt receipt2 = new Receipt();
        List<Receipt> listOfReceipts = new ArrayList<Receipt>();
        listOfReceipts.add(receipt1);

        ProductForSale productForSale1 = new ProductForSale();
        List<ProductForSale> productsForSale = new ArrayList<ProductForSale>();
        productsForSale.add(productForSale1);
        ObservableList<ProductForSale> list = FXCollections.observableArrayList(productsForSale);
        Customer customer1 = new Customer();
        Mockito.when(productsListModule.getShoppingList()).thenReturn(list);
        Mockito.when(productsListModule.getCurrentCustomer()).thenReturn(customer1);

        Mockito.doAnswer((Answer) invocation -> {
            listOfReceipts.add(receipt2);
            return null;
        }).when(receiptDao).save(Matchers.anyObject());

        Assert.assertTrue(listOfReceipts.size() == 1);
        paymentModule.createSummary(productsListModule);
        Assert.assertTrue(listOfReceipts.size() == 2);
        Assert.assertEquals(listOfReceipts.get(1), receipt2);
    }

    @Test
    public void finalizePaymentMethodIsQuantityBeingDecreasedCorrectlyTest() {
        ProductForSale productForSale1 = new ProductForSale();
        productForSale1.setQuantity(10);
        ProductDefinition productDefinition = new ProductDefinition();
        productDefinition.setQuantity(100);
        productForSale1.setProductDefinition(productDefinition);
        List<ProductForSale> productsForSale = new ArrayList<ProductForSale>();
        productsForSale.add(productForSale1);
        ObservableList<ProductForSale> list = FXCollections.observableArrayList(productsForSale);

        Mockito.when(productsListModule.getShoppingList()).thenReturn(list);

        paymentModule.finalizePayment(productsListModule, productDefinitionModule);

        Assert.assertTrue(productDefinition.getQuantity() == 90);

    }

    @Test
    public void finalizePaymentMethodAllProductsAreBeingDeletedFromProductsListTest() {
        // this stuff is irrelevant in this test, but has to be here to avoid null pointer exception...
        ProductForSale productForSale1 = new ProductForSale();
        productForSale1.setQuantity(10);
        ProductDefinition productDefinition = new ProductDefinition();
        productDefinition.setQuantity(100);
        productForSale1.setProductDefinition(productDefinition);
        ProductForSale productForSale2 = new ProductForSale();
        productForSale1.setQuantity(31);
        ProductDefinition productDefinition2 = new ProductDefinition();
        productDefinition.setQuantity(111);
        productForSale2.setProductDefinition(productDefinition2);
        List<ProductForSale> productsForSale = new ArrayList<ProductForSale>();
        productsForSale.add(productForSale1);
        productsForSale.add(productForSale2);
        ObservableList<ProductForSale> list = FXCollections.observableArrayList(productsForSale);

        Mockito.when(productsListModule.getShoppingList()).thenReturn(list);

        Mockito.doAnswer((Answer) invocation -> {
            list.clear();
            return null;
        }).when(productsListModule).deleteAllProducts();

        paymentModule.finalizePayment(productsListModule, productDefinitionModule);

        Assert.assertTrue(list.size() == 0);
    }
}