package Bomberman.entities.Item;

import Bomberman.graphics.Sprite;

public class BrickPassItem extends Item {
    /**
     * Tạo ra 1 BrickPassItem.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     */
    public BrickPassItem(int x, int y) {
        super(x, y);
        img = Sprite.powerup_brickpass.getFxImage();
    }

    @Override
    public void update() {
        //Gần như không có gì để update cả, chỉ chờ bomber lấy đc và nó sẽ biến mất.
    }
}
