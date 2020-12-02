package Bomberman;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

/**
 * Các biến để phát nhạc
 */
public class S {
    // BGM
    public static MediaPlayer BGM_inGame1 = new MediaPlayer(
            new Media(Paths.get("res/BGM/BGM_inGame1.wav").toUri().toString()));
//    public static MediaPlayer BGM_inGame2 = new MediaPlayer(
//            new Media(Paths.get("res/BGM/BGM_inGame2.m4a").toUri().toString()));
    public static MediaPlayer BGM_levelStart = new MediaPlayer(
            new Media(Paths.get("res/BGM/BGM_levelStart.wav").toUri().toString()));
    public static MediaPlayer BGM_levelFinished = new MediaPlayer(
            new Media(Paths.get("res/BGM/BGM_levelFinished.wav").toUri().toString()));
    public static MediaPlayer BGM_menu = new MediaPlayer(
            new Media(Paths.get("res/BGM/BGM_menu.wav").toUri().toString()));
    public static MediaPlayer BGM_completeAll = new MediaPlayer(
            new Media(Paths.get("res/BGM/BGM_completeAll.wav").toUri().toString()));

    // Sound effect
    public static MediaPlayer SE_bomberDie = new MediaPlayer(
            new Media(Paths.get("res/sound/SE_bomberDie.wav").toUri().toString()));
    public static MediaPlayer SE_bomberMove = new MediaPlayer(
            new Media(Paths.get("res/sound/SE_bomberMove.wav").toUri().toString()));
    public static MediaPlayer SE_bomSet = new MediaPlayer(
            new Media(Paths.get("res/sound/SE_bomSet.wav").toUri().toString()));
    public static MediaPlayer SE_explode = new MediaPlayer(
            new Media(Paths.get("res/sound/SE_explode.wav").toUri().toString()));
    public static MediaPlayer SE_itemTaken = new MediaPlayer(
            new Media(Paths.get("res/sound/SE_itemTaken.wav").toUri().toString()));
    public static MediaPlayer SE_portalOpen = new MediaPlayer(
            new Media(Paths.get("res/sound/SE_portalOpen.wav").toUri().toString()));

    protected static void setCycleCount() {
        BGM_inGame1.setCycleCount(-1);
        BGM_menu.setCycleCount(-1);
    }

    public static void stopPlayingAll() {
        BGM_completeAll.stop();
        BGM_inGame1.stop();
        BGM_levelStart.stop();
        BGM_levelFinished.stop();
        BGM_menu.stop();

        SE_bomberMove.stop();
        SE_bomberDie.stop();
        SE_bomSet.stop();
        SE_explode.stop();
        SE_itemTaken.stop();
        SE_portalOpen.stop();
    }
}
