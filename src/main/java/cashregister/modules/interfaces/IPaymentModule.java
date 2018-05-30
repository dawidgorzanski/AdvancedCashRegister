package cashregister.modules.interfaces;

import cashregister.model.Mail;
import cashregister.model.ProductForSale;
import cashregister.model.Customer;
import cashregister.model.Receipt;

import java.util.List;

public interface IPaymentModule {
    Receipt createSummary(Customer customer, List<ProductForSale> products);
    //public void finalizePayment(IProductsListModule productsListModule);
    String cardPaymentHandler(int sum, double price);
}
