package Bomberman.entities.Enemy;

import javafx.scene.image.Image;

/**
 * Balloom là địch, di chuyển ngẫu nhiên.
 */
public class Balloom extends Enemy {
    /**
     * Tạo ra 1 Balloom.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của Oneal.
     */
    public Balloom(int x, int y, Image img) {
        super(x, y, img);
        speed = 1;
        throughWall = false;
    }

    /**
     * Cập nhật vị trí của Balloom sau mỗi frame.
     */
    @Override
    public void update() {
        // Hàm di chuyển ở đây
    }
}
