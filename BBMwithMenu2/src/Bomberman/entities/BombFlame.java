package Bomberman.entities;

import Bomberman.BombermanGame;
import Bomberman.X;
import Bomberman.graphics.Sprite;

/**
 * BombFlame được tạo ra khi bomb nổ.
 */
public class BombFlame extends Entity {
    /**
     * Thời điểm bomb nổ -> bắn ra tia lửa.
     */
    private final int timeCreated;
    /**
     * Tia lửa còn hay mất?
     */
    private boolean disappeared;
    /**
     * Thời gian tồn tại.
     */
    private final int lifeTime = 500;
    /**
     * Các sprite tĩnh trong sprite động.
     */
    private final Sprite[] sprites;

    /**
     * Tạo ra 1 ô BombFlame.
     * @param x   hoành độ (Trục Ox hướng sang phải).
     * @param y   tung độ (Trục Oy hướng xuống dưới).
     * @param sprites Các sprite tĩnh trong sprite động.
     */
    public BombFlame(int x, int y, Sprite[] sprites) {
        super(x, y);
        timeCreated = X.currentTime;
        this.sprites = sprites;
        this.img = Sprite.movingSprite1PeriodOnly(lifeTime/5, timeCreated,
            sprites[0], sprites[1], sprites[2], sprites[1], sprites[0]).getFxImage();
        disappeared = false;

    }

    @Override
    public void update() {
        // Tồn tại trong 1 thời gian rất ngắn (khoảng 1 giây), sau đó biến mất
        this.img = Sprite.movingSprite1PeriodOnly(lifeTime/5, timeCreated,
                sprites[0], sprites[1], sprites[2], sprites[1], sprites[0]).getFxImage();
        if(!disappeared && X.currentTime - timeCreated > lifeTime) {
            BombermanGame.getGameplay().getAnimations().remove(this);
            disappeared = true; img = null;
            BombermanGame.getGameplay().keymap = X.replace(BombermanGame.getGameplay().keymap, (int)(x + y * BombermanGame.getGameplay().WIDTH), ' ');
        }
    }
}

