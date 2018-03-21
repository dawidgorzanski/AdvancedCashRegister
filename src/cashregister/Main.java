package cashregister;

import cashregister.barcode.BarcodeReader;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    public Main() {
        BarcodeReader.initializeBarcode("COM4");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/cashregister/gui/MainWindow.fxml"));
        primaryStage.setTitle("Kasa fiskalna");
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root, 300, 275));

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                           @Override
                                           public void handle(WindowEvent event) {
                                               BarcodeReader.uinitializeBarcode();
                                           }
                                       });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
