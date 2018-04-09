package cashregister.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentWindowController implements Initializable {

    @FXML
    private TextField textFieldDisplay;

    @FXML
    private void handleEnterButtonAction(ActionEvent event) {
        String value = textFieldDisplay.getText();
        int number;

        try {
            number = Integer.parseInt(value);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Błąd");
            alert.setContentText("Wpisz poprawną kwotę");

            alert.showAndWait();
        }
        textFieldDisplay.clear();
    }

    @FXML
    private void handleNumberButtonAction(ActionEvent event) {
        String text = ((Button) event.getSource()).getText();
        textFieldDisplay.appendText(text);
    }

    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        textFieldDisplay.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}