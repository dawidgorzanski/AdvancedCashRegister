package cashregister.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Serializable {
    private int id;
    private String name;
    private String barcode;
    private String mail;
    private String address;
    private String phone;
    private List<Receipt> receipts;

    /**
     * Sets list of Receipt objects assigned to Customer
     * @param receipts
     */
    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
        for(Receipt r : receipts)
            r.setCustomer(this);
    }

    /**
     *
     * @return list of Receipt objects assigned to Customer
     */
    public List<Receipt> getReceipts() {return receipts;}


    /**
     * Initializes Customer with default attributes
     */
    public Customer() {
        this.id = 0;
        receipts = new ArrayList<Receipt>();
    }

    /**
     * Sets attributes of Customer with attributes given as parameters
     * @param id
     * @param name
     * @param barcode
     * @param mail
     * @param address
     * @param phone
     */
    public Customer(int id, String name, String barcode, String mail, String address, String phone){
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.mail = mail;
        this.address = address;
        this.phone = phone;
        receipts = new ArrayList<Receipt>();
    }

    /**
     *
     * @return id of Customer
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id of Customer
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return name of Customer
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of Customer
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return barcode assigned to Customer
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Sets barcode assigned to Customer
     * @param barcode
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     *
     * @return address email of Customer
     */
    public String getMail() {
        return mail;
    }

    /**
     * Sets address email of Customer
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     *
     * @return address of Customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address of Customer
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return phone of Customer
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone of Customer
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

}
