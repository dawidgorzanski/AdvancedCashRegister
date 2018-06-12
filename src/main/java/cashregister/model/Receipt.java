package cashregister.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Receipt implements Serializable {
    private int id;
    private Date date;
    //Customer jest null gdy nie dodamy go podczas skanowania artykułów
    private Customer customer;
    private List<ProductForSale> productForSales;
    private Mail mail;

    /**
     * Sets mail for receipt
     * @param mail
     */
    public void setMail(Mail mail) {this.mail = mail;}
    public Mail getMail() {return mail;}

    /**
     * Returns id of Receipt
     * @return id of Receipt
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id of Receipt
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns date of shopping
     * @return date of shopping
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date of shopping
     * @param date current date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns Customer for Receipt(owner)
     * @return Customer for Receipt
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets Customer for Receipt
     * @param customer customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets list of all ProductForSale objects
     * @return list of all ProductForSale objects
     */
    public List<ProductForSale> getProductForSales() {
        return productForSales;
    }

    /**
     * Sets list of ProductForSale objects
     * @param productForSales
     */
    public void setProductForSales(List<ProductForSale> productForSales) {
        this.productForSales = productForSales;
        for(ProductForSale productForSale : productForSales) {
            productForSale.setReceipt(this);
        }
    }

    /**
     * Counts total price of products
     * @return total price
     */
    public double getTotalPrice() {
        if (this.productForSales == null || this.productForSales.size() == 0)
            return 0;

        double result = 0;
        for(ProductForSale productForSale : this.productForSales) {
            result += productForSale.getTotalPrice();
        }

        return result;
    }

    /**
     * Initializes list of ProductForSale
     */
    public Receipt() {
        this.productForSales = new ArrayList<ProductForSale>();
    }
}
