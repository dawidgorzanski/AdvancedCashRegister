package cashregister.modules;

import cashregister.dao.interfaces.IReceiptDao;
import cashregister.model.*;
import cashregister.modules.interfaces.IPaymentModule;
import cashregister.modules.interfaces.IProductDefinitionModule;
import cashregister.modules.interfaces.IProductsListModule;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentModule implements IPaymentModule {
    private IReceiptDao receiptDao;
    //private IProductDefinitionModule productDefinitionModule;

    /**
     * Initializes instance with IReceiptDao object
     * @param receiptDao object that allows to operate with Receipt objects on database
     */
    public PaymentModule(IReceiptDao receiptDao) {
        this.receiptDao = receiptDao;
        //this.productDefinitionModule = ModulesManager.getObjectByType(IProductDefinitionModule.class);
    }

    /**
     * Creates summary for transaction i.e creates Receipt associated with transaction
     * @param productsListModule
     * @return
     */
    public Receipt createSummary(IProductsListModule productsListModule) {
        Receipt newReceipt = new Receipt();
        newReceipt.setProductForSales( productsListModule.getShoppingList());
        newReceipt.setCustomer(productsListModule.getCurrentCustomer());
        newReceipt.setDate(new Date());

        receiptDao.save(newReceipt);
        return newReceipt;
    }

    /**
     * finalizing payment for transaction i.e removing all products and customer from it
     * @param productsListModule manages of ProductForSale list in transaction
     * @param productDefinitionModule manages of ProductDefinition objects
     */
    public void finalizePayment(IProductsListModule productsListModule, IProductDefinitionModule productDefinitionModule)
    {
        for (Object iter : productsListModule.getShoppingList()){
            ProductForSale productForSale = (ProductForSale)iter;
            ProductDefinition product = productForSale.getProductDefinition();

            product.decreaseQuantityBy(productForSale.getQuantity());
            productDefinitionModule.updateProduct(product);
        }

        productsListModule.deleteAllProducts();
        productsListModule.deleteCustomerFromTransaction();
    }

}
