package cashregister.modules;

import cashregister.dao.interfaces.IProductDefinitionDao;
import cashregister.model.Customer;
import cashregister.model.ProductDefinition;
import cashregister.model.ProductForSale;
import cashregister.modules.interfaces.IProductDefinitionModule;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.List;

public class ProductDefinitionModule implements IProductDefinitionModule {

    private IProductDefinitionDao productDefinitionDao;

    /**
     * Initializes instance with IProductDefinitionDao object
     * @param productDefinitionDao object that allows to operate with ProductDefinition objects on database
     */
    public ProductDefinitionModule(IProductDefinitionDao productDefinitionDao)
    {
        this.productDefinitionDao = productDefinitionDao;
    }

    /**
     * Returns ObservableList of all ProductDefinition objects
     * @return desirable ObservableList object
     */
    public ObservableList<ProductDefinition> getAllProducts()
    {
        List<ProductDefinition> products = productDefinitionDao.getAll();
        ObservableList<ProductDefinition> productsList = FXCollections.observableArrayList(products);
        return productsList;
    }

    /**
     * Adds ProductDefinition object to database
     * @param product product to add
     */
    public void addProduct(ProductDefinition product)
    {
        productDefinitionDao.saveOrUpdate(product);
    }

    /**
     * Update in database already existing ProductDefinition object
     * @param product product to update
     */
    public void updateProduct(ProductDefinition product)
    {
        productDefinitionDao.update(product);
    }

    /**
     * Deletes ProductDefinition object from database
     * @param product product to delete
     */
    public void deleteProduct(ProductDefinition product)
    {
        productDefinitionDao.delete(product);
    }

    /**
     * Returns ObservableList with all ProductDefinitions object that fulfill name criterion
     * @param name name criterion
     * @return desirable ObservableList object
     */
    public ObservableList<ProductDefinition> getByName(String name)
    {
        ObservableList<ProductDefinition> prooductsList = productDefinitionDao.getByName(name);
        return prooductsList;
    }

    /**
     * Gets ProductDefinition by barcode associated with him
     * @param barcode barcode that determines ProductDefinition
     * @return desirable ProductDefinition if found, null otherwise
     */
    public ProductDefinition getByBarcode(String barcode){ return productDefinitionDao.getByBarcode(barcode); }

    public void changeQuantity(ProductDefinition product, int quantity) { }

}
