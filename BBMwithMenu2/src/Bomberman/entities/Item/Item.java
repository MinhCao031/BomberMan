package Bomberman.entities.Item;

import Bomberman.BombermanGame;
import Bomberman.entities.Entity;

/**
 * Item nói chung chỉ có bomber mói lấy đc item.
 * Bomb có thể phá hủy item.
 * Địch và item ko liên quan.
 */
public abstract class Item extends Entity {
    /**
     * Tạo ra 1 Item.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     */
    public Item(int x, int y) {
        super(x, y);
    }

    @Override
    public void update() {}

    /**
     * Xóa item khỏi map.
     */
    public void disappear() {
        img = null;
        //System.out.println("Trying to take this item at " + x + " " + y);
        BombermanGame.getGameplay().getMayVanish().remove(this);
        BombermanGame.getGameplay().renderObjectMayVanish();
    }
}
