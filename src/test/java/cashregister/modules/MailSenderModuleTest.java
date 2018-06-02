package cashregister.modules;

import cashregister.dao.interfaces.IReceiptDao;
import cashregister.model.Customer;
import cashregister.model.Mail;
import cashregister.model.Receipt;
import cashregister.modules.interfaces.IMailSenderModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(MockitoJUnitRunner.class)
public class MailSenderModuleTest {

    @Mock
    private IReceiptDao receiptDaoMock;

    @Mock
    private IMailSenderModule mailSenderModuleMock;

    @Before
    public void setUp(){
        mailSenderModuleMock = new MailSenderModule(receiptDaoMock);
    }

    @Test
    public void createMail_ProperArguments_returnsMail() {
        //Arrange
        Receipt receipt = new Receipt();
        String paperReceipt = "emptyReceipt";
        receipt.setCustomer(new Customer());

        Mail expectedMail = new Mail();
        receipt.setMail(expectedMail);
        expectedMail.setReceipt(receipt);

        SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd");
        Date dNow = new Date();

        expectedMail.setPaperReceipt(paperReceipt);
        expectedMail.setSubject("Receipt " + ft.format(dNow));
        expectedMail.setBody("Hello, " + expectedMail.getReceipt().getCustomer().getName() + "\n We are sending you your receipt\n");

        Mockito.doAnswer((Answer) invocation-> {return null;}).when(receiptDaoMock).saveOrUpdate(Matchers.anyObject());

        //Act
        Mail createdMail = mailSenderModuleMock.createMail(receipt, paperReceipt);
        //System.out.println("created: "+createdMail.getPaperReceipt()+createdMail.getReceipt()+createdMail.getBody()+createdMail.getSubject());
        //System.out.println("expected: "+expectedMail.getPaperReceipt()+expectedMail.getReceipt()+expectedMail.getBody()+expectedMail.getSubject());

        //Assert
        assertEquals(expectedMail.getPaperReceipt(), createdMail.getPaperReceipt());
        assertEquals(expectedMail.getReceipt(), createdMail.getReceipt());
        assertEquals(expectedMail.getBody(), createdMail.getBody());
        assertEquals(expectedMail.getSubject(), createdMail.getSubject());
    }

    @Test
    public void createMail_customerFromReceiptIsNull_returnsNull() {
        //Arrange
        Receipt receipt = new Receipt();
        String paperReceipt = "emptyReceipt";
        receipt.setCustomer(null);

        Mockito.doAnswer((Answer) invocation-> {return null;}).when(receiptDaoMock).saveOrUpdate(Matchers.anyObject());

        //Act
        Mail createdMail = mailSenderModuleMock.createMail(receipt, paperReceipt);

        //Assert
        assertEquals(null , createdMail);


    }

    @Test
    public void createMail_paperReceiptIsVeryLong_returnsMail() {
        //Arrange
        Receipt receipt = new Receipt();
        String paperReceipt = "";
        for(int i = 0; i<1000; i++)
        {
            paperReceipt+= "emptyReceiptemptyReceiptemptyReceiptemptyReceiptemptyReceiptemptyReceipt\n";
        }
        receipt.setCustomer(new Customer());

        Mail expectedMail = new Mail();
        receipt.setMail(expectedMail);
        expectedMail.setReceipt(receipt);

        SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd");
        Date dNow = new Date();

        expectedMail.setPaperReceipt(paperReceipt);
        expectedMail.setSubject("Receipt " + ft.format(dNow));
        expectedMail.setBody("Hello, " + expectedMail.getReceipt().getCustomer().getName() + "\n We are sending you your receipt\n");

        Mockito.doAnswer((Answer) invocation-> {return null;}).when(receiptDaoMock).saveOrUpdate(Matchers.anyObject());

        //Act
        Mail createdMail = mailSenderModuleMock.createMail(receipt, paperReceipt);

        //Assert
        assertEquals(expectedMail.getPaperReceipt(), createdMail.getPaperReceipt());
        assertEquals(expectedMail.getReceipt(), createdMail.getReceipt());
        assertEquals(expectedMail.getBody(), createdMail.getBody());
        assertEquals(expectedMail.getSubject(), createdMail.getSubject());
    }

    @Test
    public void savePaperReceiptFromMail_ProperArguments_PerformProperly() {
        //Arrange
        String filename = "receipt.txt";
        Mail mail = new Mail();
        mail.setPaperReceipt("receipt");

        //Act and assert
        try {
            mailSenderModuleMock.savePaperReceiptFromMail(mail, filename);
        }
        catch(FileNotFoundException | UnsupportedEncodingException e){
            fail("Should not have thrown any exception");

        }
        return;
    }

    @Test
    public void savePaperReceiptFromMail_EmptyFilename_ThrowsFileNotFountException() {
        //Arrange
        String filename = "";
        Mail mail = new Mail();
        mail.setPaperReceipt("receipt");

        //Act and assert
        try {
            mailSenderModuleMock.savePaperReceiptFromMail(mail, filename);
        }
        catch(UnsupportedEncodingException e)
        {
            fail("Should have thrown FileNotFoundException instead of UnsupportedEncodingException");
        }
        catch(FileNotFoundException e) {
            return;
        }
        fail("Should have thrown FileNotFoundException");
    }


    @Test
    public void concatenateEmailFromMailAndFile_FilenameDoesntExist_DoesntThrowException() {
        Mail mail = new Mail();
        mail.setBody("body");
        String filename = "doesntexist.txt";
        try{
            mailSenderModuleMock.concatenateEmailFromMailAndFile(mail, filename);
        }
        catch(MessagingException e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    public void concatenateEmailFromMailAndFile_ArgumentIsntSetUp_ThrowException() {
        Mail mail = new Mail();
        String filename = "doesntexist.txt";
        try{
            mailSenderModuleMock.concatenateEmailFromMailAndFile(mail, filename);
        }
        catch(MessagingException | NullPointerException e) {
            return;
        }
        fail("Should have thrown exception");

    }

    @Test
    public void sendMail_ArgumentIsPartlySetUp_DoesntThrowAnyException() {
        Customer customer = new Customer();
        customer.setMail("mail@mail");

        Receipt receipt = new Receipt();
        receipt.setCustomer(customer);

        Mail mail = new Mail();
        mail.setReceipt(receipt);
        mail.setBody("body");

        try {
            mailSenderModuleMock.sendMail(mail);
        }
        catch(Exception e) {
            System.out.println(e);
            fail("Should not have thrown any exception");
        }
        return;
    }

    @Test
    public void sendMail_ArgumentIsntSetUp_DoesntThrowAnyException() {
        Mail mail = new Mail();
        try {
            mailSenderModuleMock.sendMail(mail);
        }
        catch(Exception e) {
            System.out.println(e);
            fail("Should not have thrown any exception");
        }
        return;
    }



}