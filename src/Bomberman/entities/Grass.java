package Bomberman.entities;

import javafx.scene.image.Image;

/**
 * Cỏ, không thể bị phá hủy.
 * Bất kì đối tượng nào cũng đè ảnh lên nó.
 */
public class Grass extends Entity {
    /**
     * Tạo ra 1 ô cỏ.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của cỏ.
     */
    public Grass(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {}
}
