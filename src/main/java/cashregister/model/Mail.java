package cashregister.model;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Mail {

    /**
     *
     * @return id of Mail
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id of Mail
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return Receipt assigned to Mail
     */
    public Receipt getReceipt() {
        return receipt;
    }

    /**
     * Sets Receipt that will be assigned to Mail
     * @param receipt
     */
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    /**
     *
     * @return subject of Mail
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets subject of Mail
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     *
     * @return body of Mail (message)
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets body of Mail (message)
     * @param body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     *
     * @return receipt as String
     */
    public String getPaperReceipt() {
        return paperReceipt;
    }

    /**
     * Sets paper receipt (String format)
     * @param paperReceipt
     */
    public void setPaperReceipt(String paperReceipt) {
        this.paperReceipt = paperReceipt;
    }

    private String subject;
    private String body;
    private String paperReceipt;
    private int id;
    private Receipt receipt;


}
