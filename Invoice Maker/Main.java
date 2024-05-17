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
        		new Item("Wheat", 30, 100),
                new Item("Rice", 50, 100),
                new Item("Bajra", 25, 100),
                new Item("Jowar", 28, 100),
                new Item("Maize", 22, 100),
                new Item("Barley", 26, 100),
                new Item("Ragi", 35, 100),
                new Item("Chana Dal", 60, 100),
                new Item("Moong Dal", 70, 100),
                new Item("Urad Dal", 80, 100),
                new Item("Masoor Dal", 65, 100),
                new Item("Arhar Dal", 90, 100)
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