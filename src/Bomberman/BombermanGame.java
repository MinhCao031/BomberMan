package Bomberman;

import Bomberman.entities.*;
import Bomberman.entities.Item.*;
import Bomberman.entities.Enemy.*;
import Bomberman.graphics.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static String keymap;
    public static Label timeCount;

    // Có thể hiểu canvas là 1 bức ảnh kích cỡ cố định
    // Còn GraphicsContext là 1 công cụ để vẽ canvas đó ở dạng 2D lên 1 node (Group, Pane, StackPane,...)
    private Canvas entityC;
    private Canvas stillObjectsC;
    private GraphicsContext entityGc;
    private GraphicsContext stillObjectsGc;

    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        entityC = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        entityGc = entityC.getGraphicsContext2D();
        stillObjectsC = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        stillObjectsGc = stillObjectsC.getGraphicsContext2D();

        // Tạo root container
        timeCount = new Label("Time");
        timeCount.setFont(new Font(24));
        Group root = new Group();
        root.getChildren().addAll(stillObjectsC, entityC);

        BorderPane inGame = new BorderPane();
        inGame.setTop(timeCount);
        inGame.setCenter(root);

        Scene scene = new Scene(inGame);
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
                // For experiment
                timeCount.setText("Time Count = " + l);
            }
        };
        timer.start();

        Bomber bomberman = new Bomber(2, 2, Sprite.player_down.getFxImage());
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
                    case SPACE:{
                        bomberman.createBomb(entities);
                        break;
                    }
                }
            }
        });
    }

    /**
     * Đọc map trong file text và lưu dữ liệu màn chơi trong 1 String.
     * @param player được tạo trong hàm start nên phải đưa vào hàm ở dạng tham số.
     * @return String chứa dữ liệu map.
     */
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
                        case 'x': {
                            Item escape = new Portal(j, i, Sprite.portal.getFxImage());
//                            object = new Brick(j, i, Sprite.brick.getFxImage());
//                            object.setItemInside(escape);
                            entities.add(escape);
                            break;
                        }
                        case 'b': {
                            Item bombPower = new BombsItem(j, i, Sprite.powerup_bombs.getFxImage());
//                            object = new Brick(j, i, Sprite.brick.getFxImage());
//                            object.setItemInside(bombPower);
                            entities.add(bombPower);
                            break;
                        }
                        case 'f': {
                            FlameItem flamePower = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
//                            object = new Brick(j, i, Sprite.brick.getFxImage());
//                            object.setItemInside(flamePower);
                            entities.add(flamePower);
                            break;
                        }
                        case 's': {
                            SpeedItem speedUp = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
//                            object = new Brick(j, i, Sprite.brick.getFxImage());
//                            object.setItemInside(speedUp);
                            entities.add(speedUp);
                            break;
                        }
                        case '1': {
                            Enemy enemy = new Balloom(j, i, Sprite.balloom_left1.getFxImage());
                            entities.add(enemy);
                            break;
                        }
                        case '2': {
                            Entity enemy = new Oneal(j, i, Sprite.oneal_left1.getFxImage());
                            entities.add(enemy);
                            break;
                        }
                        case 'p': {
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

    /**
     * Dùng để đếm giờ riêng cho 1 đối tượng nào đó, ví dụ bomb.
     */
    private class MyTimer extends AnimationTimer {
        /**
         * thời điểm hiện tại khi biến MyTimer nào đó được khởi tạo.
         */
        private long prevTime;

        @Override
        public void handle(long l) {
//            render();
//            update();
            long dt = l - prevTime;
            if(dt > 5e9) {
                prevTime = l;
            }
            // For experiment
            timeCount.setText(dt + " " + l + " " + prevTime);
        }
    }
}
