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

    public PaymentModule(IReceiptDao receiptDao) {
        this.receiptDao = receiptDao;
        //this.productDefinitionModule = ModulesManager.getObjectByType(IProductDefinitionModule.class);
    }

    public Receipt createSummary(IProductsListModule productsListModule) {
        Receipt newReceipt = new Receipt();
        newReceipt.setProductForSales( productsListModule.getShoppingList());
        newReceipt.setCustomer(productsListModule.getCurrentCustomer());
        newReceipt.setDate(new Date());

        receiptDao.save(newReceipt);
        return newReceipt;
    }

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
