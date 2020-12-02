package Bomberman.entities.Enemy;

import Bomberman.BombermanGame;
import Bomberman.X;
import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.Random;

/**
 * Balloom là địch, di chuyển ngẫu nhiên.
 */
public class Balloom extends Enemy {
    /**
     * Tạo ra 1 Balloom.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của balloom.
     */
    public Balloom(int x, int y, Image img) {
        super(x, y, img);
        throughBrick = false;
//        mobDead = Sprite.balloom_dead;
//        mobLeft = Sprite.balloom_left;
//        mobRight = Sprite.balloom_right;
        mobDead = Sprite.custom_dead2;
        mobLeft = Sprite.custom_left2;
        mobRight = Sprite.custom_right2;
    }

    @Override
    public void moveLeft() {
        super.moveLeft();
    }

    @Override
    public void moveRight() {
        super.moveRight();
    }

    @Override
    public void moveUp() {
        super.moveUp();
    }

    @Override
    public void moveDown() {
        super.moveDown();
    }

    @Override
    public void move(String direction) {
        super.move(direction);
    }

    @Override
    void dying() {
        super.dying();
    }

    /**
     * Cập nhật vị trí của balloom sau mỗi frame.
     */
    @Override
    public void update() {
        if(!alive) {
            img = Sprite.movingSprite1PeriodOnly(fadingTime / 4, timeDie, mobDead).getFxImage();
            if(X.currentTime - timeDie >= fadingTime) {
                img = null;
                BombermanGame.getGameplay().getAnimations().remove(this);
            }
            return;
        }
        // Để tránh việc enemy di chuyển quá nhanh, updateDelay sẽ giảm số lần gọi hàm này.
        if (X.currentTime - updateTime >= updateDelay) {
            updateTime = X.currentTime;
            super.update();
            move(indirection[indirectionIndex]);
            if (Math.random() > 0.3 && X.isBothCoordinateOddInt(x, y, delta)) {
                //System.out.println("Balloon Try turning " + x + " " + y);
                indirectionIndex = new Random().nextInt(4);
            }
        }
    }
}
