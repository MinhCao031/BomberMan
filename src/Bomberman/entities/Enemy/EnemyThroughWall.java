package Bomberman.entities.Enemy;

import Bomberman.BombermanGame;
import Bomberman.X;
import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.Random;

public class EnemyThroughWall extends Enemy {
    int timeSpawned;
    /**
     * Tạo ra 1 Enemy đi xuyên tường.
     * @param x   hoành độ (Trục Ox hướng sang phải).
     * @param y   tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của balloom.
     */
    public EnemyThroughWall(double x, double y, Image img) {
        super(x, y, img);
        throughBrick = true;
        timeSpawned = X.currentTime;
        mobDead = Sprite.custom_dead0;
        mobLeft = Sprite.custom_left0;
        mobRight = Sprite.custom_right0;
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
        if (X.currentTime - timeSpawned > 1000) super.dying();
    }

    /**
     * Cập nhật vị trí của balloom sau mỗi frame.
     */
    @Override
    public void update() {
        if (!alive) {
            img = Sprite.movingSprite1PeriodOnly(fadingTime / 4, timeDie, mobDead).getFxImage();
            if (X.currentTime - timeDie >= fadingTime) {
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
