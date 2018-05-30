package cashregister.modules.interfaces;

import cashregister.model.Mail;
import cashregister.model.ProductForSale;
import cashregister.model.Customer;
import cashregister.model.Receipt;

import java.util.List;

public interface IPaymentModule {
    Receipt createSummary(IProductsListModule productsListModule);
    public void finalizePayment(IProductsListModule productsListModule, IProductDefinitionModule productDefinitionModule);
    String cardPaymentHandler(int sum, double price);
}
