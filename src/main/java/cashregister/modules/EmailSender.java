package cashregister.modules;

import cashregister.model.*;
import cashregister.modules.interfaces.IEmailSender;
import com.rits.cloning.Cloner;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class EmailSender implements IEmailSender {
    EmailCustomer customer;
    List<EmailProductForSale> productsForSale;

    class EmailCustomer {
        String mail;

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        String name;

        public EmailCustomer(Customer customer) {
            mail = customer.getMail();
            name = customer.getName();
        }
    }

    class EmailProductForSale{
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        String name;
        double quantity;
        double price;
        double totalPrice;

        public EmailProductForSale(ProductForSale pfs) {
            name = pfs.getName();
            quantity = pfs.getQuantity();
            price = pfs.getPrice();
            totalPrice = pfs.getTotalPrice();

        }

    }

    public void send()
    {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd");
        Date dNow = new Date();
        String host = "smtp.gmail.com";
        final String user = "advancedcashregister@gmail.com";
        final String pass = "io!@2018";
        String to = customer.getMail();
        String from = "advancedcashregister@gmail.com";
        String subject = "Receipt " + ft.format(dNow);
        String message = "Hello, " + customer.getName() + "\n We are sending you your receipt\n";

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

            float sum =0;
            writer.println("Paragon Fiskalny\n");
            for(EmailProductForSale ps : productsForSale) {
                writer.println(ps.getName() + "\t" + ps.getQuantity() + "*" + ps.getPrice() + "\t" + ps.getTotalPrice());
                sum += ps.getTotalPrice();
            }
            writer.println("\nRazem zł\t" + sum );
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

    public EmailSender(Mail mail) {
            productsForSale = new ArrayList<>();
            for(ProductForSale pfs : mail.getReceipt().getProductForSales())
                productsForSale.add(new EmailProductForSale(pfs));
            customer = new EmailCustomer(mail.getReceipt().getCustomer());

    }

}
