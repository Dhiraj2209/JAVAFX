package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root, Color.SKYBLUE);

        stage.setTitle("Dhiraj Prajapati");
//        stage.setResizable(false);
        Image icon = new Image("111.png");
        stage.getIcons().add(icon);

//        stage.setFullScreen(true);
//        stage.setFullScreenExitHint("You can't ESCAPE unless you press z");
//        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("z"));
        stage.setScene(scene);
		
		stage.show();
	}
}
