package cashregister;

import cashregister.barcode.BarcodeReader;
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
        BarcodeReader.initializeBarcode("COM4");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginWindow.fxml"));
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
}
