package cashregister.modules;

import cashregister.dao.interfaces.IProductDefinitionDao;
import cashregister.model.ProductDefinition;
import cashregister.model.ProductForSale;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class ProductsListModule implements IProductsListModule {

    private IProductDefinitionDao productDefinitionDao;
    public ProductsListModule(IProductDefinitionDao productDefinitionDao)
    {
        this.productDefinitionDao = productDefinitionDao;
        shoppingList = FXCollections.observableArrayList();
    }

    public void addProduct(String barcode)
    {
        ProductDefinition productDefinition = productDefinitionDao.getByBarcode(barcode);
        if(productDefinition != null)
        {
            ProductForSale newItem = new ProductForSale();
            newItem.setName(productDefinition.getName());
            newItem.setCountable(productDefinition.getCountable());
            newItem.setPrice(productDefinition.getPrice());
            newItem.setQuantity(1);
            newItem.setProductDefinition(productDefinition);
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

    public void deleteProduct(ProductForSale product) {
        shoppingList.remove(product);
    }

    public void changeQuantity(ProductForSale product, int quantity) { }

    public void addDiscount(ProductForSale product, float discount) { }

    public ObservableList getShoppingList() { return shoppingList; }

    private ObservableList shoppingList;
}
