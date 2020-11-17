package Bomberman.entities.Enemy;

import javafx.scene.image.Image;
import Bomberman.entities.Entity;

/**
 * Địch-có-thể-tự-di-chuyển nói chung.
 */
public abstract class Enemy extends Entity {
    /**
     * tốc độ di chuyển của địch.
     * đơn vị ô vuông/frame (hoặc * 16pixel/frame).
     */
    protected double speed = 10;
    /**
     * khả năng đi xuyên gạch của địch.
     */
    protected boolean throughWall = false;
    /**
     * còn sống: true.
     * đã chết: false.
     */
    protected boolean live = true;

    /**
     * Tạo ra 1 Enemy.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của Oneal.
     */
    public Enemy(int x, int y, Image img) {
        super( x, y, img);
    }

    /**
     * Cập nhật vị trí của Enemy sau mỗi frame.
     */
    @Override
    public void update() {
        // Hàm di chuyển được code ở từng hàm nào Overide lại hàm này.
    }
}
