package cashregister.controllers;


import cashregister.helpers.ValidatorHelper;
import cashregister.model.Customer;
import cashregister.model.User;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.IAuthenticationModule;
import cashregister.modules.interfaces.ICustomerModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.IOException;


public class NewClientWindowController {

    private ICustomerModule customerModule;
    private IAuthenticationModule authenticationModule;
    private Customer customer;
    public NewClientWindowController() {

        this.customerModule = ModulesManager.getObjectByType(ICustomerModule.class);
        this.authenticationModule = ModulesManager.getObjectByType(IAuthenticationModule.class);
    }

    @FXML
    private TextField nameField, barcodeField, mailField, addressField, phoneField;
    @FXML
    private Button btnEnter, btnCancel;
    @FXML
    private Text title;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        if (customer != null) {
            nameField.setText(customer.getName());
            barcodeField.setText(customer.getBarcode());
            addressField.setText(customer.getAddress());
            phoneField.setText(customer.getPhone());
            mailField.setText(customer.getMail());
            title.setText("Edytuj klienta");
        }
    }

    @FXML
    private void handleKeyAction(KeyEvent key) throws IOException {
        KeyCode keyCode = key.getCode();
        if (keyCode.equals(KeyCode.ENTER)) {
            btnEnter.fire();
            return;
        }
        if (keyCode.equals(KeyCode.ESCAPE)) {
            btnCancel.fire();
            return;
        }
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) throws IOException {
        exitAction(event);
    }

    private void exitAction(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    private void showErrorAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawna wartość");
        alert.setHeaderText("Niepoprawna wartość");
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleOkButtonAction(ActionEvent event) throws IOException {
        if (!ValidatorHelper.validateNameNotEmpty(nameField.getText())) {
            showErrorAlert("Nazwa użytkownika jest wymagana!");
            return;
        }

        if (!ValidatorHelper.validateMailAddress(mailField.getText())) {
            showErrorAlert("Adres mailowy jest wymagany!");
            return;
        }

        if (!ValidatorHelper.validateClientBarcode(barcodeField.getText())){
            showErrorAlert("Kod kreskowy powinien składać się z 15 znaków!");
            return;
        }

        if (!ValidatorHelper.validatePhoneNumber(phoneField.getText())) {
            showErrorAlert("Numer telefonu powinien składać się z 9 znaków!");
            return;
        }


        if(customer == null)
            customer = new Customer();

        customer.setName(nameField.getText());
        customer.setAddress(addressField.getText());
        customer.setBarcode(barcodeField.getText());
        customer.setMail(mailField.getText());
        customer.setPhone(phoneField.getText());

        customerModule.addCustomer(customer);
        exitAction(event);
    }
}
