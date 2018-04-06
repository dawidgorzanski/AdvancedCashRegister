package cashregister.controllers;

import cashregister.barcode.BarcodeReader;
import cashregister.barcode.IBarcodeReaderDataListener;
import cashregister.model.Product;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.util.Callback;

public class MainWindowController implements IBarcodeReaderDataListener {

    IProductsListModule productsListModule;

    public MainWindowController() {
        BarcodeReader.addListener(this);
        this.productsListModule = ModulesManager.getObjectByType(IProductsListModule.class);
    }

    @FXML
    private TextField textFieldDisplay;
    @FXML
    private TableView tableViewProducts = new TableView();

    @FXML
    private void handleEnterButtonAction(ActionEvent event) {
        String value = textFieldDisplay.getText();
        int number;

        try {
            number = Integer.parseInt(value);
            productsListModule.addProduct(number);
            this.setTableViewProducts();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Błąd");
            alert.setContentText("Wpisz poprawny kod");

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

    public void setTableViewProducts() {
        for (int i = 0; i < 2; i++) {
            TableColumn col = new TableColumn(" ");
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return null;
                }

                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().toString());
                }
            });

            tableViewProducts.getColumns().addAll(col);
        }

        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        ObservableList<Product> products = productsListModule.getShoppingList();
        for(Product item : products) {

            ObservableList<String> row = FXCollections.observableArrayList();
                row.add(item.getName());
                String text = Integer.toString(item.getQuantity()) + "  X  CENA";
                row.add(text);
            System.out.println("Row [1] added "+row );
            data.add(row);
        }
        tableViewProducts.setItems(data);
        tableViewProducts.refresh();
    }

    @Override
    public void barcodeValueArrived(String value) {

    }

}