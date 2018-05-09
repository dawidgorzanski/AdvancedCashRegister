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
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class MailSenderModule implements IMailSenderModule {
    private IReceiptDao receiptDao;

    public MailSenderModule(IReceiptDao receiptDao) {
        this.receiptDao = receiptDao;
    }

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

    public void sendMail(Mail mail)
    {
        String to = mail.getReceipt().getCustomer().getMail();
        String from = "advancedcashregister@gmail.com";

        final String user = "advancedcashregister@gmail.com";
        final String pass = "io!@2018";

        String host = "smtp.gmail.com";
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host",host);
        props.put("mail.smtp.port","587");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.ssl.trust","smtp.gmail.com");

        javax.mail.Session session = Session.getInstance(props,new javax.mail.Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user,pass);
            }
        });

        try{
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO,address);
            msg.setSubject(mail.getSubject());
            msg.setSentDate(new Date());

            String filename = "receipt.txt";
            PrintWriter writer = new PrintWriter(filename,"UTF-8");
            writer.print(mail.getPaperReceipt());
            writer.close();

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

            msg.setContent(multipart);

            Transport.send(msg);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
