package Bomberman.entities.Enemy;

import Bomberman.BombermanGame;
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
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        speed = 0.05;
        throughWall = false;
    }

    @Override
    public void moveDown() {
        super.moveDown();
        setImg(Sprite.movingSprite(Sprite.oneal_left1,
                Sprite.oneal_left2,
                Sprite.oneal_left3,
                BombermanGame.currTime,
                100).getFxImage());
    }

    @Override
    public void moveUp() {
        super.moveUp();
        setImg(Sprite.movingSprite(Sprite.oneal_right1,
                Sprite.oneal_right2,
                Sprite.oneal_right3,
                BombermanGame.currTime,
                100).getFxImage());
    }

    @Override
    public void moveRight() {
        super.moveRight();
        setImg(Sprite.movingSprite(Sprite.oneal_right1,
                Sprite.oneal_right2,
                Sprite.oneal_right3,
                BombermanGame.currTime,
                100).getFxImage());
    }

    @Override
    public void moveLeft() {
        super.moveLeft();
        setImg(Sprite.movingSprite(Sprite.oneal_left1,
                Sprite.oneal_left2,
                Sprite.oneal_left3,
                BombermanGame.currTime,
                100).getFxImage());
    }

    /**
     * Cập nhật vị trí của Oneal sau mỗi frame.
     */
    @Override
    public void update() {
        // Hàm di chuyển ở đây
        super.update();
        if (live && dying) {
            setImg(Sprite.animation(limit - 4000, BombermanGame.currTime, limit,
                    Sprite.oneal_dead,
                    Sprite.mob_dead1,
                    Sprite.mob_dead2,
                    Sprite.mob_dead3).getFxImage());
            if(BombermanGame.currTime > limit) {

                //System.out.println(BombermanGame.currTime);
                dying = false;
                BombermanGame.getEntities().remove(this);
                img = null;
            }
        }

    }

}
