package Bomberman.entities.Item;

import Bomberman.graphics.Sprite;

/**
 * Item giúp người chơi tăng số bomb tối đa có thể đặt (maxBomb).
 * Nếu maxBomb = 3 => tại mọi thời điểm chỉ có tối đa 3 quả bomb trên map.
 */
public class BombsItem extends Item {
    /**
     * Tạo ra 1 BombsItem.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     */
    public BombsItem(int x, int y) {
        super(x, y);
        img = Sprite.powerup_bombs.getFxImage();
    }

    @Override
    public void update() {
        //Gần như không có gì để update cả, chỉ chờ bomber lấy đc và nó sẽ biến mất.
    }
}
