package cashregister.controllers;

import cashregister.dao.interfaces.IUserDao;
import cashregister.model.User;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.IAuthenticationModule;
import cashregister.modules.interfaces.IUserModule;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
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

        @FXML
        private Button enter;

        IAuthenticationModule authenticationModule;

        private IUserModule userModule;

        @Override
        public void initialize(URL location, ResourceBundle resources) {

        }

        /**
         * Initializes IAuthenticationModule and IUserModule by ModulesManager
         */
        public LoginWindowController() {
            this.authenticationModule = ModulesManager.getObjectByType(IAuthenticationModule.class);
            this.userModule = ModulesManager.getObjectByType(IUserModule.class);
        }

        /**
         * Exits the application when quit button is clicked
         * @param actionEvent
         * @throws IOException
         */
        @FXML
        private void handleQuitButtonAction(ActionEvent actionEvent) throws IOException{
            Platform.exit();
            System.exit(0);
        }

        /**
         * Tries to log in when enter is clicked
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
        }

        /**
         * Sets width and height of Stage passed as parameter to max values
         * @param primaryStage
         */
        private void maximizeScreen(Stage primaryStage)//used before Java 8
        {
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();

            primaryStage.setX(bounds.getMinX());
            primaryStage.setY(bounds.getMinY());
            primaryStage.setWidth(bounds.getWidth());
            primaryStage.setHeight(bounds.getHeight());
        }

        /**
         * Function is activated when login button is clicked, function checks if username and password are correct
         * and if they are then user is logged in and main window is showed, otherwise alert with message is showed
         * @param event
         * @throws IOException
         */
        @FXML
        private void handleCheckLoginButtonAction(ActionEvent event) throws IOException {
            String username = userIdTextField.getText();
            String password = userPasswordField.getText();

            if ( authenticationModule.login(username, password) ){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                cashregister.Main.setMainWindowController(fxmlLoader.getController());
                Stage primaryStage = cashregister.Main.getPrimaryStage();
                primaryStage.setScene(scene);
                maximizeScreen(primaryStage); //primaryStage.setMaximized(true);

                primaryStage.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Błędny login bądź hasło!");
                alert.show();
            }
        }
    }

    
