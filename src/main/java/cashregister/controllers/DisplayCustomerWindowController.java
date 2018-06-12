package cashregister.controllers;

import cashregister.model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Controller class for DisplayCustomerWindow
 */
public class DisplayCustomerWindowController {

    @FXML
    private Label name_label, barcode_label, mail_label, address_label, phone_label;

    @FXML
    private void initialize(){
    }

    /**
     * Function for displaying all stored information about the given customer
     * @param customer
     */
    public void initData(Customer customer) {
        name_label.setText(customer.getName());
        barcode_label.setText(customer.getBarcode());
        mail_label.setText(customer.getMail());
        address_label.setText(customer.getAddress());
        phone_label.setText(customer.getPhone());
    }

    /**
     * Function is activated when exit button is clicked, function closes current stage
     * @param event
     */
    @FXML
    private void handleOkButtonAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}

