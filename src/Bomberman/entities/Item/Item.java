package Bomberman.entities.Item;

import javafx.scene.image.Image;
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
     * @param img ảnh của Item.
     */
    public Item(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        // Được code ở từng hàm nào Overide lại hàm này.
    }

}
