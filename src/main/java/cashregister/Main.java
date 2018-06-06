package cashregister;

import cashregister.barcode.BarcodeReader;
import cashregister.controllers.MainWindowController;
import cashregister.modules.ModulesManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.transaction.Transactional;
import java.util.List;

public class Main extends Application {
    public Main() {
        BarcodeReader.initializeBarcode("COM3");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/LoginWindow.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Kasa fiskalna");
        Scene scene = new Scene (root,300,300);

        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                           @Override
                                           public void handle(WindowEvent event) {
                                               BarcodeReader.uinitializeBarcode();
                                           }
                                       });

        primaryStage.show();
    }

    public static void main(String[] args) {
        ModulesManager.initialize();
        launch(args);
    }

    private static Stage primaryStage;
    public static Stage getPrimaryStage()
    {
        return primaryStage;
    }

    private static MainWindowController mainWindowController;
    public static MainWindowController getMainWindowController() {
        return mainWindowController;
    }
    public static void setMainWindowController(MainWindowController controller) {
        mainWindowController = controller;
    }
}
