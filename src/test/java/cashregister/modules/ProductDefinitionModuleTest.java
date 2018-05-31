package cashregister.modules;

import cashregister.dao.interfaces.IProductDefinitionDao;
import cashregister.model.ProductDefinition;
import cashregister.modules.interfaces.IProductDefinitionModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ProductDefinitionModuleTest {
    @Mock
    private ProductDefinition productDefinition1, productDefinition2, productDefinition3;

    @Mock
    private IProductDefinitionDao productDefinitionDaoMock;

    @InjectMocks
    private IProductDefinitionModule productDefinitionModule = new ProductDefinitionModule(productDefinitionDaoMock);

    @Test
    public void getAllProductsMethodReturnsAllProductsFromDatabaseTest(){

        //Arrange
        List<ProductDefinition> productsList = new ArrayList<ProductDefinition>();
        productsList.add(productDefinition1);
        productsList.add(productDefinition2);
        productsList.add(productDefinition3);
        Mockito.when(productDefinitionDaoMock.getAll()).thenReturn(productsList);

        //Act
        List<ProductDefinition> productsTest = productDefinitionModule.getAllProducts();

        //Assert
        assertEquals(productsTest, productsList);
    }

    @Test
    public void addProductMethodAddsProductToDatabaseTest(){
        //Arrange
        List<ProductDefinition> ProductsList = new ArrayList<ProductDefinition>();

        Mockito.doAnswer( (Answer) invocation -> {
            ProductDefinition arg0 = invocation.getArgumentAt(0,ProductDefinition.class);
            ProductsList.add(arg0);
            return null;
        }).when(productDefinitionDaoMock).saveOrUpdate(Matchers.anyObject());

        //Act
        productDefinitionModule.addProduct(productDefinition1);
        productDefinitionModule.addProduct(productDefinition2);

        //Assert
        assertTrue(ProductsList.size() == 2  );
    }

    @Test
    public void updateProductMethodUpdateProductsInDatabaseTest()
    {
        //Arrange
        ArrayList<ProductDefinition> ProductDefinitionList = new ArrayList<ProductDefinition>();
        ProductDefinitionList.add(productDefinition1);

        ProductDefinition productForUpdate = new ProductDefinition(4, "bread", 100.0, 2.5, "1234123412341", true, false);
        ProductDefinitionList.add(productForUpdate);


        Mockito.doAnswer( (Answer) invocation -> {
            ProductDefinition arg0 = invocation.getArgumentAt(0,ProductDefinition.class);
            ProductDefinitionList.set(ProductDefinitionList.indexOf(arg0), arg0);
            return null;
        }).when(productDefinitionDaoMock).update(Matchers.anyObject());


        //Act
        productForUpdate.setName("Maslo");
        productDefinitionModule.updateProduct(productForUpdate);


        //Assert
        assertTrue(ProductDefinitionList.contains(productForUpdate));
        assertEquals("Maslo", ProductDefinitionList.get(ProductDefinitionList.indexOf(productForUpdate)).getName() );
    }

    @Test
    public void deleteProductMethodDeletesProductFromDatabaseTest(){
        //Arrange
        List<ProductDefinition> productsList = new ArrayList<ProductDefinition>();
        productsList.add(productDefinition1);
        productsList.add(productDefinition2);
        productsList.add(productDefinition3);

        Mockito.doAnswer( (Answer)invocation-> {
            productsList.remove(invocation.getArgumentAt(0,ProductDefinition.class));
            return null;
        }).when(productDefinitionDaoMock).delete(Matchers.anyObject());

        //Act
        productDefinitionModule.deleteProduct(productDefinition2);
        //Assert
        assertTrue(productsList.size() == 2);
        productDefinitionModule.deleteProduct(productDefinition1);
        assertTrue(productsList.size() == 1);
    }

    @Test
    public void getProductByBarcodeReturnsCorrectProductTest(){
        //Arrange
        String barcode = "1234123412341";
        Mockito.when(productDefinitionDaoMock.getByBarcode(barcode)).thenReturn(productDefinition1);

        //Act
        ProductDefinition productTest = productDefinitionModule.getByBarcode(barcode);

        //Assert
        assertTrue(productTest.equals(productDefinition1));
    }

    @Test
    public void getProductByNameReturnsCorrectProductTest(){
        //Arrange
        String name = "name";

        List<ProductDefinition> productsList = new ArrayList<>();
        productsList.add(productDefinition1);
        ObservableList<ProductDefinition> productsObservableList = FXCollections.observableArrayList(productsList);

        Mockito.when(productDefinitionDaoMock.getByName(name)).thenReturn(productsObservableList);

        //Act
        List<ProductDefinition> productsListTest = productDefinitionModule.getByName(name);

        //Assert
        assertTrue(productsListTest == productsObservableList);
    }



}







