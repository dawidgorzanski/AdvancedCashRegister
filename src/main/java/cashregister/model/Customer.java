package cashregister.model;

public class Customer {
    private int id;
    private String name;
    private String barcode;
    private String mail;
    private String address;
    private String phone;

    public Customer() {this.id = 0;}

    public Customer(int id, String name, String barcode, String mail, String address, String phone){
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.mail = mail;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
