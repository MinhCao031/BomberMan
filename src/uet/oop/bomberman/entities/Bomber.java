package uet.oop.bomberman.entities;

import com.sun.javafx.geom.Rectangle;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    private int left;
    private int right;
    private int up;
    private int down;
    private double speed;
    private double delta;

    private boolean encounterObstacle(String inDirection) {
        switch (inDirection) {
            case "left":{
                int nextTileX = (int)Math.floor(x - 1);
                int nextTileYUp = (int)Math.floor(y);
                int nextTileYDown = nextTileYUp + 1;

                char nextTileUp = BombermanGame.keymap.charAt(nextTileX + nextTileYUp * BombermanGame.WIDTH);
                char nextTileDown = BombermanGame.keymap.charAt(nextTileX + nextTileYDown * BombermanGame.WIDTH);

                if(y - nextTileYUp < delta && nextTileUp != '#' && nextTileUp != '*'
                && (nextTileDown == '#' || nextTileDown == '*')) {
                    y = nextTileYUp;
                    x -= speed;
                    //System.out.println(x + " " + y);
                    return true;
                } else if (nextTileYDown - y < delta && nextTileDown != '#' && nextTileDown != '*'
                && (nextTileUp == '#' || nextTileUp == '*')) {
                    y = nextTileYDown;
                    x -= speed;
                    //System.out.println(x + " " + y);
                    return true;
                }
                if(nextTileUp == '#' || nextTileUp == '*' || nextTileDown == '#' || nextTileDown == '*') {
                    x = nextTileX + 1;
                    return true;
                }
                return false;
            }
            case "right":{
                int nextTileX = (int)Math.ceil(x + 1);
                int nextTileYUp = (int)Math.floor(y);
                int nextTileYDown = nextTileYUp + 1;

                char nextTileUp = BombermanGame.keymap.charAt(nextTileX + nextTileYUp * BombermanGame.WIDTH);
                char nextTileDown = BombermanGame.keymap.charAt(nextTileX + nextTileYDown * BombermanGame.WIDTH);

                if(y - nextTileYUp < delta && nextTileUp != '#' && nextTileUp != '*'
                && (nextTileDown == '#' || nextTileDown == '*')) {
                    y = nextTileYUp;
                    x += speed;
                    //System.out.println(x + " " + y);
                    return true;
                } else if (nextTileYDown - y < delta && nextTileDown != '#' && nextTileDown != '*'
                && (nextTileUp == '#' || nextTileUp == '*')) {
                    y = nextTileYDown;
                    x += speed;
                    //System.out.println(x + " " + y);
                    return true;
                }
                if(nextTileUp == '#' || nextTileUp == '*' || nextTileDown == '#' || nextTileDown == '*') {
                    x = nextTileX - 1;
                    return true;
                }
                return false;
            }
            case "up":{
                int nextTileY = (int)Math.floor(y - 1);
                int nextTileXUp = (int)Math.floor(x);
                int nextTileXDown = nextTileXUp + 1;

                char nextTileUp = BombermanGame.keymap.charAt(nextTileXUp + nextTileY * BombermanGame.WIDTH);
                char nextTileDown = BombermanGame.keymap.charAt(nextTileXDown + nextTileY * BombermanGame.WIDTH);

                if(x - nextTileXUp < delta && nextTileUp != '#' && nextTileUp != '*'
                && (nextTileDown == '#' || nextTileDown == '*')) {
                    x = nextTileXUp;
                    y -= speed;
                    //System.out.println(x + " " + y);
                    return true;
                } else if (nextTileXDown - x < delta && nextTileDown != '#' && nextTileDown != '*'
                && (nextTileUp == '#' || nextTileUp == '*')) {
                    x = nextTileXDown;
                    y -= speed;
                    //System.out.println(x + " " + y);
                    return true;
                }
                if(nextTileUp == '#' || nextTileUp == '*' || nextTileDown == '#' || nextTileDown == '*') {
                    y = nextTileY + 1;
                    return true;
                }
                return false;
            }
            case "down":{
                int nextTileY = (int)Math.ceil(y + 1);
                int nextTileXUp = (int)Math.floor(x);
                int nextTileXDown = nextTileXUp + 1;

                char nextTileUp = BombermanGame.keymap.charAt(nextTileXUp + nextTileY * BombermanGame.WIDTH);
                char nextTileDown = BombermanGame.keymap.charAt(nextTileXDown + nextTileY * BombermanGame.WIDTH);

                if(x - nextTileXUp < delta && nextTileUp != '#' && nextTileUp != '*'
                && (nextTileDown == '#' || nextTileDown == '*')) {
                    x = nextTileXUp;
                    y += speed;
                    //System.out.println(x + " " + y);
                    return true;
                } else if (nextTileXDown - x < delta && nextTileDown != '#' && nextTileDown != '*'
                && (nextTileUp == '#' || nextTileUp == '*')) {
                    x = nextTileXDown;
                    y += speed;
                    //System.out.println(x + " " + y);
                    return true;
                }
                if(nextTileUp == '#' || nextTileUp == '*' || nextTileDown == '#' || nextTileDown == '*') {
                    y = nextTileY - 1;
                    return true;
                }
                return false;
            }
            default: return true;
        }
    }

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        left = 0; right = 0;
        up = 0; down = 0;
        speed = 0.1; delta = 0.25;
    }

    public void moveLeft() {
        if(left / 3 == 0) {
            this.img = Sprite.player_left_1.getFxImage();
        } else {
            this.img = Sprite.player_left_2.getFxImage();
        }
        left = ++left%6;
        if (x - speed >= Math.floor(x) || !encounterObstacle("left")) {
            x-=speed;
        }
        //System.out.println(x + " " + y);
    }

    public void moveRight() {
        if(right / 3 == 0) {
            this.img = Sprite.player_right_1.getFxImage();
        } else {
            this.img = Sprite.player_right_2.getFxImage();
        }
        right = ++right%6;
        if (x + speed - 2/16 < Math.ceil(x) || !encounterObstacle("right")) {
            x += speed;
        }
        //System.out.println(x + " " + y);
    }

    public void moveUp() {
        if(up / 3 == 0) {
            this.img = Sprite.player_up_1.getFxImage();
        } else {
            this.img = Sprite.player_up_2.getFxImage();
        }
        up = ++up%6;
        if (y - speed >= Math.floor(y) || !encounterObstacle("up")) {
            y -= speed;
        }
        //System.out.println(x + " " + y);
    }

    public void moveDown() {
        if(down / 3 == 0) {
            this.img = Sprite.player_down_1.getFxImage();
        } else {
            this.img = Sprite.player_down_2.getFxImage();
        }
        down = ++down%6;
        if (y + speed < Math.ceil(y) || !encounterObstacle("down")) {
            y+=speed;
        }
        //System.out.println(x + " " + y);
    }

    @Override
    public void update() {}
}
