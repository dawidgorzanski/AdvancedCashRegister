package cashregister.modules;

import cashregister.model.Product;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class ProductsListModule implements IProductsListModule {

    public ProductsListModule()
    {
        shoppingList = FXCollections.observableArrayList();
        testData = new TestProductList();
    }

    public void addProduct(int barcode)
    {
        String name = testData.getProduct(barcode);
        if(!name.equals(null))
        {
            Product newItem = new Product(name);
            shoppingList.add(newItem);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Błąd");
            alert.setContentText("Podany kod nie znajduje się w bazie");
            alert.showAndWait();
        }
    }

    public void deleteProduct(Product product) {
        shoppingList.remove(product);
    }

    public void changeQuantity(Product product, int quantity) { }

    public void addDiscount(Product product, float discount) { }

    public ObservableList getShoppingList() { return shoppingList; }

    private ObservableList shoppingList;

    //Dane testowe
    TestProductList testData;
}
