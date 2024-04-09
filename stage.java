import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    public static void main(String args[]){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Group root = new Group();
        Scene scene = new Scene(root, Color.SKYBLUE);

        stage.setTitle("Dhiraj Prajapati");
        stage.setWidth(550);
        stage.setHeight(550);
        stage.setResizable(false);

        stage.setFullScreen(true);
        stage.setFullScreenExitHint("You can't ESCAPE unless you press z");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("z"));

        stage.setScene(scene);
        stage.show();
    }
}
