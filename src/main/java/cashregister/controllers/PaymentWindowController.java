package cashregister.controllers;

import cashregister.dao.PaymentCardDao;
import cashregister.model.Mail;
import cashregister.model.PaymentCard;
import cashregister.model.ProductDefinition;
import cashregister.model.ProductForSale;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.IPaymentModule;
import cashregister.modules.interfaces.IProductDefinitionModule;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.event.ActionEvent;
import javafx.extensions.DialogController;
import javafx.extensions.DialogResult;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class PaymentWindowController extends DialogController implements Initializable {

    private IProductsListModule productsListModule;
    private IPaymentModule paymentModule;
    private double price;
    boolean cardHandling;
    private IProductDefinitionModule productDefinitionModule;

    public PaymentWindowController() {
        this.productsListModule = ModulesManager.getObjectByType(IProductsListModule.class);
        this.paymentModule = ModulesManager.getObjectByType(IPaymentModule.class);
        this.productDefinitionModule = ModulesManager.getObjectByType(IProductDefinitionModule.class);
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
        cashField.setVisible(true);
        cashLabel.setVisible(true);
        cashField.clear();
        changeField.clear();
        changeField.setVisible(false);
        cashLabel.setText("PIN: ");
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
        paymentModule.createSummary(productsListModule.getCurrentCustomer(), productsListModule.getShoppingList());

        //paymentModule.finalizePayment(productsListModule);
        for (Object iter : productsListModule.getShoppingList()){
            ProductForSale productForSale = (ProductForSale)iter;
            ProductDefinition product = productForSale.getProductDefinition();

            product.decreaseQuantityBy(productForSale.getQuantity());
            productDefinitionModule.addProduct(product);
        }
        this.productsListModule.deleteAllProducts();
        this.productsListModule.deleteCustomerFromTransaction();
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        if(!cardHandling) {
            double change = Double.parseDouble(cashField.getText()) - this.price;
            changeField.setText(String.valueOf(change));
        }
        else{
            changeField.setText(paymentModule.cardPaymentHandler(Integer.parseInt(cashField.getText()), price));
            changeField.setVisible(true);
        }

        setDialogResult(DialogResult.OK);
    }
}