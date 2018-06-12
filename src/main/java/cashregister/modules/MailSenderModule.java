package cashregister.modules;

import cashregister.dao.interfaces.IReceiptDao;
import cashregister.model.Receipt;
import cashregister.model.Mail;
import cashregister.modules.interfaces.IMailSenderModule;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * class responsible for managing mail sending
 */
public class MailSenderModule implements IMailSenderModule {
    private IReceiptDao receiptDao;

    /**
     * initializes instance with IReceiptDao object
     * @param receiptDao IReceiptDao object that allows database operations with Receipt objects
     */
    public MailSenderModule(IReceiptDao receiptDao) {
        this.receiptDao = receiptDao;
    }

    /**
     * creates new Mail with Receipt and paperReceipt passed as arguments
     * @param receipt Receipt object
     * @param paperReceipt receipt in string version that allows to create .txt file for customer
     * @return created Mail object
     */
    public Mail createMail(Receipt receipt, String paperReceipt) {
        if (receipt.getCustomer() == null)
            return null;

        Mail mail = new Mail();
        receipt.setMail(mail);
        mail.setReceipt(receipt);

        SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd");
        Date dNow = new Date();

        mail.setPaperReceipt(paperReceipt);
        mail.setSubject("Receipt " + ft.format(dNow));
        mail.setBody("Hello, " + mail.getReceipt().getCustomer().getName() + "\n We are sending you your receipt\n");

        receiptDao.saveOrUpdate(receipt);

        return mail;
    }

    /**
     * creates session which allow send email to customer
     * @param mail Mail object to send
     * @return javax.Mail.Session desirable object
     */
    private javax.mail.Session createSession(Mail mail) {
        final String user = "advancedcashregister@gmail.com";
        final String pass = "io!@2018";

        String host = "smtp.gmail.com";
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host",host);
        props.put("mail.smtp.port","587");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.ssl.trust","smtp.gmail.com");

        javax.mail.Session session = Session.getInstance(props,new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user,pass);
            }
        });
        return session;
    }

    /**
     * writes mail receipt to .txt file
     * @param mail Mail object from which receipt is taken
     * @param filename filename to which receipt will be writen
     * @throws FileNotFoundException exception if file with filename doesn't exist
     * @throws UnsupportedEncodingException exception if encoding troubles occurred
     */
    public void savePaperReceiptFromMail(Mail mail, String filename) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = null;
        try{
            writer = new PrintWriter(filename,"UTF-8");
            writer.print(mail.getPaperReceipt());
            writer.close();
        }
        catch(FileNotFoundException | UnsupportedEncodingException e){
            if(writer!=null)
                writer.close();
            throw e;
        }
    }

    /**
     * creates Multipart object from two separated parts i.e mail body message and attachment of mail
     * @param mail Mail object that contains needed attributes
     * @param filename filename where paper receipt is written
     * @return created Multipart object
     * @throws MessagingException
     */
    public Multipart concatenateEmailFromMailAndFile(Mail mail, String filename) throws MessagingException {
        MimeBodyPart attachmentBodyPart;
        MimeBodyPart messageBodyPart;

        Multipart multipart = new MimeMultipart();

        messageBodyPart = new MimeBodyPart();

        messageBodyPart.setText(mail.getBody());

        attachmentBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filename);
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName(filename);

        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachmentBodyPart);
        return multipart;
    }

    /**
     * creates message to customer
     * @param session created session between user email and application
     * @param mail Mail object that contains required attributes for message
     * @return created Message object
     * @throws MessagingException
     */
    private Message createMessage(javax.mail.Session session, Mail mail) throws MessagingException
    {
        String to = mail.getReceipt().getCustomer().getMail();
        String from = "advancedcashregister@gmail.com";

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        InternetAddress[] address = {new InternetAddress(to)};
        msg.setRecipients(Message.RecipientType.TO, address);
        msg.setSubject(mail.getSubject());
        msg.setSentDate(new Date());
        return msg;
    }

    /**
     * sending mail to customer
     * @param mail Mail object to send
     */
    public void sendMail(Mail mail)
    {
        javax.mail.Session session = createSession(mail);
        
        try{
            Message msg = createMessage(session, mail);

            String filename = "receipt.txt";
            savePaperReceiptFromMail(mail, filename);

            Multipart multipart = concatenateEmailFromMailAndFile(mail, filename);

            msg.setContent(multipart);
            Transport.send(msg);
        }
        catch (MessagingException | FileNotFoundException | UnsupportedEncodingException e ) {
            System.out.println("\nmail hasnt been sent, the reason is: \n\n");
            System.out.println(e);
        }
        catch(NullPointerException e){
            System.out.println("\nmail hasnt been sent, probably argument has null attributes\n\n");

        }
    }
}
