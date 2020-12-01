package Bomberman;

import Bomberman.entities.Entity;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BombermanGame extends Application {
    private static final String[] mapLevel = {
        "res/levels/Level0.txt","res/levels/Level1.txt",
        "res/levels/Level2.txt","res/levels/Level3.txt",
        "res/levels/Level4.txt","res/levels/Level5.txt",
    };
    private static Gameplay gameplay;
    public static Gameplay getGameplay() { return gameplay; }

    private static final BorderPane mainMenu = new BorderPane();
    protected static Scene menuScene;
    private static Scene inGame1PScene, inGame2PScene;

    public static Scene getMenuScene() {
        return menuScene;
    }

    protected static Stage onlyStage;

    public static int currentLevel = 1;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        onlyStage = stage;

        VBox buttons = new VBox(20);
        Button play1P = new Button("1-Player Mode");
        Button play2P = new Button("2-Player Mode");
        Button quit = new Button("Quit");
        buttons.getChildren().addAll(play1P, play2P, quit);
        buttons.setAlignment(Pos.CENTER);

        mainMenu.setCenter(buttons);

        menuScene = new Scene(mainMenu, 992, 450);
        onlyStage.setScene(menuScene);
        onlyStage.show();

        BorderPane empty1 = new BorderPane();
        BorderPane empty2 = new BorderPane();

        inGame1PScene = new Scene(empty1, 992, 450);
        inGame2PScene = new Scene(empty2, 992, 450);

        play1P.setOnAction(event -> {
            BorderPane forward = showLevelThenPLay(currentLevel, false);
            menuScene.setRoot(forward);
        });
        play2P.setOnAction(event -> {
            BorderPane forward = showLevelThenPLay(currentLevel, true);
            menuScene.setRoot(forward);
        });
        quit.setOnAction(event -> onlyStage.close());
    }

    protected static BorderPane showLevelThenPLay(int levelComeNext, boolean twoPlayerMode) {
        AnimationTimer showTime = new AnimationTimer() {
            int startTime;
            boolean started = false;
            boolean forwarded = false;

            @Override
            public void handle(long now) {
                if(!started) {
                    X.globalTime = (int)(now/1e6);
                    startTime = X.globalTime;
                    started = true;
                } else {
                    X.globalTime = (int)(now/1e6);
                    if(X.globalTime - startTime > 3000 && !forwarded) {
                        forwarded = true;
                        if (twoPlayerMode) {
                            BorderPane lvl = setGame(levelComeNext, true);
                            lvl.addEventHandler(KeyEvent.KEY_PRESSED, Gameplay.twoPlayerHandle);
                            lvl.addEventHandler(KeyEvent.KEY_RELEASED,Gameplay.twoPlayerHandle);
                            inGame2PScene.setRoot(lvl);
                            onlyStage.setScene(inGame2PScene);
                        } else {
                            BorderPane lvl = setGame(levelComeNext, false);
                            lvl.addEventHandler(KeyEvent.KEY_PRESSED, Gameplay.onePlayerHandle);
                            lvl.addEventHandler(KeyEvent.KEY_RELEASED, Gameplay.onePlayerHandle);
                            inGame1PScene.setRoot(lvl);
                            onlyStage.setScene(inGame1PScene);
                        }
                        this.stop();
                    }
                }
            }
        };
        showTime.start();

        BorderPane ans = new BorderPane();
        Label levelName = new Label("Level " + levelComeNext);
        levelName.setFont(new Font(64));
        ans.setCenter(levelName);

        return ans;
    }

    private static BorderPane setGame(int level, boolean twoPlayerMode) {
        gameplay = new Gameplay(twoPlayerMode, mapLevel[level]);

        // Tạo root container
        Group root = new Group();
        root.getChildren().addAll(gameplay.getImmunitiesCanvas(), gameplay.getMayVanishCanvas(), gameplay.getAnimationsCanvas());

        Button pause = new Button("Pause");
        Button back = new Button("Back to menu");
        HBox topBar = new HBox(20);
        topBar.getChildren().addAll(pause, back, gameplay.timeCount);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topBar);
        borderPane.setCenter(root);

        if (twoPlayerMode) inGame2PScene.addEventHandler(KeyEvent.ANY, Gameplay.twoPlayerHandle);
        else inGame1PScene.addEventHandler(KeyEvent.ANY, Gameplay.onePlayerHandle);

        pause.setOnAction(event -> {
            if (pause.getText().equals("Pause")) {
                gameplay.pause();
                pause.setText("Continue");
                return;
            } else if (pause.getText().equals("Continue")) {
                gameplay.start();
                pause.setText("Pause");
            }
        });

        back.setOnAction(event -> {
            gameplay.setStatus(-1);
            gameplay.collapse();
            if (twoPlayerMode) {
                borderPane.removeEventHandler(KeyEvent.KEY_PRESSED, Gameplay.twoPlayerHandle);
                borderPane.removeEventHandler(KeyEvent.KEY_RELEASED, Gameplay.twoPlayerHandle);
            } else {
                borderPane.removeEventHandler(KeyEvent.KEY_PRESSED, Gameplay.onePlayerHandle);
                borderPane.removeEventHandler(KeyEvent.KEY_RELEASED, Gameplay.onePlayerHandle);
            }
            System.out.println("Event removed.");
            menuScene.setRoot(mainMenu);
            onlyStage.setScene(menuScene);
        });

        // Debug mode
        //System.out.println(keymap.length());
        //for (Entity e: immunities) System.out.println(e.getClass() + " at " + (int)e.getX() + " " + (int)e.getY());
        //for (Entity e: mayVanish) System.out.println(e.getClass() + " at " + (int)e.getX() + " " + (int)e.getY());
        //for (Entity e: gameplay.getAnimations()) System.out.println("In borderPane: " + e.getClass() + " at " + (int)e.getX() + " " + (int)e.getY());

        return borderPane;
    }


}
