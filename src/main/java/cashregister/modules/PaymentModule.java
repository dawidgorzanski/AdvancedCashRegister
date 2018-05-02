package cashregister.modules;

import cashregister.dao.interfaces.ICustomerDao;
import cashregister.dao.interfaces.IReceiptDao;
import cashregister.model.Customer;
import cashregister.model.Mail;
import cashregister.model.ProductForSale;
import cashregister.model.Receipt;
import cashregister.modules.interfaces.IEmailSender;
import cashregister.modules.interfaces.IPaymentModule;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class PaymentModule implements IPaymentModule {
    private IReceiptDao receiptDao;
    private IEmailSender emailSender;

    public PaymentModule(IReceiptDao receiptDao) {
        this.receiptDao = receiptDao;
    }

    @Transactional
    public void createSummary(Customer customer, List<ProductForSale> products) {

        //testowy customer
        ICustomerDao customerDao = ModulesManager.getObjectByType(ICustomerDao.class);
        Customer customer2 = new Customer();
        customer2.setMail("advancedcashregister@gmail.com");
        customer2.setName("testowy");
        customerDao.save(customer2);
        //potem usunac

        Mail newMail = new Mail();
        Receipt newReceipt = new Receipt();
        newReceipt.setProductForSales(products);
        newReceipt.setCustomer(customer2);
        newReceipt.setDate(new Date());
        newReceipt.setMail(newMail);
        newMail.setReceipt(newReceipt);

        emailSender = new EmailSender(newMail);
        new Thread(emailSender::send).start();


        receiptDao.save(newReceipt);
    }
}
