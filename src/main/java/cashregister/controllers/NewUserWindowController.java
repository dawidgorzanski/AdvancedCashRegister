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


public class NewUserWindowController {

    private IUserModule userModule;
    private User user;

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

    private void exitAction(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


    @FXML
    private void handleCancelButtonAction(ActionEvent event) throws IOException {
        exitAction(event);
    }

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

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawna wartość");
        alert.setHeaderText("Niepoprawna wartość");
        alert.setContentText("Proszę uzupełnić wszystkie pola.\nHasło musi zawierać co najmniej 4 znaki.");
        alert.showAndWait();
    }

    private void showNameAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawna wartość");
        alert.setHeaderText("Niepoprawna wartość");
        alert.setContentText("Ta nazwa użytkownika znajduje się już w bazie danych.");
        alert.showAndWait();
    }

    @FXML
    private void initialize()
    {
        isAdmin.getItems().addAll("TAK", "NIE");
    }

}
