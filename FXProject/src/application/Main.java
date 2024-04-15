package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        String css = this.getClass().getResource("application.css").toExternalForm();
        
        stage.setTitle("Dhiraj Prajapati");
        Image icon = new Image("111.png");
        stage.getIcons().add(icon);
        
        stage.setScene(scene);
		scene.getStylesheets().add(css);
		stage.show();
		
		stage.setOnCloseRequest(event -> {
			event.consume();
			exit(stage);
		});
			
	}
	
	public void exit (Stage s) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("EXIT");
		alert.setHeaderText("You are about to exit from application");
		alert.setContentText("Do you want to save before exiting ?: ");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			System.out.println("You Successfully Exited application");
			s.close();
		}
	}
}
