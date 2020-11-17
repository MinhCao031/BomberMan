package Bomberman.entities.Item;

import javafx.scene.image.Image;

/**
 * Item giúp người chơi chạy nhanh hơn.
 */
public class SpeedItem extends Item{
    /**
     * Tạo ra 1 SpeedItem.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của SpeedItem.
     */
    public SpeedItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        //Gần như không có gì để update cả, chỉ chờ bomber lấy đc và nó sẽ biến mất.
    }
}
