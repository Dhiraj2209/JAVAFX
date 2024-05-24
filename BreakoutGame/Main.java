package application;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;
    private static final double PADDLE_WIDTH = 100;
    private static final double PADDLE_HEIGHT = 20;
    private static final double BALL_RADIUS = 10;
    private static final double BRICK_WIDTH = 60;
    private static final double BRICK_HEIGHT = 20;

    private double ballSpeedX = 3;
    private double ballSpeedY = 3;
    private Rectangle paddle;
    private Circle ball;
    private Timeline gameLoop;
    private Label countdownLabel;
    private Button restartButton;
    private int countdown = 3;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Pane gamePane = new Pane();

        // Create the paddle
        paddle = new Rectangle(PADDLE_WIDTH, PADDLE_HEIGHT, Color.BLUE);
        paddle.setLayoutX((WIDTH - PADDLE_WIDTH) / 2);
        paddle.setLayoutY(HEIGHT - PADDLE_HEIGHT - 10);

        // Create the ball
        ball = new Circle(BALL_RADIUS, Color.RED);
        ball.setLayoutX(WIDTH / 2);
        ball.setLayoutY(HEIGHT / 2);

        // Create bricks
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                Rectangle brick = new Rectangle(BRICK_WIDTH, BRICK_HEIGHT, Color.GREEN);
                brick.setLayoutX(j * (BRICK_WIDTH + 5) + 30);
                brick.setLayoutY(i * (BRICK_HEIGHT + 5) + 50);
                gamePane.getChildren().add(brick);
            }
        }

        gamePane.getChildren().addAll(paddle, ball);

        // Countdown label
        countdownLabel = new Label();
        countdownLabel.setStyle("-fx-font-size: 48px; -fx-text-fill: red;");
        StackPane countdownPane = new StackPane(countdownLabel);
        countdownPane.setAlignment(Pos.CENTER);

        // Restart button
        restartButton = new Button("Restart");
        restartButton.setVisible(false);
        restartButton.setOnAction(e -> restartGame());

        VBox controlBox = new VBox(10, countdownPane, restartButton);
        controlBox.setAlignment(Pos.CENTER);
        controlBox.setPrefWidth(200);

        root.setCenter(gamePane);
        root.setRight(controlBox);

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        // Handle paddle movement
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.LEFT && paddle.getLayoutX() > 0) {
                paddle.setLayoutX(paddle.getLayoutX() - 20);
            }
            if (event.getCode() == KeyCode.RIGHT && paddle.getLayoutX() < WIDTH - PADDLE_WIDTH) {
                paddle.setLayoutX(paddle.getLayoutX() + 20);
            }
        });

        // Game loop
        gameLoop = new Timeline(new KeyFrame(Duration.millis(20), e -> runGameLoop(gamePane)));
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        startCountdown();

        primaryStage.setTitle("Breakout Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startCountdown() {
        final Timeline countdownTimeline; // Define countdownTimeline as final

        countdownTimeline = new Timeline();
        countdownTimeline.getKeyFrames().add(
            new KeyFrame(Duration.seconds(1), e -> {
                countdownLabel.setText(Integer.toString(countdown));
                countdown--;
                if (countdown < 0) {
                    countdownTimeline.stop();
                    countdownLabel.setVisible(false);
                    gameLoop.play();
                }
            })
        );

        countdownTimeline.setCycleCount(countdown + 1);
        countdownTimeline.play();
    }



    private void runGameLoop(Pane gamePane) {
        ball.setLayoutX(ball.getLayoutX() + ballSpeedX);
        ball.setLayoutY(ball.getLayoutY() + ballSpeedY);

        // Check for wall collisions
        if (ball.getLayoutX() <= BALL_RADIUS || ball.getLayoutX() >= WIDTH - BALL_RADIUS) {
            ballSpeedX *= -1;
        }
        if (ball.getLayoutY() <= BALL_RADIUS) {
            ballSpeedY *= -1;
        }

        // Check for paddle collision
        if (ball.getBoundsInParent().intersects(paddle.getBoundsInParent())) {
            ballSpeedY *= -1;
        }

        // Check for brick collisions
        for (int i = 0; i < gamePane.getChildren().size(); i++) {
            if (gamePane.getChildren().get(i) instanceof Rectangle && gamePane.getChildren().get(i) != paddle) {
                Rectangle brick = (Rectangle) gamePane.getChildren().get(i);
                if (ball.getBoundsInParent().intersects(brick.getBoundsInParent())) {
                    gamePane.getChildren().remove(brick);
                    ballSpeedY *= -1;
                    break;
                }
            }
        }

        // Check for game over
        if (ball.getLayoutY() >= HEIGHT - BALL_RADIUS) {
            System.out.println("Game Over!");
            ballSpeedX = 0;
            ballSpeedY = 0;
            gameLoop.stop();
            restartButton.setVisible(true);
        }
    }

    private void restartGame() {
        // Reset the game state
        ball.setLayoutX(WIDTH / 2);
        ball.setLayoutY(HEIGHT / 2);
        ballSpeedX = 3;
        ballSpeedY = 3;

        // Remove existing bricks and recreate them
        Pane gamePane = (Pane) ball.getParent();
        gamePane.getChildren().removeIf(node -> node instanceof Rectangle && node != paddle);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                Rectangle brick = new Rectangle(BRICK_WIDTH, BRICK_HEIGHT, Color.GREEN);
                brick.setLayoutX(j * (BRICK_WIDTH + 5) + 30);
                brick.setLayoutY(i * (BRICK_HEIGHT + 5) + 50);
                gamePane.getChildren().add(brick);
            }
        }

        restartButton.setVisible(false);
        countdown = 3;
        countdownLabel.setVisible(true);
        startCountdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
