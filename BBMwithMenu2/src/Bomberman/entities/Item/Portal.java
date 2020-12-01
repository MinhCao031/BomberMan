package Bomberman.entities.Item;

import Bomberman.graphics.Sprite;

/**
 * "Item" này đặc biệt hơn các item khác.
 * Cánh cổng để giúp người chơi kết thúc thắng lợi 1 màn chơi.
 * Điều kiện: không có địch nào còn sống.
 * Mặc dù bản chất ko phải là item nhưng nó vẫn có tính chất của 1 item.
 */
public class Portal extends Item{
    /**
     * Tạo ra 1 Portal.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     */
    public Portal(int x, int y) {
        super(x, y);
        img = Sprite.portal.getFxImage();
    }

    @Override
    public void update() {}
}
