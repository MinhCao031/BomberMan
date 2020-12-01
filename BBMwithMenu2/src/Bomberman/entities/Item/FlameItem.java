package Bomberman.entities.Item;

import Bomberman.graphics.Sprite;

/**
 * Item giúp bomb có thể nổ với phạm vi xa hơn.
 */
public class FlameItem extends Item{
    /**
     * Tạo ra 1 FlameItem.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     */
    public FlameItem(int x, int y) {
        super(x, y);
        img = Sprite.powerup_flames.getFxImage();
    }

    @Override
    public void update() {
        //Gần như không có gì để update cả, chỉ chờ bomber lấy đc và nó sẽ biến mất.
    }
}
