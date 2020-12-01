package Bomberman.entities.Enemy;

import Bomberman.BombermanGame;
import Bomberman.X;
import Bomberman.entities.BombFlame;
import Bomberman.entities.Entity;
import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.List;
import java.util.Random;

/**
 * Địch-có-thể-tự-di-chuyển nói chung.
 */
public abstract class Enemy extends Entity {
    /**
     * Number of Enemies currently.
     */
    private static int numOfEnemy;
    /**
     * Number of Enemies currently.
     * @return
     */
    public static int getNumOfEnemy() {
        return numOfEnemy;
    }

    public static void setNumOfEnemy(int numOfEnemy) {
        Enemy.numOfEnemy = numOfEnemy;
    }

    /**
     * Gọi hàm này khi có 1 Enemy chết.
     */
    public static void reduceEnemy() {
        numOfEnemy--;
    }

    /**
     * Thời gian Enemy dần biến mất, thời gian của từng Sprite tĩnh.
     */
    final int fadingTime = 2000, timeAnimate = 200;
    /**
     * tốc độ di chuyển của địch.
     * đơn vị ô vuông/frame (hoặc * 16pixel/frame).
     */
    double speed;
    /**
     * khả năng đi xuyên chướng ngại của địch.
     */
    boolean throughBrick, throughBomb;
    /**
     * true nếu còn sống.
     */
    boolean alive = true;
    /**
     * Thời điểm enemy bị tiêu diệt
     */
    int timeDie;
    /**
     * Các biến kiểm soát tốc độ gọi hàm update
     */
    int updateTime,updateDelay;
    /**
     * Sai số vấp chướng ngại vật.
     */
    final double delta = 0.01;
    /**
     * Kiểm soát hướng đi của enemy.
     */
    int indirectionIndex;
    final String[] indirection = {"right", "down", "left", "up"};
    /**
     * Các Sprite tĩnh của enemy.
     */
    Sprite[] mobDead, mobLeft, mobRight;

    /**
     * Tạo ra 1 Enemy.
     *
     * @param x   hoành độ (Trục Ox hướng sang phải).
     * @param y   tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của Oneal.
     */
    public Enemy(double x, double y, Image img) {
        super(x, y, img);
        numOfEnemy++;
        speed = 0.04;
        indirectionIndex = 0;
        updateTime = X.currentTime;
        updateDelay = 10;
        mobDead = Sprite.balloom_dead;
        mobLeft = Sprite.balloom_left;
        mobRight = Sprite.balloom_right;
    }

    /**
     * @return true nếu còn sống
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Kiểm tra va chạm với chướng ngại vật.
     * @param inDirection hướng đi.
     * @return false nếu không đụng bất kì chướng ngại nào.
     */
    private boolean notEncounterObstacle(String inDirection) {
        switch (inDirection) {
            case "left":{
                int nextTileX = (int)Math.floor(x - 1);
                int nextTileYUp = (int)Math.floor(y);
                int nextTileYDown = nextTileYUp + 1;

                char nextTileUp = BombermanGame.getGameplay().keymap.charAt(nextTileX + nextTileYUp * BombermanGame.getGameplay().WIDTH);
                char nextTileDown = BombermanGame.getGameplay().keymap.charAt(nextTileX + nextTileYDown * BombermanGame.getGameplay().WIDTH);

                if(y - nextTileYUp < delta && X.howMighty(nextTileUp) < 1 && X.howMighty(nextTileDown) > 0) {
                    y = nextTileYUp;
                    //x -= speed;
                    //System.out.println(x + " " + y);
                    return true;
                } else if (nextTileYDown - y < delta && X.howMighty(nextTileDown) < 1 && X.howMighty(nextTileUp) > 0) {
                    y = nextTileYDown;
                    //x -= speed;
                    //System.out.println(x + " " + y);
                    return true;
                }
                if(X.howMighty(nextTileDown) > 0 || X.howMighty(nextTileUp) > 0) {
                    x = nextTileX + 1;
                    indirectionIndex = new Random().nextInt(4);
                    return false;
                }
                return true;
            }
            case "right":{
                int nextTileX = (int)Math.ceil(x + speed);
                int nextTileYUp = (int)Math.floor(y);
                int nextTileYDown = nextTileYUp + 1;

                char nextTileUp = BombermanGame.getGameplay().keymap.charAt(nextTileX + nextTileYUp * BombermanGame.getGameplay().WIDTH);
                char nextTileDown = BombermanGame.getGameplay().keymap.charAt(nextTileX + nextTileYDown * BombermanGame.getGameplay().WIDTH);

                if(y - nextTileYUp < delta && X.howMighty(nextTileUp) < 1 && X.howMighty(nextTileDown) > 0) {
                    y = nextTileYUp;
                    //x += speed;
                    //System.out.println(x + " " + y);
                    return true;
                } else if (nextTileYDown - y < delta && X.howMighty(nextTileDown) < 1 && X.howMighty(nextTileUp) > 0) {
                    y = nextTileYDown;
                    //x += speed;
                    //System.out.println(x + " " + y);
                    return true;
                }
                if(X.howMighty(nextTileDown) > 0 || X.howMighty(nextTileUp) > 0) {
                    x = nextTileX - 1;
                    indirectionIndex = new Random().nextInt(4);
                    return false;
                }
                return true;
            }
            case "up":{
                int nextTileY = (int)Math.floor(y - speed);
                int nextTileXUp = (int)Math.floor(x);
                int nextTileXDown = nextTileXUp + 1;

                char nextTileUp = BombermanGame.getGameplay().keymap.charAt(nextTileXUp + nextTileY * BombermanGame.getGameplay().WIDTH);
                char nextTileDown = BombermanGame.getGameplay().keymap.charAt(nextTileXDown + nextTileY * BombermanGame.getGameplay().WIDTH);

                if(x - nextTileXUp < delta && X.howMighty(nextTileUp) < 1 && X.howMighty(nextTileDown) > 0) {
                    x = nextTileXUp;
                    //y -= speed;
                    //System.out.println(x + " " + y);
                    return true;
                } else if (nextTileXDown - x < delta && X.howMighty(nextTileDown) < 1 && X.howMighty(nextTileUp) > 0) {
                    x = nextTileXDown;
                    //y -= speed;
                    //System.out.println(x + " " + y);
                    return true;
                }
                if(X.howMighty(nextTileDown) > 0 || X.howMighty(nextTileUp) > 0) {
                    y = nextTileY + 1;
                    indirectionIndex = new Random().nextInt(4);
                    return false;
                }
                return true;
            }
            case "down":{
                int nextTileY = (int)Math.ceil(y + speed);
                int nextTileXUp = (int)Math.floor(x);
                int nextTileXDown = nextTileXUp + 1;

                char nextTileUp = BombermanGame.getGameplay().keymap.charAt(nextTileXUp + nextTileY * BombermanGame.getGameplay().WIDTH);
                char nextTileDown = BombermanGame.getGameplay().keymap.charAt(nextTileXDown + nextTileY * BombermanGame.getGameplay().WIDTH);

                if(x - nextTileXUp < delta && X.howMighty(nextTileUp) < 1 && X.howMighty(nextTileDown) > 0) {
                    x = nextTileXUp;
                    //y += speed;
                    return true;
                } else if (nextTileXDown - x < delta && X.howMighty(nextTileDown) < 1 && X.howMighty(nextTileUp) > 0) {
                    x = nextTileXDown;
                    //y += speed;
                    return true;
                }
                if(X.howMighty(nextTileDown) > 0 || X.howMighty(nextTileUp) > 0) {
                    y = nextTileY - 1;
                    indirectionIndex = new Random().nextInt(4);
                    return false;
                }
                return true;
            }
            default: return false;
        }
    }

    /**
     * Các hàm di chuyển Enemy.
     */
    public void moveLeft() {
        if (!alive) return;
        img = Sprite.movingSprite(timeAnimate, mobLeft[1], mobLeft[0], mobLeft[2], mobLeft[0]).getFxImage();
        if (x - speed >= Math.floor(x) || notEncounterObstacle("left")) {
            x-=speed;
        }
    }
    public void moveRight() {
        if (!alive) return;
        img = Sprite.movingSprite(timeAnimate, mobRight[1], mobRight[0], mobRight[2], mobRight[0]).getFxImage();
        if (x + speed < Math.ceil(x) || notEncounterObstacle("right")) {
            x += speed;
        }
    }
    public void moveUp() {
        if (!alive) return;
        img = Sprite.movingSprite(timeAnimate, mobLeft[1], mobLeft[0], mobLeft[2], mobLeft[0]).getFxImage();
        if (y - speed >= Math.floor(y) || notEncounterObstacle("up")) {
            y -= speed;
        }
    }
    public void moveDown() {
        if (!alive) return;
        img = Sprite.movingSprite(timeAnimate, mobRight[1], mobRight[0], mobRight[2], mobRight[0]).getFxImage();
        if (y + speed < Math.ceil(y) || notEncounterObstacle("down")) {
            y+=speed;
        }
    }
    public void move(String direction) {
        if (speed > 0) switch (direction) {
            case "up": {moveUp(); break;}
            case "down": {moveDown(); break;}
            case "left": {moveLeft(); break;}
            case "right": {moveRight(); break;}
        }
    }

    /**
     * Enemy khi bị tiêu diệt.
     */
    void dying() {
        timeDie = X.currentTime;
        alive = false;
        numOfEnemy--;
        img = Sprite.movingSprite1PeriodOnly(fadingTime / 4, timeDie, mobDead).getFxImage();
        System.out.println(numOfEnemy);
    }

    /**
     * Cập nhật vị trí của Enemy sau mỗi frame.
     */
    @Override
    public void update() {
        List<Entity> entities = BombermanGame.getGameplay().getAnimations();
//        System.out.println("---------------------------------------------------------------------\n" + entities.size());
//        for (Entity e: entities) System.out.println("In entities: " + e.getClass() + " at " + (int)e.getX() + " " + (int)e.getY());

        int n = entities.size();
        for (int i = n - 1; i > 0; --i) {
            Entity e = entities.get(i);
            if (X.twoSqrCollide(x, y, e.getX(), e.getY())) {
                if (e instanceof BombFlame) { // Nếu enemy chạm tia lửa thì chết.
                    dying();
                    break;
                }
            }
        }

    }
}
