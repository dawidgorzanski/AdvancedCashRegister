package cashregister.model;

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

    public void send()
    {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd");
        Date dNow = new Date();
        String host = "smtp.gmail.com";
        final String user = "advancedcashregister@gmail.com";
        final String pass = "io!@2018";
        String to = receipt.getCustomer().getMail();
        String from = "advancedcashregister@gmail.com";
        String subject = "Receipt " + ft.format(dNow);
        String message = "Test message";

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
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            String filename = "receipt.txt";
            PrintWriter writer = new PrintWriter(filename,"UTF-8");

            writer.println("Paragon Fiskalny\n");
            for(ProductForSale ps : receipt.getProductForSales()) {
                writer.println(ps.getName() + "\t" + ps.getQuantity() + "*" + ps.getPrice() + "\t" + ps.getTotalPrice());
            }
            writer.println("\nRazem zł\t" + receipt.getTotalPrice());
            writer.close();

            MimeBodyPart attachmentBodyPart;
            MimeBodyPart messageBodyPart;

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(message);

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

    private int id;
    private Receipt receipt;


}
