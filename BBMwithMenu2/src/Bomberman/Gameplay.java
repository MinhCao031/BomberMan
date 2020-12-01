package Bomberman;

import Bomberman.entities.*;
import Bomberman.entities.Enemy.Balloom;
import Bomberman.entities.Enemy.Enemy;
import Bomberman.entities.Enemy.Oneal;
import Bomberman.entities.Item.*;
import Bomberman.graphics.Sprite;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gameplay {
    public static final KeyHandle keyHandle = new KeyHandle();
    public static final EventHandler<KeyEvent> onePlayerHandle = keyEvent -> {
        final KeyCode key = keyEvent.getCode();
        if (key == KeyCode.LEFT) {
            keyHandle.setP1left(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        } else if (key == KeyCode.RIGHT) {
            keyHandle.setP1right(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        } else if (key == KeyCode.UP) {
            keyHandle.setP1up(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        } else if (key == KeyCode.DOWN) {
            keyHandle.setP1down(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        } else if (key == KeyCode.B) {
            keyHandle.setP1setBomb(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        } else if (key == KeyCode.Z) {
            keyHandle.setP1detonateBomb(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        }
    };
    public static final EventHandler<KeyEvent> twoPlayerHandle = keyEvent -> {
        final KeyCode key = keyEvent.getCode();
        // Handle player2
        if (key == KeyCode.LEFT) {
            keyHandle.setP2left(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        } else if (key == KeyCode.RIGHT) {
            keyHandle.setP2right(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        } else if (key == KeyCode.UP) {
            keyHandle.setP2up(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        } else if (key == KeyCode.DOWN) {
            keyHandle.setP2down(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        } else if (key == KeyCode.ENTER) {
            keyHandle.setP2setBomb(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        } else if (key == KeyCode.SLASH) {
            keyHandle.setP2detonateBomb(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        }
        // Handle player1
        else if (key == KeyCode.A) {
            keyHandle.setP1left(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        } else if (key == KeyCode.D) {
            keyHandle.setP1right(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        } else if (key == KeyCode.W) {
            keyHandle.setP1up(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        } else if (key == KeyCode.S) {
            keyHandle.setP1down(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        } else if (key == KeyCode.B) {
            keyHandle.setP1setBomb(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        } else if (key == KeyCode.Z) {
            keyHandle.setP1detonateBomb(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
            keyEvent.consume();
        }
    };

    public final int WIDTH = 31;
    public final int HEIGHT = 13;
    public String keymap;
    public final Media mediaInGameBgm1 = new Media(Paths.get("res/BGM/inGameBGM1.m4a").toUri().toString());
    public final MediaPlayer mediaPlayer = new MediaPlayer(this.mediaInGameBgm1);
    public Label timeCount;
    private boolean gamePaused;

    // Bomber, Enemy, Bomb & Bomb's Flame
    private final List<Entity> animations = new ArrayList<>();
    // Brick & Items
    private final List<Entity> mayVanish = new ArrayList<>();
    // Grass, Wall & Portal
    private final List<Entity> immunities = new ArrayList<>();

    public List<Entity> getAnimations() {
        return animations;
    }

    public List<Entity> getMayVanish() {
        return mayVanish;
    }

    // Có thể hiểu canvas là 1 bức ảnh kích cỡ cố định
    private final Canvas animationsCanvas;
    private final Canvas mayVanishCanvas;
    private final Canvas immunitiesCanvas;

    public Canvas getAnimationsCanvas() {
        return animationsCanvas;
    }

    public Canvas getMayVanishCanvas() {
        return mayVanishCanvas;
    }

    public Canvas getImmunitiesCanvas() {
        return immunitiesCanvas;
    }

    // Còn GraphicsContext là 1 công cụ để vẽ canvas đó ở dạng 2D lên 1 node (Group, Pane, StackPane,...)
    private final GraphicsContext animationsGraphic;
    private final GraphicsContext mayVanishGraphic;
    private final GraphicsContext immunitiesGraphic;

//    public GraphicsContext getAnimationsGraphic() {
//        return animationsGraphic;
//    }
//
//    public GraphicsContext getMayVanishGraphic() {
//        return mayVanishGraphic;
//    }
//
//    public GraphicsContext getImmunitiesGraphic() {
//        return immunitiesGraphic;
//    }

    private final AnimationTimer timer;

    public void pause() {
        gamePaused = true;
    }

    public void start() {
        gamePaused = false;
    }

    /**
     * -1 nếu thua (bomber chết), 1 nếu thắng, 0 là đang chơi.
     */
    private int status;

//    public int getStatus() {
//        return status;
//    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Gameplay(boolean twoPlayerMode, String fileNameWithMap) {
        mediaPlayer.play();
        gamePaused = false;

        status = 0;
        this.animationsCanvas = new Canvas(Sprite.SCALED_SIZE * this.WIDTH, Sprite.SCALED_SIZE * this.HEIGHT);
        this.animationsGraphic = this.animationsCanvas.getGraphicsContext2D();

        this.mayVanishCanvas = new Canvas(Sprite.SCALED_SIZE * this.WIDTH, Sprite.SCALED_SIZE * this.HEIGHT);
        this.mayVanishGraphic = this.mayVanishCanvas.getGraphicsContext2D();

        this.immunitiesCanvas = new Canvas(Sprite.SCALED_SIZE * this.WIDTH, Sprite.SCALED_SIZE * this.HEIGHT);
        this.immunitiesGraphic = this.immunitiesCanvas.getGraphicsContext2D();

        Enemy.setNumOfEnemy(0);
        this.keymap = createMap(twoPlayerMode, fileNameWithMap);

        timeCount = new Label("Time");
        timeCount.setFont(new Font(24));

        timer = new AnimationTimer() {
            boolean isStarted = false;
            int timeStart;
            int timePlayed = 0;
            int delayWinTime = 0;

            /**
             * hàm này sẽ được gọi tối đa 800 lần 1 giây.
             * @param time thời gian hiện tại.
             */
            @Override
            public void handle(long time) {
                if (!isStarted) {
                    isStarted = true;
                    timeStart = (int) (time / 1e6);
                    //System.out.println("Ready to update");
                } else {
                    if (!gamePaused) {
                        timePlayed += (int) (time / 1e6 - X.currentTime);
                        renderEntityWithAnimations();
                        update();
                        timeCount.setText("Time left: " + (200000 - timePlayed) / 1e3);
                    }
                    if (status < 0 || timePlayed > 2e5) {
                        collapse();
                        BorderPane forward = BombermanGame.showLevelThenPLay(BombermanGame.currentLevel, twoPlayerMode);
                        BombermanGame.menuScene.setRoot(forward);
                        BombermanGame.onlyStage.setScene(BombermanGame.menuScene);
                    } else if (status == 1) {
                        gamePaused = true;
                        delayWinTime += (int) (time / 1e6 - X.currentTime);
                        if (delayWinTime > 3e3) {
                            collapse();
                            BombermanGame.currentLevel++;
                            BorderPane forward = BombermanGame.showLevelThenPLay(BombermanGame.currentLevel, twoPlayerMode);
                            BombermanGame.menuScene.setRoot(forward);
                            BombermanGame.onlyStage.setScene(BombermanGame.menuScene);
                            this.stop();
                        }
                    }
                }
                X.currentTime = (int) (time / 1e6);
            }
        };
        timer.start();

        // Debug mode
        //System.out.println(keymap.length());
//        for (Entity e: immunities) System.out.println(e.getClass() + " at " + (int)e.getX() + " " + (int)e.getY());
//        for (Entity e: mayVanish) System.out.println(e.getClass() + " at " + (int)e.getX() + " " + (int)e.getY());
//        for (Entity e: animations) System.out.println(e.getClass() + " at " + (int)e.getX() + " " + (int)e.getY());

        System.out.println("Lvl " + BombermanGame.currentLevel + " started.");
    }

    /**
     * Đọc map trong file text và lưu dữ liệu màn chơi trong 1 String.
     *
     * @return String chứa dữ liệu map.
     */
    private String createMap(boolean twoPlayerMode, String fileNameWithMap) {
        try {
            Scanner scf = new Scanner(new BufferedReader(new FileReader(fileNameWithMap)));
            int lvl = scf.nextInt();
            int row = scf.nextInt();
            int col = scf.nextInt();
            String ans = scf.nextLine();
            animations.add(new Bomber(1, 1, Sprite.player_down.getFxImage(), true));
            if (twoPlayerMode) animations.add(new Bomber(col - 2, row - 2, Sprite.player_down.getFxImage(), false));
            for (int i = 0; i < row; ++i) {
                String rowOfChar = scf.nextLine();
                ans = ans.concat(rowOfChar);
                for (int j = 0; j < col; ++j) {
                    switch (rowOfChar.charAt(j)) {
                        case '#': {
                            Wall wall = new Wall(j, i, Sprite.wall.getFxImage());
                            immunities.add(wall);
                            break;
                        }
                        case '*': {
                            Grass grass = new Grass(j, i, Sprite.grass.getFxImage());
                            immunities.add(grass);
                            Brick noItem = new Brick(j, i, Sprite.brick.getFxImage());
                            mayVanish.add(noItem);
                            break;
                        }
                        case 'x': {
                            Grass grass = new Grass(j, i, Sprite.grass.getFxImage());
                            immunities.add(grass);
                            Brick hasPortal = new Brick(j, i, Sprite.brick.getFxImage(), new Portal(j, i));
                            mayVanish.add(hasPortal);
                            break;
                        }
                        case 'b': {
                            Grass grass = new Grass(j, i, Sprite.grass.getFxImage());
                            immunities.add(grass);
                            Brick hasBomb = new Brick(j, i, Sprite.brick.getFxImage(), new BombsItem(j, i));
                            mayVanish.add(hasBomb);
                            break;
                        }
                        case 'f': {
                            Grass grass = new Grass(j, i, Sprite.grass.getFxImage());
                            immunities.add(grass);
                            Brick hasFlame = new Brick(j, i, Sprite.brick.getFxImage(), new FlameItem(j, i));
                            mayVanish.add(hasFlame);
                            break;
                        }
                        case 's': {
                            Grass grass = new Grass(j, i, Sprite.grass.getFxImage());
                            immunities.add(grass);
                            Brick hasSpeed = new Brick(j, i, Sprite.brick.getFxImage(), new SpeedItem(j, i));
                            mayVanish.add(hasSpeed);
                            break;
                        }
                        case 'w': {
                            Grass grass = new Grass(j, i, Sprite.grass.getFxImage());
                            immunities.add(grass);
                            Brick hasBrickPass = new Brick(j, i, Sprite.brick.getFxImage(), new BrickPassItem(j, i));
                            mayVanish.add(hasBrickPass);
                            break;
                        }
                        case 'd': {
                            Grass grass = new Grass(j, i, Sprite.grass.getFxImage());
                            immunities.add(grass);
                            Brick hasDetonator = new Brick(j, i, Sprite.brick.getFxImage(), new DetonatorItem(j, i));
                            mayVanish.add(hasDetonator);
                            break;
                        }
                        case 'o': {
                            Grass grass = new Grass(j, i, Sprite.grass.getFxImage());
                            immunities.add(grass);
                            Brick hasBombPass = new Brick(j, i, Sprite.brick.getFxImage(), new BombPassItem(j, i));
                            mayVanish.add(hasBombPass);
                            break;
                        }
                        case 'i': {
                            Grass grass = new Grass(j, i, Sprite.grass.getFxImage());
                            immunities.add(grass);
                            Brick hasImmune = new Brick(j, i, Sprite.brick.getFxImage(), new FlamePassItem(j, i));
                            mayVanish.add(hasImmune);
                            break;
                        }
                        case '1': {
                            Grass grass = new Grass(j, i, Sprite.grass.getFxImage());
                            immunities.add(grass);
                            Enemy balloom = new Balloom(j, i, Sprite.balloom_left[0].getFxImage());
                            animations.add(balloom);
                            break;
                        }
                        case '2': {
                            Grass grass = new Grass(j, i, Sprite.grass.getFxImage());
                            immunities.add(grass);
                            Enemy oneal = new Oneal(j, i, Sprite.oneal_left[0].getFxImage());
                            animations.add(oneal);
                            break;
                        }
                        case 'p': {
//                            Grass grass = new Grass(j, i, Sprite.grass.getFxImage());
//                            immunities.add(grass);
//                            Bomber player = new Bomber(2, 2, Sprite.player_down.getFxImage(), false);
//                            animations.add(player);
//                            break;
                        }
                        default: {
                            Grass grass = new Grass(j, i, Sprite.grass.getFxImage());
                            immunities.add(grass);
                        }
                    }
                }
            }
            immunities.forEach(g -> g.render(immunitiesGraphic));
            mayVanish.forEach(g -> g.render(mayVanishGraphic));
            animations.forEach(g -> g.render(animationsGraphic));
            return ans;
        } catch (Exception e) {
            System.out.println(fileNameWithMap);
            e.printStackTrace();
            System.exit(123);
            return "";
        }
    }

    /**
     * Cập nhật trạng thái cho từng đối tượng.
     */
    private void update() {  //entities.forEach(Entity::update);
        for (int i = animations.size() - 1; i >= 0; --i) animations.get(i).update();
    }

    /**
     * Hiển thị hình ảnh của các đối tượng chuyển động (Entity) lên cửa sổ.
     */
    private void renderEntityWithAnimations() {
        animationsGraphic.clearRect(0, 0, animationsCanvas.getWidth(), animationsCanvas.getHeight());
        animations.forEach(g -> g.render(animationsGraphic));
    }

    /**
     * Hiển thị hình ảnh của 1 số đối tượng lên cửa sổ.
     */
    public void renderObjectMayVanish() {
        mayVanishGraphic.clearRect(0, 0, mayVanishCanvas.getWidth(), mayVanishCanvas.getHeight());
        mayVanish.forEach(g -> g.render(mayVanishGraphic));
    }

    protected void collapse() {
        if (status != 0) {
            animations.clear();
            mayVanish.clear();
            immunities.clear();
            mediaPlayer.stop();
            keyHandle.reset();
            timer.stop();
        }
    }

}
