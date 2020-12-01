package Bomberman.entities.Enemy;

import Bomberman.BombermanGame;
import Bomberman.X;
import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.Random;

/**
 * Oneal là địch, di chuyển ngẫu nhiên.
 * Oneal có thể đuổi theo bomber nếu ở gần, cũng có thể tránh bomb (optional).
 */
public class Oneal extends Enemy{
    /**
     * Tạo ra 1 Oneal.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của Oneal.
     */
    public Oneal(double x, double y, Image img) {
        super(x, y, img);
        throughBrick = false;
        throughBomb = false;
        mobDead = Sprite.oneal_dead;
        mobLeft = Sprite.oneal_left;
        mobRight = Sprite.oneal_right;
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
     * Cập nhật vị trí của Oneal sau mỗi frame.
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
        if (X.currentTime - updateTime >= updateDelay){
            updateTime = X.currentTime;
            super.update();
            move(indirection[indirectionIndex]);
            if (Math.random() > 0.3 && X.isBothCoordinateOddInt(x, y, delta)) {
                indirectionIndex = new Random().nextInt(4);
            }
            if (Math.random() > 0.6) {
                double newSpeed = speed + 0.01 * (new Random().nextInt(5) - 2);
                if (newSpeed > 0.1) speed = 0.1;
                else if (newSpeed < 0) speed = 0;
                else speed = newSpeed;
            }
        }


    }
}
