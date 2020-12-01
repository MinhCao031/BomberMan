package Bomberman.entities;

import Bomberman.graphics.Sprite;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Thực thể nói chung, bao gồm tất cả các đối tượng trong map của game.
 */
public abstract class Entity {
    /**
     * x: hoành độ (Trục Ox hướng sang phải).
     * y: tung độ (Trục Oy hướng xuống dưới).
     */
    protected double x,y;
    /**
     * ảnh của Entity.
     */
    protected Image img;

    /**
     * Tạo ra 1 Entity.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của Entity.
     */
    public Entity(double x, double y, Image img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }

    /**
     * Tạo ra 1 Entity.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     */
    public Entity(double x, double y) {
        this.x = x;
        this.y = y;
        img = Sprite.nothing.getFxImage();
    }

    /**
     * Truy cập các tọa độ.
     * @return hoành độ || tung độ.
     */
    public double getX() { return x; }
    public double getY() { return y; }
    public void setImg(Image img) { this.img = img; }

    /**
     * load ảnh trong GraphicsContext gc
     * @param gc
     */
    public void render(GraphicsContext gc) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        ImageView iv = new ImageView(img);
        Image base = iv.snapshot(params, null);

        //ảnh được vẽ trong GraphicsContext gc ở tọa độ (x,y)
        gc.drawImage(base, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
    }

    /**
     * Được code trong các hàm nào overide lại những hàm này.
     */
    public void moveLeft() {}
    public void moveRight() {}
    public void moveUp() {}
    public void moveDown() {}

    /**
     * Cập nhật trạng thái của Entity sau mỗi frame.
     */
    public abstract void update();
}
