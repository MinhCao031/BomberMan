package Bomberman.entities;

import javafx.scene.image.Image;

import javax.swing.*;

/**
 * Quả bomb được tạo ra bởi bomber khi ấn dấu cách.
 * Sau 1 thời gian cố định bomb sẽ tự nổ.
 */
public class Bomb extends Entity {
    /**
     * Phạm vi nổ của bomb.
     * Để static cho tiện.
     */
    protected static int flameLong = 1;
    /**
     * Tạo ra 1 Bomb.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của Bomb.
     */
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        // Đếm giờ chờ bomb nổ


    }
}
