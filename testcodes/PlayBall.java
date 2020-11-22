import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import common.PVector;

public class PlayBall extends Application {

    Pane playfield;
    AnimationTimer gameLoop;
    Scene scene;
    Ball ball;
    boolean mouseClick = false;
    boolean clickedOnce = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException{
        BorderPane root = new BorderPane();
        StackPane layerPane = new StackPane();
        playfield = new Pane();
        playfield.setPrefSize(800,600);
        layerPane.getChildren().addAll(playfield);
        root.setCenter(layerPane);
        scene = new Scene(root,800,600);
        primaryStage.setScene(scene);
        primaryStage.show();
        addBall();
        startGame();
    }

    public void startGame() throws InterruptedException{
        gameLoop = new AnimationTimer() {
            long time = System.currentTimeMillis();
            @Override
            public void handle(long l) {
                playfield.setOnMouseClicked(e -> mouseClick = true);
                if(mouseClick){
                    if(!clickedOnce) {
                        time = System.currentTimeMillis();
                        clickedOnce = true;
                    }
                    ball.userMove();
                }
                else{
                    ball.applyForce();
                    ball.move();
                }
                ball.checkBounds();
                ball.display();
                if(mouseClick && System.currentTimeMillis()-time>150) {
                    mouseClick = false;
                    clickedOnce = false;
                }
            }
        };
        gameLoop.start();
    }

    public void addBall(){
        Pane layer = playfield;
        double x = 400;
        double y = 200;
        PVector location = new PVector(x,y);
        PVector velocity = new PVector(0,0);
        PVector acceleration = new PVector(0,0);
        ball = new Ball(layer, location, velocity, acceleration);
    }
}