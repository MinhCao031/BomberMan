package Bomberman.entities.Enemy;

import Bomberman.BombermanGame;
import Bomberman.X;
import Bomberman.entities.Bomber;
import Bomberman.entities.Entity;
import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

/**
 * Oneal là địch, di chuyển ngẫu nhiên.
 * Oneal có thể đuổi theo bomber nếu ở gần, cũng có thể tránh bomb (optional).
 */
public class Oneal extends Enemy{
    private ArrayList<int[]> guidePath;
    private int timeActiveAI;
    private int targetIndex;
    private final int aiDuration;

    /**
     * Tạo ra 1 Oneal.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của Oneal.
     */
    public Oneal(double x, double y, Image img) {
        super(x, y, img);
        throughBrick = false;
//        mobDead = Sprite.oneal_dead;
//        mobLeft = Sprite.oneal_left;
//        mobRight = Sprite.oneal_right;
        mobDead = Sprite.custom_dead3;
        mobLeft = Sprite.custom_left3;
        mobRight = Sprite.custom_right3;
        guidePath = new ArrayList<>(4);
        // AI
        aiActivated = false;
        timeActiveAI = 0;
        targetIndex = -1;
        aiDuration = 6000;
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

            // Not AI
            if (!aiActivated) {
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

                // Next Update will have a chance to activate AI
                int targetX;
                int targetY;
                if (BombermanGame.getGameplay().getAnimations().get(0) instanceof Bomber) {
                    Entity bomber = BombermanGame.getGameplay().getAnimations().get(0);
                    if (Math.random() > 0.8  && X.inRange(x, y, bomber.getX(), bomber.getY())) {
                        targetX = (int)(bomber.getX() + 0.5);
                        targetY = (int)(bomber.getY() + 0.5);
                        guidePath = X.findWay(BombermanGame.getGameplay().keymap, x, y, targetX, targetY);
                        if (guidePath.size() > 0) {
                            //System.out.println("Warning!!!");
                            aiActivated = true;
                            timeActiveAI = updateTime;
                            targetIndex = 0;
                            speed = 0.08;
                        }
                    }
                }
                if (!aiActivated && BombermanGame.getGameplay().getAnimations().get(1) instanceof Bomber) {
                    Entity bomber = BombermanGame.getGameplay().getAnimations().get(1);
                    if (Math.random() > 0.8 && X.inRange(x, y, bomber.getX(), bomber.getY())) {
                        targetX = (int)(bomber.getX() + 0.5);
                        targetY = (int)(bomber.getY() + 0.5);
                        guidePath = X.findWay(BombermanGame.getGameplay().keymap, x, y, targetX, targetY);
                        if (guidePath.size() > 0) {
                            //System.out.println("Warning!!!!!!");
                            aiActivated = true;
                            timeActiveAI = updateTime;
                            targetIndex = 1;
                            speed = 0.08;
                        }
                    }
                }
            } else {
                // AI
                indirectionIndex = X.findDirection(x, y, guidePath.get(0)[0], guidePath.get(0)[1], 0.01);

                if(indirectionIndex > -1 && indirectionIndex < 5) {
                    //System.out.println(indirectionIndex);
                    move(indirection[indirectionIndex]);
                    if (X.isAlmostThere(x, y, guidePath.get(0)[0], guidePath.get(0)[1], delta)) {
                        //System.out.println("Removed 1 point");
                        guidePath.remove(0);
                    }
                }
                if (indirectionIndex == -1){
                    //System.out.println("Removed 1 point, too close to move");
                    guidePath.remove(0);
                }
                if (getStuck || guidePath.size() == 0 || updateTime - timeActiveAI > aiDuration) {
                    //System.out.println("AI disabled.");
                    guidePath.clear();
                    aiActivated = false;
                    timeActiveAI = 0;
                    targetIndex = -1;
                }
            }
        }
    }
}
