package cashregister.modules;

import cashregister.model.Mail;
import cashregister.modules.interfaces.IMailSenderModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmailRunnableTest {
    @Mock
    private IMailSenderModule mailSenderModule;

    @Mock
    private Runnable emailRunnable;

    @Test
    public void happyTest__AlwaysPassed() {
        Mail mail = new Mail();
        emailRunnable = new EmailRunnable(mailSenderModule, mail);
        emailRunnable.run();
        Mockito.verify(mailSenderModule).sendMail(mail);
    }
}