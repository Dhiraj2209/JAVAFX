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
        		new Item("Wheat", 33, 1000),
                new Item("Rice", 35, 1000),
                new Item("Basmati Rice", 95,1000),
                new Item("Bajra", 25, 1000),
                new Item("Jowar", 60, 1000),
                new Item("Maize", 32, 1000),
                new Item("Ragi", 70, 700),
                new Item("Chana Dal", 120, 100),
                new Item("Moong Dal", 130, 100),
                new Item("Urad Dal", 160, 100),
                new Item("Masoor Dal", 130, 100),
                new Item("Arhar Dal", 180, 100)
        );

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
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
        return name + " - ₹" + price + " - Qty: " + quantity;
    }
}
