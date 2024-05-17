package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MainController {

    @FXML
    private TextField searchField;

    @FXML
    private TilePane itemTilePane;

    @FXML
    private VBox invoiceVBox;

    @FXML
    private VBox invoiceItemList;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField customerMobileField;

    @FXML
    private TextField customerAddressField;

    @FXML
    private Label shopNameLabel;

    @FXML
    private Label totalLabel;

    private ObservableList<Item> items;

    private Map<Item, Integer> invoiceItems = new HashMap<>();

    public void setItems(ObservableList<Item> items) {
        this.items = items;
        displayItems(items);
    }

    @FXML
    void initialize() {
        shopNameLabel.setText("Your Shop Name");
        searchField.addEventHandler(KeyEvent.KEY_RELEASED, event -> filterItems());
    }

    private void filterItems() {
        String searchText = searchField.getText().toLowerCase();
        ObservableList<Item> filteredItems = items.stream()
                .filter(item -> item.getName().toLowerCase().contains(searchText))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        displayItems(filteredItems);
    }

    private void displayItems(ObservableList<Item> items) {
        itemTilePane.getChildren().clear();
        for (Item item : items) {
            VBox itemBox = new VBox(5);
            itemBox.getStyleClass().add("item-box");
            
            Label nameLabel = new Label(item.getName());
            nameLabel.getStyleClass().add("label");
            Label priceLabel = new Label("$" + item.getPrice());
            priceLabel.getStyleClass().add("label");
            Label quantityLabel = new Label("Available Qty: " + item.getQuantity());
            quantityLabel.getStyleClass().add("label");
            TextField quantityField = new TextField("1");
            Button addButton = new Button("Add to Invoice");
            addButton.getStyleClass().add("add-button");
            addButton.setOnAction(e -> addItemToInvoice(item, Integer.parseInt(quantityField.getText())));
            itemBox.getChildren().addAll(nameLabel, priceLabel, quantityLabel, quantityField, addButton);
            itemTilePane.getChildren().add(itemBox);
        }
    }

    private void addItemToInvoice(Item item, int quantity) {
        if (quantity > 0 && quantity <= item.getQuantity()) {
            invoiceItems.put(item, invoiceItems.getOrDefault(item, 0) + quantity);
            item.setQuantity(item.getQuantity() - quantity);
            updateInvoice();
        }
    }

    private void removeItemFromInvoice(Item item) {
        if (invoiceItems.containsKey(item)) {
            item.setQuantity(item.getQuantity() + invoiceItems.get(item));
            invoiceItems.remove(item);
            updateInvoice();
        }
    }

    private void updateInvoice() {
        invoiceItemList.getChildren().clear();
        double total = 0;

        for (Map.Entry<Item, Integer> entry : invoiceItems.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            double price = item.getPrice() * quantity;
            total += price;

            HBox invoiceItem = new HBox(10);
            Label nameLabel = new Label(item.getName());
            Label quantityLabel = new Label("Qty: " + quantity);
            Label priceLabel = new Label("$" + price);
            Button removeButton = new Button("Remove");
            removeButton.getStyleClass().add("remove-button");
            removeButton.setOnAction(e -> removeItemFromInvoice(item));

            invoiceItem.getChildren().addAll(nameLabel, quantityLabel, priceLabel, removeButton);
            invoiceItemList.getChildren().add(invoiceItem);
        }

        totalLabel.setText("Total: $" + total);

        // Re-display items to update their available quantity
        displayItems(items);
    }

    @FXML
    void handleSort(ActionEvent event) {
        items.sort((item1, item2) -> item1.getName().compareTo(item2.getName()));
        displayItems(items);
    }

    @FXML
    void handlePrintInvoice(ActionEvent event) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(invoiceVBox.getScene().getWindow())) {
            boolean success = printerJob.printPage(invoiceVBox);
            if (success) {
                printerJob.endJob();
            }
        }
    }
}
