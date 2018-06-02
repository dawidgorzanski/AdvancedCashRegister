package cashregister.modules.interfaces;

import cashregister.model.Mail;
import cashregister.model.Receipt;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface IMailSenderModule {
    Mail createMail(Receipt receipt, String paperReceipt);
    void savePaperReceiptFromMail(Mail mail, String filename) throws FileNotFoundException, UnsupportedEncodingException;
    Multipart concatenateEmailFromMailAndFile(Mail mail, String filename) throws MessagingException;
    void sendMail(Mail mail);
}
