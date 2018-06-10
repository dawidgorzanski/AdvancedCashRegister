package cashregister.controllers;

import cashregister.model.*;
import cashregister.modules.EmailRunnable;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.*;
import javafx.event.ActionEvent;
import javafx.extensions.DialogController;
import javafx.extensions.DialogResult;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class PaymentWindowController extends DialogController implements Initializable {

    private MainWindowController mainWindowController;
    private IProductsListModule productsListModule;
    private IPaymentModule paymentModule;
    private double price;
    boolean cardHandling;
    private IProductDefinitionModule productDefinitionModule;
    private IPaperReceiptCreator paperReceiptCreator;
    private IMailSenderModule mailSenderModule;

    public PaymentWindowController() {
        this.productsListModule = ModulesManager.getObjectByType(IProductsListModule.class);
        this.paymentModule = ModulesManager.getObjectByType(IPaymentModule.class);
        this.productDefinitionModule = ModulesManager.getObjectByType(IProductDefinitionModule.class);
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
        cashField.clear();
        changeField.clear();
        cashLabel.setText("Gotówka ");
        cashLabel.setVisible(true);
        changeLabel.setVisible(true);
        plnLabel1.setVisible(true);
        plnLabel2.setVisible(true);
        confirmButton.setVisible(true);
        cardHandling = false;
    }

    @FXML
    private void handleCardPayment(ActionEvent event){
        cashField.setVisible(false);
        cashLabel.setVisible(false);
        cashField.clear();
        changeField.clear();
        changeField.setVisible(false);
        changeLabel.setVisible(false);
        plnLabel1.setVisible(false);
        plnLabel2.setVisible(false);
        confirmButton.setVisible(true);
        cardHandling = true;
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        setDialogResult(DialogResult.Cancel);
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

        this.price = productsListModule.getTotalPrice();
        textTotalPrice.setText("SUMA: " + this.price + " PLN");
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
        if(!cardHandling) {
            Alert alert;
            if(cashField.getText().equals("") || Double.parseDouble(cashField.getText()) < this.price) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ostrzeżenie");
                alert.setContentText("Za mała ilość gotówki lub wcale niewprowadzona");
                alert.showAndWait();
                return;
            }
            DecimalFormat df = new DecimalFormat("#.##");
            double change = Double.parseDouble(cashField.getText()) - this.price;
            changeField.setText(String.valueOf(change));
            if(!(change <  1e-7 && change > -1e-7)) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Reszta");
                alert.setHeaderText("Reszta");
                alert.setContentText("Wydaj reszte: " + df.format(change) + " zł");
                alert.showAndWait();
            }
        }
        else{
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        }
        Receipt receipt = paymentModule.createSummary(productsListModule);
        String paperReceipt = paperReceiptCreator.createPaperReceipt(receipt);
        Mail mail = mailSenderModule.createMail(receipt, paperReceipt);

        EmailRunnable emailRunnable = new EmailRunnable(mailSenderModule, mail);
        new Thread(emailRunnable).start();

        paymentModule.finalizePayment(productsListModule, productDefinitionModule);

        Stage stage = (Stage) confirmButton.getScene().getWindow();



        stage.close();
        mainWindowController.updateTotalPrice();


        setDialogResult(DialogResult.OK);
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
            this.mainWindowController = mainWindowController;
    }
}