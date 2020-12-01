package Bomberman.entities.Item;

import Bomberman.graphics.Sprite;

/**
 * Item giúp bomber miễn nhiễm với bomb.
 */
public class FlamePassItem extends Item{
    /**
     * Tạo ra 1 FlamePass Item.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     */
    public FlamePassItem(int x, int y) {
        super(x, y);
        img = Sprite.powerup_flamepass.getFxImage();
    }

    @Override
    public void update() {
        //Gần như không có gì để update cả, chỉ chờ bomber lấy đc và nó sẽ biến mất.
    }
}
