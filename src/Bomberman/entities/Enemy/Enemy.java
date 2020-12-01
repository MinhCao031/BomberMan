package Bomberman.entities.Enemy;

import Bomberman.BombermanGame;
import Bomberman.entities.Bomb;
import Bomberman.entities.BombFlame;
import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;
import Bomberman.entities.Entity;

import java.util.Random;

/**
 * Địch-có-thể-tự-di-chuyển nói chung.
 */
public abstract class Enemy extends Entity {
    /**
     * tốc độ di chuyển của địch.
     * đơn vị ô vuông/frame (hoặc * 16pixel/frame).
     */
    protected double speed;

    protected int indirectionIndex = 0;
    protected String[] indirection = {"right", "left", "up", "down"};
    /**
     * khả năng đi xuyên gạch của địch.
     */
    protected boolean throughWall = false;
    /**
     * còn sống: true.
     * đã chết: false.
     */
    protected boolean live = true;
    protected boolean dying = false;
    protected int limit;
    /**
     * Tạo ra 1 Enemy.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của Oneal.
     */
    public Enemy(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void moveLeft() {
        x -= speed;
    }

    @Override
    public void moveRight() {
        x += speed;
    }

    @Override
    public void moveUp() {
        y -= speed;
    }

    @Override
    public void moveDown() {
        y += speed;
    }

    /**
     * kiem tra va cham voi tuong ben trai va ben phai
     * @param inDirection
     * @return
     */



    private boolean encounterObstacle(String inDirection) {
        switch (inDirection) {
            case "left":{
                /**
                 * @param nextTileX: tọa độ X của entity sắp xa chạm.
                 * @param nextTileY: tọa độ Y của entity sắp xa chạm.
                 * @param nextTile: kí hiệu của tile tiếp theo trong keymap.
                 */
                int nextTileX = (int)Math.floor(x - speed);
                int nextTileY = (int)Math.floor(y);
                char nextTile = BombermanGame.keymap.charAt(nextTileX + nextTileY * BombermanGame.WIDTH);

                if(nextTile != '*' && nextTile != '#') {
                    moveLeft();
                    //System.out.println(x + " " + y);
                    return true;
                }
                if(nextTile == '*' || nextTile == '#') {
                    x = nextTileX + 1;
                    return false;
                }
                return false;
            }
            case "right":{
                int nextTileX = (int)Math.ceil(x + speed);
                int nextTileY = (int)Math.floor(y);

                char nextTile = BombermanGame.keymap.charAt(nextTileX + nextTileY * BombermanGame.WIDTH);

                if(nextTile != '*' && nextTile != '#') {
                    moveRight();
                    return true;
                }
                if(nextTile == '*' || nextTile == '#') {
                    x = nextTileX - 1;
                    return false;
                }
                return false;
            }
            case "up":{
                int nextTileY = (int) Math.floor(y - speed);
                int nextTileX = (int)Math.floor(x);

                char nextTile = BombermanGame.keymap.charAt(nextTileX + nextTileY * BombermanGame.WIDTH);

                if(nextTile != '#' && nextTile != '*') {
                    moveUp();
                    return true;
                }
                if(nextTile == '#' || nextTile == '*') {
                    y = nextTileY + 1;
                    return false;
                }
                return false;
            }
            case "down":{
                int nextTileY = (int)Math.ceil(y + speed);
                int nextTileX = (int)Math.floor(x);

                char nextTile = BombermanGame.keymap.charAt(nextTileX + nextTileY * BombermanGame.WIDTH);

                if(nextTile != '#' && nextTile != '*') {
                    moveDown();
                    //System.out.println(x + " " + y);
                    return true;
                }
                if(nextTile == '#' || nextTile == '*') {
                    y = nextTileY - 1;
                    return false;
                }
                return false;
            }
            default: return true;
        }
    }

    public void encounterFlame() {
        for (int i = 0; i < BombermanGame.getEntities().size(); i++) {
            Entity e = BombermanGame.getEntities().get(i);
            if (e instanceof BombFlame) {
                if (e.getX() + 1 >= x && e.getX() <= x && e.getY() + 1 >= y && e.getY() <= y) {
                    dying = true;
                    limit = BombermanGame.currTime + 4000;
                }
            }
        }
    }

    /**
     * Cập nhật vị trí của Enemy sau mỗi frame.
     */
    @Override
    public void update() {
        /**
         * nếu còn sống thì update enemy theo speed.
         * chuyển hướng enemy khi gặp chướng ngại vật.s
         */

        if(live && !dying) {
            if(!encounterObstacle(indirection[indirectionIndex])) {
                indirectionIndex = new Random().nextInt(4);
            }
            encounterFlame();
        }
    }
}
