package Bomberman.entities;

import javafx.scene.image.Image;

/**
 * Tường, không thể bị phá hủy.
 * Bất kì đối tượng nào cũng không thể đi xuyên qua nó.
 */
public class Wall extends Entity {
    /**
     * Tạo ra 1 ô tường.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của tường.
     */
    public Wall(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        // Nothing here, i guess.
    }
}
