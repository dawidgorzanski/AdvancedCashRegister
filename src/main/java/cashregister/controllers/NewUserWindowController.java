package cashregister.controllers;


import cashregister.helpers.ValidatorHelper;
import cashregister.model.User;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.IUserModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Controller class for NewUserWindow
 */
public class NewUserWindowController {

    private IUserModule userModule;
    private User user;

    /**
     * Initializes IUserModule by ModulesManager
     */
    public NewUserWindowController() { this.userModule = ModulesManager.getObjectByType(IUserModule.class);
    }

    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button enter, cancel;
    @FXML
    private ComboBox isAdmin;
    @FXML
    private Text title;
    private boolean edit;
    private String oldName;

    /**
     * Function for displaying name of a logged User. Checkes if logged User is an admin to show admin buttons
     * @param product
     */
    public void setUser(User user) {

        this.user = user;
        if(user != null) {
            this.nameField.setText(user.getName());

            if(user.getIsAdmin())
                isAdmin.getSelectionModel().select(0);
            else
                isAdmin.getSelectionModel().select(1);

            title.setText("Edytuj użytkownika");
        }
    }

    /**
     * Method for handling key events. Closes current stage when ESCAPE or ENTER key is clicked
     * @param key
     * @throws IOException
     */
    @FXML
    private void handleKeyAction(KeyEvent key) throws IOException {
        KeyCode keyCode = key.getCode();
        if (keyCode.equals(KeyCode.ENTER)) {
            enter.fire();
            return;
        }
        if (keyCode.equals(KeyCode.ESCAPE)) {
            cancel.fire();
            return;
        }
    }

    /**
     * Function closes current stage
     * @param event
     * @throws IOException
     */
    private void exitAction(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    /**
     * Function is activated when cancel button is clicked, function closes current stage
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleCancelButtonAction(ActionEvent event) throws IOException {
        exitAction(event);
    }

    /**
     * Function is activated when wrong OK button is clicked, validates entered data and creates new User with entered informations
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleOkButtonAction(ActionEvent event) throws IOException {
        String name = this.nameField.getText();
        if (!ValidatorHelper.validateName(name, this.userModule.getUserByUserName(name), this.edit, this.oldName )){
            showNameAlert();
            return;
        }
        if (!ValidatorHelper.validateInput(this.passwordField.getText(), this.nameField.getText(), this.isAdmin.getValue())) {
            showAlert();
            return;
        }
        if(user == null)
            user = new User();
        user.setName(nameField.getText());
        user.setPassword(passwordField.getText());
        if(isAdmin.getValue().equals("TAK"))
            user.setIsAdmin(true);
        else
            user.setIsAdmin(false);

        userModule.addUser(user);
        exitAction(event);

    }

    /**
     * Function is activated when not all the fields were filled, function shows an alert
     * @param content
     */
    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawna wartość");
        alert.setHeaderText("Niepoprawna wartość");
        alert.setContentText("Proszę uzupełnić wszystkie pola.\nHasło musi zawierać co najmniej 4 znaki.");
        alert.showAndWait();
    }

    /**
     * Function is activated when entered User name already exists, function shows an alert
     * @param content
     */
    private void showNameAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawna wartość");
        alert.setHeaderText("Niepoprawna wartość");
        alert.setContentText("Ta nazwa użytkownika znajduje się już w bazie danych.");
        alert.showAndWait();
    }

    /**
     * Function initializes isAdmin
     */
    @FXML
    private void initialize()
    {
        isAdmin.getItems().addAll("TAK", "NIE");
    }

}
