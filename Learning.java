import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Learning extends Application {
    public void start(Stage ps) {
        Button b = new Button("Ok");
        Scene s = new Scene(b, 250, 250);

        ps.setTitle("My");
        ps.setScene(s);
        ps.show();
    }

    public static void main(String[] args) {
        Application.launch(args); // corrected 'a' to 'args'
    }
}
