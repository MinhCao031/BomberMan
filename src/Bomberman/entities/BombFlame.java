package Bomberman.entities;

import javafx.scene.image.Image;

/**
 * BombFlame được tạo ra khi bomb nổ.
 */
public class BombFlame extends Entity {
    /**
     * Tạo ra 1 ô BombFlame.
     * @param x   hoành độ (Trục Ox hướng sang phải).
     * @param y   tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của BombFlame.
     */
    public BombFlame(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        // Tồn tại trong 1 thời gian rất ngắn (khoảng 1 giây), sau đó biến mất
    }
}

