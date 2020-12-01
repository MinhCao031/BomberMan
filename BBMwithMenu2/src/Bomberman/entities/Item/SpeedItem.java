package Bomberman.entities.Item;

import Bomberman.graphics.Sprite;

/**
 * Item giúp người chơi chạy nhanh hơn.
 */
public class SpeedItem extends Item{
    /**
     * Tạo ra 1 SpeedItem.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     */
    public SpeedItem(int x, int y) {
        super(x, y);
        img = Sprite.powerup_speed.getFxImage();
    }

    @Override
    public void update() {}
}
