package Bomberman.entities;

import javafx.scene.image.Image;
import Bomberman.entities.Item.Item;

public class Brick extends Entity {
    /**
     * Gạch sẽ nhả ra item này nếu bị bomb phá hủy.
     */
    Item item = null;

    /**
     * Tạo ra 1 Brick ko có item bên trong.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của Brick.
     */
    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    /**
     * Tạo ra 1 Brick có item bên trong.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của Brick.
     */
    public Brick(int x, int y, Image img, Item item_) {
        super(x, y, img);
        item = item_;
    }

    @Override
    public void update() {
        // Gần như ko có gì cả, chờ bomb phá hủy để nhả ra item nếu có.
    }
}
