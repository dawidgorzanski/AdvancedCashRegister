package cashregister.controllers;

import cashregister.model.Mail;
import cashregister.model.Receipt;
import cashregister.modules.EmailRunnable;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.IMailSenderModule;
import cashregister.modules.interfaces.IPaperReceiptCreator;
import cashregister.modules.interfaces.IPaymentModule;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminWindowController implements Initializable {
    private IProductsListModule productsListModule;
    private IPaymentModule paymentModule;
    private IPaperReceiptCreator paperReceiptCreator;
    private IMailSenderModule mailSenderModule;

    public AdminWindowController() {
        this.productsListModule = ModulesManager.getObjectByType(IProductsListModule.class);
        this.paymentModule = ModulesManager.getObjectByType(IPaymentModule.class);
        this.paperReceiptCreator = ModulesManager.getObjectByType(IPaperReceiptCreator.class);
        this.mailSenderModule = ModulesManager.getObjectByType(IMailSenderModule.class);
    }

    @FXML
    private Label cashLabel, changeLabel, plnLabel1, plnLabel2;
    @FXML
    private TextField cashField, changeField;
    @FXML
    private Text textTotalPrice;
    @FXML
    private Button confirmButton;

    @FXML
    private void handleCashButtonAction(ActionEvent event) {
        cashField.setVisible(true);
        changeField.setVisible(true);
        cashLabel.setVisible(true);
        changeLabel.setVisible(true);
        plnLabel1.setVisible(true);
        plnLabel2.setVisible(true);
        confirmButton.setVisible(true);
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cashField.setVisible(false);
        changeField.setVisible(false);
        cashLabel.setVisible(false);
        changeLabel.setVisible(false);
        plnLabel1.setVisible(false);
        plnLabel2.setVisible(false);
        confirmButton.setVisible(false);

        textTotalPrice.setText("SUMA: " + String.valueOf(productsListModule.getTotalPrice()) + " PLN");
    }

    @FXML
    private void handleKeyAction(KeyEvent key) throws IOException {
        KeyCode keyCode = key.getCode();
        if (keyCode.equals(KeyCode.ENTER)) {
            if(confirmButton.isVisible())
            confirmButton.fire();
            return;
        }
        if (keyCode.equals(KeyCode.ESCAPE)) {
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
            return;
        }
    }

    @FXML
    private void handleConfirmButtonAction(ActionEvent event) throws IOException
    {
        Receipt receipt = paymentModule.createSummary(productsListModule.getCurrentCustomer(), productsListModule.getShoppingList());
        String paperReceipt = paperReceiptCreator.createPaperReceipt(receipt);
        Mail mail = mailSenderModule.createMail(receipt, paperReceipt);

        EmailRunnable emailRunnable = new EmailRunnable(mailSenderModule, mail);
        new Thread(emailRunnable).start();

        this.productsListModule.deleteAllProducts();
        this.productsListModule.deleteCustomerFromTransaction();

        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

}