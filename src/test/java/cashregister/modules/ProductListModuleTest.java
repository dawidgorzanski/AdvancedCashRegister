package cashregister.modules;

import cashregister.dao.interfaces.IProductDefinitionDao;
import cashregister.model.ProductDefinition;
import cashregister.model.ProductForSale;
import cashregister.modules.interfaces.IProductsListModule;
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
    public void addProductMethodIsShoppingListUpdatedCorrectlyTest(){
        final String barcode1 = "1234567891234";
        final String barcode2 = "0123456789123";
        ProductDefinition productDefinition1 = new ProductDefinition();
        productDefinition1.setPrice(110.4);
        productDefinition1.setName("product1");
        ProductDefinition productDefinition2 = new ProductDefinition();
        productDefinition2.setPrice(30);
        productDefinition2.setName("product2");
        Mockito.when(productDefinitionDao.getByBarcode(barcode1)).thenReturn(productDefinition1);
        Mockito.when(productDefinitionDao.getByBarcode(barcode2)).thenReturn(productDefinition2);

        ProductsListModule test = new ProductsListModule(productDefinitionDao);

        System.out.println(test.getShoppingList().size());
        test.addProduct(barcode1);
        System.out.println(test.getShoppingList().size());
        test.addProduct(barcode2);
        System.out.println(test.getShoppingList().size());

        for(ProductForSale it : test.getShoppingList()){
            System.out.println(it.getName());
            System.out.println(it.getPrice());
            System.out.println(it.getQuantity());
        }

    }

    @Test
    public void getTotalPriceMethodIsTotalPriceBeingCalculatedCorrectly(){
        final String barcode1 = "barcode1";
        final String barcode2 = "barcode2";
        ProductDefinition productDefinition1 = new ProductDefinition();
        productDefinition1.setPrice(110.4);
        productDefinition1.setName("product1");
        ProductDefinition productDefinition2 = new ProductDefinition();
        productDefinition2.setPrice(30);
        productDefinition2.setName("product2");
        Mockito.when(productDefinitionDao.getByBarcode(barcode1)).thenReturn(productDefinition1);
        Mockito.when(productDefinitionDao.getByBarcode(barcode2)).thenReturn(productDefinition2);

        ProductsListModule test = new ProductsListModule(productDefinitionDao);

        test.addProduct(barcode1);
        test.addProduct(barcode2);

        double returnedPrice = test.getTotalPrice();

        System.out.println(returnedPrice);
    }



}


/*
    public void addProduct(String barcode)
    {
        ProductDefinition productDefinition = productDefinitionDao.getByBarcode(barcode);
        if(productDefinition != null)
        {
            if (shoppingListContainsProduct(productDefinition)) {
                ProductForSale productForSale = findProductByProductDefinition(productDefinition);
                productForSale.setQuantity(productForSale.getQuantity() + 1);
            }
            else {
                ProductForSale newItem = new ProductForSale(productDefinition);
                shoppingList.add(newItem);
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Błąd");
            alert.setContentText("Podany kod nie znajduje się w bazie");
            alert.showAndWait();
        }

    public double getTotalPrice() {
        if (this.shoppingList == null || this.shoppingList.size() == 0)
            return 0;

        double result = 0;
        for(ProductForSale productForSale : this.shoppingList) {
            result += productForSale.getTotalPrice();
        }

        return result;
    }
    */