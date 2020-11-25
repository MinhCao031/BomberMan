package Bomberman.entities.Enemy;

import Bomberman.BombermanGame;
import javafx.scene.image.Image;
import Bomberman.entities.Entity;

/**
 * Địch-có-thể-tự-di-chuyển nói chung.
 */
public abstract class Enemy extends Entity {
    /**
     * tốc độ di chuyển của địch.
     * đơn vị ô vuông/frame (hoặc * 16pixel/frame).
     */
    protected double speed;
    protected final double delta = 1;
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

    /**
     * Tạo ra 1 Enemy.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của Oneal.
     */
    public Enemy(int x, int y, Image img) {
        super( x, y, img);
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

                //Kiểm tra nếu tile tiếp theo khác wall hoặc brick thì đi tiếp. trả về giá trị false nếu ngược lại.
                //Tại sao khác wall hoặc brick? vì trong keymap vẫn còn item, player...
                //Lối logic: keymap vẫn lưu vị trí khởi tạo của enemy, khắc phục?
                if(nextTile != '*' && nextTile != '#') {
                    x -= speed;
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
                    x += speed;
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
                    y -= speed;
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
                    y += speed;
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

    /**
     * Cập nhật vị trí của Enemy sau mỗi frame.
     */
    @Override
    public void update() {
        /**
         * nếu còn sống thì update enemy theo speed.
         * chuyển hướng enemy khi gặp chướng ngại vật.s
         */

        if(live) {
            if(!encounterObstacle(indirection[indirectionIndex%4])) {
                indirectionIndex++;
            }
        }
    }
}
