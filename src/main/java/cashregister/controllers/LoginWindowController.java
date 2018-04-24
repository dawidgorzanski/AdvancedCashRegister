package cashregister.controllers;

import cashregister.dao.interfaces.IUserDao;
import cashregister.model.User;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.IAuthenticationModule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

    public class LoginWindowController implements Initializable {

        @FXML
        TextField userIdTextField;

        @FXML
        PasswordField userPasswordField;

        IAuthenticationModule authenticationModule;

        @Override
        public void initialize(URL location, ResourceBundle resources) {

        }

        public LoginWindowController() {
            this.authenticationModule = ModulesManager.getObjectByType(IAuthenticationModule.class);
        }

        @FXML
        private void handleCancelButtonAction(ActionEvent actionEvent) throws IOException{
            Stage primaryStage = cashregister.Main.getPrimaryStage();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/PrimaryWindow.fxml")));
            primaryStage.setScene(scene);
            primaryStage.setWidth(300);
            primaryStage.setHeight(300);
            primaryStage.show();
        }

        @FXML
        private void handleCheckLoginButtonAction(ActionEvent event) throws IOException {
            String username = userIdTextField.getText();
            String password = userPasswordField.getText();

            if (authenticationModule.login(username, password)){
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/MainWindow.fxml")));
                Stage primaryStage = cashregister.Main.getPrimaryStage();
                primaryStage.setScene(scene);
                primaryStage.setMaximized(true);
                primaryStage.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Błędny login bądź hasło!");
                alert.show();
            }
        }
    }

    
