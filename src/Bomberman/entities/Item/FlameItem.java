package Bomberman.entities.Item;

import javafx.scene.image.Image;

/**
 * Item giúp bomb có thể nổ với phạm vi xa hơn.
 */
public class FlameItem extends Item{
    /**
     * Tạo ra 1 FlameItem.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của FlameItem.
     */
    public FlameItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        //Gần như không có gì để update cả, chỉ chờ bomber lấy đc và nó sẽ biến mất.
    }
}
