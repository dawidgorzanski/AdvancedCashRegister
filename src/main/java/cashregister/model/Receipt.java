package cashregister.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Receipt {
    private int id;
    private Date date;
    //Customer jest null gdy nie dodamy go podczas skanowania artykułów
    private Customer customer;
    private List<ProductForSale> productForSales;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<ProductForSale> getProductForSales() {
        return productForSales;
    }

    public void setProductForSales(List<ProductForSale> productForSales) {
        this.productForSales = productForSales;
    }

    public double getTotalPrice() {
        if (this.productForSales == null || this.productForSales.size() == 0)
            return 0;

        double result = 0;
        for(ProductForSale productForSale : this.productForSales) {
            result += productForSale.getTotalPrice();
        }

        return result;
    }

    public Receipt() {
        this.productForSales = new ArrayList<ProductForSale>();
    }
}
