package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private ObservableList<Item> items;

    @Override
    public void start(Stage primaryStage) throws Exception {
        items = FXCollections.observableArrayList(
                new Item("Apple", 1.0, 50),
                new Item("Banana", 0.5, 100),
                new Item("Carrot", 0.7, 200),
                new Item("Date", 1.2, 75)
        );

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.setItems(items);

        primaryStage.setTitle("Shop Billing System");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true); // Fullscreen mode
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Item {
    private String name;
    private double price;
    private int quantity;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + " - $" + price + " - Qty: " + quantity;
    }
}
