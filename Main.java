package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;
import java.util.EmptyStackException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle( "TD v0.01" );
        final ArrayList <MouseEvent> mouseClick = new ArrayList<>();
        final ArrayList <Sprite> AllEnemy = new ArrayList<>();


        Group root = new Group();
        Scene theScene = new Scene( root );
        primaryStage.setScene( theScene );
        Canvas canvas = new Canvas( 800, 600 );
        root.getChildren().add( canvas );


        theScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseClick.add(event);
            }
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image background = new Image("BackgroundTD.png");
        Sprite tower = new Sprite("TowerTD.png");
        Sprite Enemy = new Sprite("EnemyTD.png");
        AllEnemy.add(Enemy);

        gc.drawImage(background,0,0);
        //AddNew(AllEnemy);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(3000), ae -> {
            AddNew(AllEnemy);
        }));
        timeline.setCycleCount(100);
        timeline.play();


        new AnimationTimer(){

            @Override
            public void handle(long now) {
                gc.clearRect(0,0,800,600);

                gc.drawImage(background,0,0);
                if (mouseClick.size() != 0){
                    for(MouseEvent E : mouseClick){
                        tower.setPositionX(E.getX(),E.getY());
                        tower.render(gc);
                    }
                }
                for (Sprite enemy : AllEnemy){
                    enemy.render(gc);
                    Move(enemy);
                }
            }
        }.start();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void AddNew(ArrayList<Sprite> sprites){

        Sprite enemy = new Sprite("EnemyTD.png");
        sprites.add(enemy);

    }

    public static void Move(Sprite Enemy){
        if (Enemy.getPositionX() == 650 & Enemy.getPositionY() != 800){
            Enemy.setVelocityX(0,1);
            Enemy.update(1);
        } else if (Enemy.getPositionX() != 650){
            Enemy.setVelocityX(1,0);
            Enemy.update(1);;
        }
    }
}

