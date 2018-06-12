package cashregister.modules;

import cashregister.model.Mail;
import cashregister.modules.interfaces.IMailSenderModule;


/**
 * Implements Runnable interface
 * and is responsible for sending email with receipt to customer in new thread
 */
public class EmailRunnable implements Runnable {
    private IMailSenderModule mailSenderModule;
    private Mail mail;

    /**
     * Initializes instance with IMailSenderModule and Mail objects
     * @param mailSenderModule object responsible for managing mail sending
     * @param mail Mail object to send
     */
    public EmailRunnable(IMailSenderModule mailSenderModule, Mail mail)
    {
        this.mailSenderModule = mailSenderModule;
        this.mail = mail;
    }

    /**
     * Starts sending email to customer
     */
    public void run()
    {
        mailSenderModule.sendMail(mail);
    }
}
