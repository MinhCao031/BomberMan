package Bomberman.entities;

import Bomberman.BombermanGame;
import Bomberman.X;
import Bomberman.entities.Item.Item;
import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;

public class Brick extends Entity {
    /**
     * Gạch sẽ nhả ra item này nếu bị bomb phá hủy.
     */
    final Item item;
    /**
     * Time to completely disappear.
     */
    private final int fadingTime = 480;
    /**
     * Time exploded.
     */
    private int timeExploded;
    /**
     * Is it exploded?
     */
    private boolean alreadyExploded;

    /**
     * Tạo ra 1 Brick ko có item bên trong.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của Brick.
     */
    public Brick(int x, int y, Image img) {
        super(x, y, img);
        item = null;
        alreadyExploded = false;
    }

    /**
     * Tạo ra 1 Brick có item bên trong.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của Brick.
     */
    public Brick(int x, int y, Image img, Item item_) {
        super(x, y, img);
        item = item_;
    }

    public void breaking() {
        timeExploded = X.currentTime;
        img = Sprite.movingSprite1PeriodOnly(fadingTime/3, timeExploded,
                Sprite.brick_exploded[0], Sprite.brick_exploded[1], Sprite.brick_exploded[2]).getFxImage();
        alreadyExploded = true;
        BombermanGame.getGameplay().getMayVanish().remove(this);
        BombermanGame.getGameplay().getAnimations().add(this);
        if (item != null) BombermanGame.getGameplay().getMayVanish().add(item);
        BombermanGame.getGameplay().renderObjectMayVanish();
    }


    @Override
    public void update() {
        if(alreadyExploded) {
            img = Sprite.movingSprite1PeriodOnly(fadingTime/3, timeExploded,
                    Sprite.brick_exploded[0], Sprite.brick_exploded[1], Sprite.brick_exploded[2]).getFxImage();
            if(X.currentTime - timeExploded >= fadingTime) {
                BombermanGame.getGameplay().getAnimations().remove(this);
                BombermanGame.getGameplay().keymap = X.replace(BombermanGame.getGameplay().keymap, (int) (x + y * BombermanGame.getGameplay().WIDTH), ' ');
            }
        }
    }
}
