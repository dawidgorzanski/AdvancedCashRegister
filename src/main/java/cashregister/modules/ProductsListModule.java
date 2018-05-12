package cashregister.modules;

import cashregister.dao.interfaces.IProductDefinitionDao;
import cashregister.model.Customer;
import cashregister.model.ProductDefinition;
import cashregister.model.ProductForSale;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class ProductsListModule implements IProductsListModule {

    private IProductDefinitionDao productDefinitionDao;
    private Customer currentCustomerForTransaction;

    public ProductsListModule(IProductDefinitionDao productDefinitionDao)
    {
        this.productDefinitionDao = productDefinitionDao;
        shoppingList = FXCollections.observableArrayList();
    }

    public ProductDefinition getByBarcode(String barcode) {
        return productDefinitionDao.getByBarcode(barcode);
    }

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
    }

    private boolean shoppingListContainsProduct(ProductDefinition productDefinition) {
        for(ProductForSale productForSale : shoppingList) {
            if (productForSale.getProductDefinition().getId() == productDefinition.getId()) {
                return true;
            }
        }

        return false;
    }

    private ProductForSale findProductByProductDefinition(ProductDefinition productDefinition) {
        for(ProductForSale productForSale : shoppingList) {
            if (productForSale.getProductDefinition().getId() == productDefinition.getId()) {
                return productForSale;
            }
        }

        return null;
    }

    public void deleteProduct(ProductForSale product) {
        shoppingList.remove(product);
    }

    public void deleteAllProducts() {
        this.shoppingList.clear();
    }

    public void changeQuantity(ProductForSale product, int quantity) { }

    public void addDiscount(ProductForSale product, float discount) { }

    public ObservableList<ProductForSale> getShoppingList() { return shoppingList; }

    private ObservableList<ProductForSale> shoppingList;

    public void setCustomerForTransaction(Customer customer) {
        this.currentCustomerForTransaction = customer;
    }

    public void deleteCustomerFromTransaction() {
        this.currentCustomerForTransaction = null;
    }

    public Customer getCurrentCustomer() {
        return this.currentCustomerForTransaction;
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
}
