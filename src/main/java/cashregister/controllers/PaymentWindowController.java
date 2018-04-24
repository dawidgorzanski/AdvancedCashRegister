package cashregister.controllers;

import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.IPaymentModule;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaymentWindowController implements Initializable {
    private IProductsListModule productsListModule;
    private IPaymentModule paymentModule;

    public PaymentWindowController() {
        this.productsListModule = ModulesManager.getObjectByType(IProductsListModule.class);
        this.paymentModule = ModulesManager.getObjectByType(IPaymentModule.class);
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
    private void handleConfirmButtonAction(ActionEvent event) throws IOException
    {
        paymentModule.createSummary(productsListModule.getCurrentCustomer(), productsListModule.getShoppingList());
        this.productsListModule.deleteAllProducts();
        this.productsListModule.deleteCustomerFromTransaction();

        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}