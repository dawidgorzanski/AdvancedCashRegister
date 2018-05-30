package cashregister.modules;

import cashregister.dao.interfaces.IPaymentCardDao;
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

    public Receipt createSummary(Customer customer, List<ProductForSale> products) {
        Receipt newReceipt = new Receipt();
        newReceipt.setProductForSales(products);
        newReceipt.setCustomer(customer);
        newReceipt.setDate(new Date());

        receiptDao.save(newReceipt);
        return newReceipt;
    }
/*
    public void finalizePayment(IProductsListModule productsListModule)
    {
        for (Object iter : productsListModule.getShoppingList()){
            ProductForSale productForSale = (ProductForSale)iter;
            ProductDefinition product = productForSale.getProductDefinition();

            product.decreaseQuantityBy(productForSale.getQuantity());
            productDefinitionModule.addProduct(product);
        }
    }
*/
    @Transactional
    public String cardPaymentHandler(int sum, double price){
        boolean isInTable = false;
        IPaymentCardDao paymentCardDao = ModulesManager.getObjectByType(IPaymentCardDao.class);
        String cardPaymentMessage = new String();
        List<PaymentCard> paymentCardList = new ArrayList<PaymentCard>(paymentCardDao.getAll());
        for(PaymentCard it : paymentCardList) {
            if (it.getPIN() == sum) {
                isInTable = true;
                if (it.getAccountBalance() > price) {
                    paymentCardDao.updateAccountBalance(it,price); // sam nie wiem
                    cardPaymentMessage += "Transakcja zakończona pomyślnie.";
                } else
                    cardPaymentMessage += "Za mało środków na koncie!";
            }
        }
        if(!isInTable)
            cardPaymentMessage += "Podano zły PIN!";

        return cardPaymentMessage;
    }
}
