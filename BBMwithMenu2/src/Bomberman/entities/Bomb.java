package Bomberman.entities;

import Bomberman.BombermanGame;
import Bomberman.X;
import Bomberman.graphics.Sprite;

/**
 * Quả bomb được tạo ra bởi bomber khi ấn dấu cách.
 * Sau 1 thời gian cố định bomb sẽ tự nổ.
 */
public class Bomb extends Entity {
    /**
     * Phạm vi nổ của bomb, Để static cho tiện.
     */
    protected int flameLong = 1;
    /**
     * Thời điểm đặt bomb (timeSetBomb) và thời gian để bomb nổ (lifeTime).
     */
    private final int timeSetBomb, lifeTime;
    /**
     * true nếu bomb đã nổ.
     */
    private boolean alreadyExploded, isFromP1;
    /**
     * Các sprite tĩnh của bomb (trước khi nổ).
     */
    private final Sprite[] sprites;

    /**
     * Tạo ra 1 Bomb.
     * @param x   hoành độ (Trục Ox hướng sang phải).
     * @param y   tung độ (Trục Oy hướng xuống dưới).
     * @param sprites các sprite tĩnh.
     */
    public Bomb(int x, int y, Sprite[] sprites, boolean isFromP1, int flameLong) {
        super(x, y);
        timeSetBomb = X.currentTime;
        lifeTime = 3000;
        this.flameLong = flameLong;
        img = Sprite.movingSprite(225, sprites[0], sprites[1], sprites[2], sprites[1]).getFxImage();
        this.isFromP1 = isFromP1;
        alreadyExploded = false;
        this.sprites = sprites;
    }

    public boolean isFromP1() {
        return isFromP1;
    }

    /**
     * Bomb nổ và tạo ra tia lửa.
     */
    public void explode() {
        BombermanGame.getGameplay().getAnimations().remove(this);
        img = null;
        for(int i = 0; i < BombermanGame.getGameplay().getAnimations().size(); ++i) {
            Entity e = BombermanGame.getGameplay().getAnimations().get(i);
            if (e instanceof Bomber && ((Bomber)e).isP1 == this.isFromP1) {
                ((Bomber)e).bombCounted--;
            }
        }
        alreadyExploded = true;
        createFlame();
    }

    /**
     * Bomb tạo tia lửa.
     */
    private void createFlame() {
        // center
        BombFlame flameSource = new BombFlame((int) x, (int) y, Sprite.bomb_exploded);
        BombermanGame.getGameplay().getAnimations().add(flameSource);
        // up
        for (int i = 1; i <= flameLong; ++i) {
            int pos = (int) (x + (y - i) * BombermanGame.getGameplay().WIDTH);
            if (X.howMighty(BombermanGame.getGameplay().keymap.charAt(pos)) < 1) {
                BombFlame flame = i < flameLong ?
                        new BombFlame((int) x, (int) y - i, Sprite.explosion_vertical) :
                        new BombFlame((int) x, (int) y - i, Sprite.explosion_vertical_top_last);
                BombermanGame.getGameplay().getAnimations().add(flame);
            } else if (X.howMighty(BombermanGame.getGameplay().keymap.charAt(pos)) > 0) {
                forceDestroy((int)x, (int)y - i);
//                System.out.println("Force Destroy Object at (" + x + ", " + (y-i) + ") -> " + BombermanGame.getGameplay().keymap.charAt(pos));
                break;
            } else break;
        }
        // down
        for (int i = 1; i <= flameLong; ++i) {
            int pos = (int) (x + (y + i) * BombermanGame.getGameplay().WIDTH);
            if (X.howMighty(BombermanGame.getGameplay().keymap.charAt(pos)) < 1) {
                BombFlame flame = i < flameLong ?
                        new BombFlame((int) x, (int) y + i, Sprite.explosion_vertical) :
                        new BombFlame((int) x, (int) y + i, Sprite.explosion_vertical_down_last);
                BombermanGame.getGameplay().getAnimations().add(flame);
            } else if (X.howMighty(BombermanGame.getGameplay().keymap.charAt(pos)) > 0) {
                forceDestroy((int)x, (int)y + i);
//                System.out.println("Force Destroy Object at (" + x + ", " + (y+i) + ") -> " + BombermanGame.getGameplay().keymap.charAt(pos));
                break;
            } else break;
        }
        // left
        for (int i = 1; i <= flameLong; ++i) {
            int pos = (int) (x - i + y * BombermanGame.getGameplay().WIDTH);
            if (X.howMighty(BombermanGame.getGameplay().keymap.charAt(pos)) < 1) {
                BombFlame flame = i < flameLong ?
                        new BombFlame((int) x - i, (int) y, Sprite.explosion_horizontal) :
                        new BombFlame((int) x - i, (int) y, Sprite.explosion_horizontal_left_last);
                BombermanGame.getGameplay().getAnimations().add(flame);
            } else if (X.howMighty(BombermanGame.getGameplay().keymap.charAt(pos)) > 0) {
                forceDestroy((int)x - i, (int)y);
//                System.out.println("Force Destroy Object at (" + (x-i) + ", " + y + ") -> " + BombermanGame.getGameplay().keymap.charAt(pos));
                break;
            } else break;
        }
        // right
        for (int i = 1; i <= flameLong; ++i) {
            int pos = (int) (x + i + y * BombermanGame.getGameplay().WIDTH);
            if (X.howMighty(BombermanGame.getGameplay().keymap.charAt(pos)) < 1) {
                BombFlame flame = i < flameLong ?
                        new BombFlame((int) x + i, (int) y, Sprite.explosion_horizontal) :
                        new BombFlame((int) x + i, (int) y, Sprite.explosion_horizontal_right_last);
                BombermanGame.getGameplay().getAnimations().add(flame);
            } else if (X.howMighty(BombermanGame.getGameplay().keymap.charAt(pos)) > 0) {
                forceDestroy((int)x + i, (int)y);
//                System.out.println("Force Destroy Object at (" + (x+i) + ", " + y + ") -> " + BombermanGame.getGameplay().keymap.charAt(pos));
                break;
            } else break;
        }
    }

    /**
     * Flame nếu gặp vật cản sẽ tiêu diệt vật cản tại đó.
     * @param x hoành độ
     * @param y tung độ
     */
    public void forceDestroy(int x, int y) {
        int pos = x + y * BombermanGame.getGameplay().WIDTH;
        if (X.howMighty(BombermanGame.getGameplay().keymap.charAt(pos)) > 0) {
            for (Entity entity: BombermanGame.getGameplay().getAnimations()) {
                if ((int)entity.x == x && (int)entity.y == y && entity instanceof Bomb) {
                    //System.out.println(entity.getClass() + " at " + (int)entity.x + " " + (int)entity.y);
                    ((Bomb)entity).explode();
                    return;
                }
            }
            for (Entity entity: BombermanGame.getGameplay().getMayVanish()) {
                if ((int)entity.x == x && (int)entity.y == y && entity instanceof Brick) {
                    //System.out.println(entity.getClass() + " at " + (int)entity.x + " " + (int)entity.y);
                    ((Brick)entity).breaking();
                    return;
                }
            }
        }

    }

    /**
     * Đếm giờ chờ bomb nổ.
     */
    @Override
    public void update() {
        // Tạo hoạt ảnh cho bomb.
        img = Sprite.movingSprite(225, sprites[0], sprites[1], sprites[2], sprites[1]).getFxImage();
        if (!alreadyExploded && X.currentTime - timeSetBomb >= lifeTime) {
            explode(); // System.out.println("Bomb exploded.");
        }
    }
}
