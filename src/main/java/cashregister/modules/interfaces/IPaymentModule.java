package cashregister.modules.interfaces;

import cashregister.model.Mail;
import cashregister.model.ProductForSale;
import cashregister.model.Customer;

import java.util.List;

public interface IPaymentModule {
    void createSummary(Customer customer, List<ProductForSale> products);
}
