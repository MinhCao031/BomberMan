package Bomberman.entities.Item;

import javafx.scene.image.Image;

/**
 * Item giúp người chơi tăng số bomb tối đa có thể đặt (maxBomb).
 * maxBomb = 1 => muốn đặt quả bomb nữa phải chờ bomb trước đó nổ.
 * maxBomb = 2 => có thể đặt cùng lúc 2 bomb, muốn đặt quả bomb thứ 3 phải chờ bomb trước đó nổ.
 * maxBomb = 3 => có thể đặt cùng lúc 3 bomb, muốn đặt quả bomb thứ 4 phải chờ bomb trước đó nổ.
 * and so on...
 */
public class BombsItem extends Item{
    /**
     * Tạo ra 1 BombsItem.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của BombsItem.
     */
    public BombsItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        //Gần như không có gì để update cả, chỉ chờ bomber lấy đc và nó sẽ biến mất.
    }
}
