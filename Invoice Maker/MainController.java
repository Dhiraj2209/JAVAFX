package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
        shopNameLabel.setText("Prakash Trading Co.");
        searchField.addEventHandler(KeyEvent.KEY_RELEASED, event -> filterItems());
    }

    private void filterItems() {
        String searchText = searchField.getText().toLowerCase();
        ObservableList<Item> filteredItems = items.stream()
                .filter(item -> item.getName().toLowerCase().contains(searchText))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        displayItems(filteredItems);
    }

//    private void displayItems(ObservableList<Item> items) {
//        itemTilePane.getChildren().clear();
//        for (Item item : items) {
//            VBox itemBox = new VBox(5);
//            itemBox.getStyleClass().add("item-box");
//
//            Label nameLabel = new Label(item.getName());
//            nameLabel.getStyleClass().add("label");
//            Label priceLabel = new Label("₹" + item.getPrice());
//            priceLabel.getStyleClass().add("label");
//            Label quantityLabel = new Label("Available Qty: " + item.getQuantity());
//            quantityLabel.getStyleClass().add("label");
//
//            HBox quantitySelector = new HBox(5);
//            Button decreaseButton = new Button("-");
//            TextField quantityField = new TextField("1");
//            quantityField.setPrefWidth(40); // Set a preferred width for the text field
//            Button increaseButton = new Button("+");
//            Button addButton = new Button("->");
//
//            decreaseButton.setOnAction(e -> {
//                int currentQty = Integer.parseInt(quantityField.getText());
//                if (currentQty > 1) {
//                    quantityField.setText(String.valueOf(currentQty - 1));
//                }
//            });
//
//            increaseButton.setOnAction(e -> {
//                int currentQty = Integer.parseInt(quantityField.getText());
//                if (currentQty < item.getQuantity()) {
//                    quantityField.setText(String.valueOf(currentQty + 1));
//                }
//            });
//
//            addButton.setOnAction(e -> addItemToInvoice(item, Integer.parseInt(quantityField.getText())));
//
//            quantitySelector.getChildren().addAll(decreaseButton, quantityField, increaseButton, addButton);
//
//            itemBox.getChildren().addAll(nameLabel, priceLabel, quantityLabel, quantitySelector);
//            itemTilePane.getChildren().add(itemBox);
//        }
//    }
    
    private void displayItems(ObservableList<Item> items) {
        itemTilePane.getChildren().clear();
        for (Item item : items) {
            VBox itemBox = new VBox(5);
            itemBox.getStyleClass().add("item-box");

            Label nameLabel = new Label(item.getName());
            nameLabel.getStyleClass().add("label");
            Label priceLabel = new Label("₹" + item.getPrice());
            priceLabel.getStyleClass().add("label");
            Label quantityLabel = new Label("Available Qty: " + item.getQuantity());
            quantityLabel.getStyleClass().add("label");

            HBox quantitySelector = new HBox(5);
            Button decreaseButton = new Button("-");
            TextField quantityField = new TextField("1");
            quantityField.setPrefWidth(50); // Set a preferred width for the text field
            Button increaseButton = new Button("+");
            Button addButton = new Button("→");

            decreaseButton.getStyleClass().add("button");
            increaseButton.getStyleClass().add("button");
            addButton.getStyleClass().add("add-button");

            decreaseButton.setOnAction(e -> {
                int currentQty = Integer.parseInt(quantityField.getText());
                if (currentQty > 1) {
                    quantityField.setText(String.valueOf(currentQty - 1));
                }
            });

            increaseButton.setOnAction(e -> {
                int currentQty = Integer.parseInt(quantityField.getText());
                if (currentQty < item.getQuantity()) {
                    quantityField.setText(String.valueOf(currentQty + 1));
                }
            });

            addButton.setOnAction(e -> addItemToInvoice(item, Integer.parseInt(quantityField.getText())));

            quantitySelector.getChildren().addAll(decreaseButton, quantityField, increaseButton, addButton);

            itemBox.getChildren().addAll(nameLabel, priceLabel, quantityLabel, quantitySelector);
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
    
    @FXML
    private TableView<Item> invoiceTableView;

    private void updateInvoice() {
        invoiceTableView.getItems().clear();
        double total = 0;

        TableColumn<Item, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Item, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());

        TableColumn<Item, Double> pricePerKgCol = new TableColumn<>("Price/kg");
        pricePerKgCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        TableColumn<Item, Double> totalPriceCol = new TableColumn<>("Total Price");
        totalPriceCol.setCellValueFactory(cellData -> {
            Item item = cellData.getValue();
            int selectedQuantity = invoiceItems.getOrDefault(item, 0); // Get the selected quantity for this item
            double totalPriceForItem = selectedQuantity * item.getPrice(); // Calculate total price for this item based on selected quantity
            return new SimpleDoubleProperty(totalPriceForItem).asObject(); // Return the total price
        });




        TableColumn<Item, Void> removeCol = new TableColumn<>("Remove");
        removeCol.setCellFactory(param -> new TableCell<>() {
            private final Button removeButton = new Button("❌");

            {
                removeButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    removeItemFromInvoice(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeButton);
                }
            }
        });

        invoiceTableView.getColumns().setAll(nameCol, quantityCol, pricePerKgCol, totalPriceCol, removeCol);

        for (Map.Entry<Item, Integer> entry : invoiceItems.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            total += item.getPrice() * quantity;
            item.setQuantity(item.getQuantity() - quantity); // Adjust the item's quantity

            invoiceTableView.getItems().add(item);
        }

        totalLabel.setText("Total: ₹" + total);
    }





//    private void updateInvoice() {
//        invoiceItemList.getChildren().clear();
//        double total = 0;
//
//        for (Map.Entry<Item, Integer> entry : invoiceItems.entrySet()) {
//            Item item = entry.getKey();
//            int quantity = entry.getValue();
//            double price = item.getPrice() * quantity;
//            total += price;
//
//            HBox invoiceItem = new HBox(10);
//            invoiceItem.setSpacing(10);
//            Label nameLabel = new Label(item.getName());
//            Label quantityLabel = new Label("Qty: " + quantity);
//            Label priceLabel = new Label("₹" + price);
//            Button removeButton = new Button("Remove");
//            removeButton.getStyleClass().add("remove-button");
//            removeButton.setOnAction(e -> removeItemFromInvoice(item));
//
//            HBox.setHgrow(nameLabel, Priority.ALWAYS);
//            HBox.setHgrow(quantityLabel, Priority.ALWAYS);
//            HBox.setHgrow(priceLabel, Priority.ALWAYS);
//            HBox.setHgrow(removeButton, Priority.ALWAYS);
//            removeButton.setMaxWidth(Double.MAX_VALUE);
//
//            invoiceItem.getChildren().addAll(nameLabel, quantityLabel, priceLabel, removeButton);
//            HBox.setHgrow(invoiceItem, Priority.ALWAYS);
//            invoiceItem.setAlignment(Pos.BASELINE_RIGHT);
//            invoiceItemList.getChildren().add(invoiceItem);
//        }
//
//        totalLabel.setText("Total: ₹" + total);
//
//        displayItems(items);
//    }

    
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
