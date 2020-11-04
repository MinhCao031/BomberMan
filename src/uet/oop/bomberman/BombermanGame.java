package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Enemy.Ballon;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static String keymap;

//    private GraphicsContext gc;
    private GraphicsContext entityGc;
    private GraphicsContext stillObjectsGc;
//    private Canvas canvas;
    private Canvas entityC;
    private Canvas stillObjectsC;

    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
//        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
//        gc = canvas.getGraphicsContext2D();
        entityC = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        entityGc = entityC.getGraphicsContext2D();
        stillObjectsC = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        stillObjectsGc = stillObjectsC.getGraphicsContext2D();
        // Tao root container
        Group root = new Group();
        root.getChildren().addAll(stillObjectsC, entityC);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        Entity bomberman = new Bomber(1, 1, Sprite.player_down.getFxImage());
        keymap = createMap(bomberman);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case LEFT:{
                        bomberman.moveLeft();
                        break;
                    }
                    case RIGHT:{
                        bomberman.moveRight();
                        break;
                    }
                    case UP:{
                        bomberman.moveUp();
                        break;
                    }
                    case DOWN:{
                        bomberman.moveDown();
                        break;
                    }
                }
            }
        });
    }

    public String createMap(Entity player) {
        try {
            Scanner scf = new Scanner(new BufferedReader(new FileReader("res/levels/Level1.txt")));
            int lvl = scf.nextInt();
            int row = scf.nextInt();
            int col = scf.nextInt();
            String ans = scf.nextLine();
            for (int i = 0; i < row; ++i) {
                String rowOfChar = scf.nextLine();
                ans = ans.concat(rowOfChar);
                for (int j = 0; j < col; ++j) {
                    Entity object = new Grass(j, i, Sprite.grass.getFxImage());
                    char key = rowOfChar.charAt(j);
                    switch (key) {
                        case '#': {
                            object = new Wall(j, i, Sprite.wall.getFxImage());
                            break;
                        }
                        case '*': {
                            object = new Brick(j, i, Sprite.brick.getFxImage());
                            break;
                        }
                        case '1': {
                            stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            Enemy enemy = new Ballon(j, i, Sprite.balloom_left1.getFxImage());
                            entities.add(enemy);
                            break;
                        }
                        case '2': {
                            stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            Entity enemy = new Ballon(j, i, Sprite.oneal_left1.getFxImage());
                            entities.add(enemy);
                            break;
                        }
                        case 'p':{
                            stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            entities.add(player);
                            break;
                        }
                        default: {
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                        }
                    }
                    stillObjects.add(object);
                }
            }
            stillObjects.forEach(g -> g.render(stillObjectsGc));
            return ans;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(123);
            return "";
        }
    }

    public void update() {
        //entities.forEach(Entity::update);
    }

    public void render() {
        entityGc.clearRect(0, 0, entityC.getWidth(), entityC.getHeight());
        entities.forEach(g -> g.render(entityGc));
    }

    private class MyTimer extends AnimationTimer {
        private long prevTime = 0;

        @Override
        public void handle(long l) {
            long dt = l - prevTime;
            if(dt > 5e9) {
                prevTime = l;
            }
        }
    }
}
