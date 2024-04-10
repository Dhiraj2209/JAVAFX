package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		Group root = new Group();
        Scene scene = new Scene(root, Color.SKYBLUE);

        stage.setTitle("Dhiraj Prajapati");
        stage.setWidth(550);
        stage.setHeight(550);
//        stage.setResizable(false);
        Image icon = new Image("111.png");
        stage.getIcons().add(icon);

//        stage.setFullScreen(true);
//        stage.setFullScreenExitHint("You can't ESCAPE unless you press z");
//        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("z"));
        Text text = new Text();
        text.setText("Hello !");
        text.setX(50);
        text.setY(50);
        text.setFont(Font.font(50));

        Line line = new Line();
        line.setStartX(200);
        line.setStartY(200);
        line.setEndX(500);
        line.setEndY(200);
        line.setStrokeWidth(8);
        line.setRotate(2);
        
        Circle c = new Circle(150.0f, 400.0f, 25.f); //x,y,radius
        c.setStroke(Color.RED);
        c.setFill(Color.ALICEBLUE);
        
        Image img = new Image("314.jpg");
        ImageView imageView = new ImageView(img);
        imageView.setX(350);
        imageView.setY(350);
        imageView.setFitWidth(75);
        imageView.setFitHeight(90);
       
        root.getChildren().add(text);
        root.getChildren().add(line);
        root.getChildren().add(c);
        root.getChildren().add(imageView);
        stage.setScene(scene);
		
		stage.show();
	}
}
