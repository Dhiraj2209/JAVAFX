package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

public class Controller {
	
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
