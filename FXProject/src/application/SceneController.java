package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class SceneController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void switchToMain(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setTitle("Dhiraj Prajapati");
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToShape(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Shape.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		stage.setTitle("Shape Window");
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private Circle c;
	private double x;
	private double y;
	
	public void up(ActionEvent e) {
		System.out.println("UP");
		c.setCenterY(y-=10);
	}
	
	public void down(ActionEvent e) {
		System.out.println("DOWN");
		c.setCenterY(y+=10);
	}
	
	public void right(ActionEvent e) {
		System.out.println("RIGHT");
		c.setCenterX(x+=10);
	}
	
	public void left(ActionEvent e) {
		System.out.println("LEFT");
		c.setCenterX(x-=10);
	}
}
