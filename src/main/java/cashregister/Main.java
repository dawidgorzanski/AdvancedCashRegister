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

    /**
     * Shows windor for logging
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/LoginWindow.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Kasa fiskalna");
        Scene scene = new Scene (root,335,400);
        javafx.scene.text.Font latoFont = javafx.scene.text.Font.loadFont(getClass().getResourceAsStream("/fonts/Lato-Regular.ttf"), 10);
        javafx.scene.text.Font latoBoldFont = javafx.scene.text.Font.loadFont(getClass().getResourceAsStream("/fonts/Lato-Bold.ttf"), 10);
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
    /**
     * Gets primaryStage
     * @return primaryStage
     */
    public static Stage getPrimaryStage()
    {
        return primaryStage;
    }

    private static MainWindowController mainWindowController;
    /**
     * Gets mainWindowController
     * @return mainWindowController
     */
    public static MainWindowController getMainWindowController() {
        return mainWindowController;
    }
    /**
     * Sets mainWindowController
     * @param controller
     */
    public static void setMainWindowController(MainWindowController controller) {
        mainWindowController = controller;
    }
}
