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

    /**
     * Initializes instance with IProductDefinitionDao object
     * @param productDefinitionDao allows to operate with ProductDefinition objects on database
     */
    public ProductsListModule(IProductDefinitionDao productDefinitionDao)
    {
        this.productDefinitionDao = productDefinitionDao;
        shoppingList = FXCollections.observableArrayList();
    }

    /**
     * Gets ProductDefinition by barcode
     * @param barcode barcode integrated with ProductDefinition
     * @return desirable ProductDefinition object if success, null otherwise
     */
    public ProductDefinition getByBarcode(String barcode) {
        return productDefinitionDao.getByBarcode(barcode);
    }

    /**
     * Adds Product to database
     * @param barcode barcode that will be integrated with ProductDefinition
     */
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

    /**
     * Deletes ProductForSale object
     * @param product ProductForSale object to delete
     */
    public void deleteProduct(ProductForSale product) {
        shoppingList.remove(product);
    }


    /**
     * Deletes all ProductForSales objects
     */
    public void deleteAllProducts() {
        this.shoppingList.clear();
    }

    /**
     * Changes quantity of ProductForSale object
     * @param product product to change
     * @param quantity new quantity
     */
    public void changeQuantity(ProductForSale product, int quantity) { }


    /**
     * Adds discount to ProductForSale
     * @param product product which will have discount assigned
     * @param discount value of discount
     */
    public void addDiscount(ProductForSale product, float discount) { }


    /**
     * Returns ObservableList of all ProductForSale
     * @return desirable ObservableList object
     */
    public ObservableList<ProductForSale> getShoppingList() { return shoppingList; }

    private ObservableList<ProductForSale> shoppingList;

    /**
     * Sets customer for current transaction
     * @param customer customer that will be set
     */
    public void setCustomerForTransaction(Customer customer) {
        this.currentCustomerForTransaction = customer;
    }

    /**
     * Deletes customer for current transaction
     */
    public void deleteCustomerFromTransaction() {
        this.currentCustomerForTransaction = null;
    }

    /**
     * Gets current customer assigned to transaction
     * @return desirable Customer object if exists, null otherwise
     */
    public Customer getCurrentCustomer() {
        return this.currentCustomerForTransaction;
    }

    /**
     * Gets total price of current products
     * @return value of total price of products
     */
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
