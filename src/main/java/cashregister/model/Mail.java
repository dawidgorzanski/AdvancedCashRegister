package cashregister.model;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Mail {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPaperReceipt() {
        return paperReceipt;
    }

    public void setPaperReceipt(String paperReceipt) {
        this.paperReceipt = paperReceipt;
    }

    private String subject;
    private String body;
    private String paperReceipt;
    private int id;
    private Receipt receipt;


}
