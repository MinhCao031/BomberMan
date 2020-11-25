package Bomberman.entities.Enemy;

import javafx.scene.image.Image;

/**
 * Oneal là địch, di chuyển ngẫu nhiên.
 * Oneal có thể đuổi theo bomber nếu ở gần, cũng có thể tránh bomb (optional).
 */
public class Oneal extends Enemy{
    /**
     * Tạo ra 1 Oneal.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của Oneal.
     */
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        speed = 0.05;
        throughWall = false;
    }

    /**
     * Cập nhật vị trí của Oneal sau mỗi frame.
     */
    @Override
    public void update() {
        // Hàm di chuyển ở đây
        super.update();
    }

}
