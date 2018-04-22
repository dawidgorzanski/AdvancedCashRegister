package cashregister.controllers;


import cashregister.model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;


public class NewClientWindowController {

    @FXML
    private TextField nameField, barcodeField, mailField, addressField, phoneField;

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawna wartość");
        alert.setHeaderText("Niepoprawna wartość");
        alert.setContentText("Proszę wpisać 6-cyfrowy kod kreskowy.");
        alert.showAndWait();
    }

    private boolean validateInput() {
        String s = barcodeField.getText();
        if (StringUtils.isNumeric(s) && s.length()==6 )
            return true;
        return false;
    }

    @FXML
    private void handleOkButtonAction(ActionEvent event) {
        if (!validateInput()) {
            showAlert();
            return;
        }
        Customer customer = new Customer();
        customer.setName(nameField.getText());
        customer.setAddress(addressField.getText());
        customer.setBarcode(barcodeField.getText());
        customer.setMail(mailField.getText());
        customer.setPhone(phoneField.getText());
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }
}
