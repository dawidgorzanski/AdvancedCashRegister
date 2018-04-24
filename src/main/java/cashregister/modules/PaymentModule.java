package cashregister.modules;

import cashregister.dao.interfaces.IReceiptDao;
import cashregister.model.Customer;
import cashregister.model.ProductForSale;
import cashregister.model.Receipt;
import cashregister.modules.interfaces.IPaymentModule;

import java.util.Date;
import java.util.List;

public class PaymentModule implements IPaymentModule {
    private IReceiptDao receiptDao;

    public PaymentModule(IReceiptDao receiptDao) {
        this.receiptDao = receiptDao;
    }

    public void createSummary(Customer customer, List<ProductForSale> products) {
        Receipt newReceipt = new Receipt();
        newReceipt.setProductForSales(products);
        newReceipt.setCustomer(customer);
        newReceipt.setDate(new Date());
        receiptDao.save(newReceipt);
    }
}
