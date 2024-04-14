package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Controller {
	
	@FXML
	private Circle c;
	private double x;
	private double y;
	private Stage stage;
	private Scene scene;
	private Parent root;
	@FXML
	TextField name;
	
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
	
	public void switchToShape(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Shape.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		stage.setTitle("Shape Window");
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void displayname(ActionEvent e) throws IOException {
		
		String username = name.getText();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Userdata.fxml"));
		root = loader.load();
		
		SceneController sceneC = loader.getController();
		sceneC.displayName(username);
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		stage.setTitle(username);
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
