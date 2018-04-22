package cashregister.controllers;

import cashregister.model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class NewClientWindowController {

    @FXML
    private TextField nameField, barcodeField, mailField, addressField, phoneField;

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void handleOkButtonAction(ActionEvent event) {
        Customer customer = new Customer();
        customer.setName(nameField.getText());
        customer.setAddress(addressField.getText());
        customer.setBarcode(barcodeField.getText());
        customer.setMail(mailField.getText());
        customer.setPhone(phoneField.getText());
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
