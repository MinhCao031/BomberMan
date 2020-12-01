package Bomberman.entities;

import Bomberman.BombermanGame;
import Bomberman.Gameplay;
import Bomberman.X;
import Bomberman.entities.Enemy.Enemy;
import Bomberman.entities.Item.*;
import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.List;

/**
 * Bomberman, nhân vật người chơi điều khiển
 */
public class Bomber extends Entity {
    /**
     * các biến tạo hoạt ảnh cho bomber.
     */
    private int left, right, up, down;
    /**
     * Sai số để bomber đi bị vấp.
     */
    private final double delta = 0.3;
    /**
     * Time to completely disappear.
     */
    private final int fadingTime = 1600;
    /**
     * Số bomb tối đa người chơi có thể đặt tại 1 thời điểm.
     */
    private int maxBomb, flamePower;
    /**
     * Bomber set a bomb at bombset, Bomber died at timeDie.
     */
    private int bombSet, timeDie;
    /**
     * Các biến kiểm soát khả năng kích nổ bomb lập tức của bomber.
     */
    private int detonateTime, detonateDelay;
    /**
     * Kiểm soát tốc độ.
     */
    private int updateTime, updateDelay;
    /**
     * Tốc độ của người chơi, đơn vị là ô vuông trên 1 lần bấm mũi tên.
     */
    private double speed;
    /**
     * Some boolean of alive or ability.
     */
    private boolean alive, isImmuneToFlame, brickPass, bombPass, detonatePower;
    /**
     * true if this is player1.
     */
    protected boolean isP1;
    /**
     * Số bomb hiện có trên màn hình.
     */
    protected int bombCounted = 0;

    /**
     * Tạo ra 1 Bomber.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của Bomber.
     */
    public Bomber(int x, int y, Image img, boolean isFirstPlayer) {
        super( x, y, img);
        left = 0; right = 0;
        up = 0; down = 0;
        maxBomb = 2;
        flamePower = 1;
        speed = 0.1;
        alive = true;
        isP1 = isFirstPlayer;
        // ability
        isImmuneToFlame = false;
        brickPass = false;
        bombPass = false;
        detonatePower = false;
        // others
        detonateTime = 0;
        detonateDelay = 500;
        updateTime = X.currentTime;
        updateDelay = 1;
    }

    /**
     * Kiểm tra bomber có va chạm chướng ngại ở hướng đi inDirection không.
     * Có thể chỉnh sửa để áp dụng cho enemy.
     * @param inDirection hướng đi.
     * @return false nếu không có chướng ngại, ngược lại true đồng thời cập nhật luôn tọa độ (x,y).
     */
    private boolean encounterObstacle(String inDirection) {
        switch (inDirection) {
            case "left":{
                int nextTileX = (int)Math.floor(x - 1);
                int nextTileYUp = (int)Math.floor(y);
                int nextTileYDown = nextTileYUp + 1;

                char nextTileUp = BombermanGame.getGameplay().keymap.charAt(nextTileX + nextTileYUp * BombermanGame.getGameplay().WIDTH);
                char nextTileDown = BombermanGame.getGameplay().keymap.charAt(nextTileX + nextTileYDown * BombermanGame.getGameplay().WIDTH);

                if(y - nextTileYUp < delta && X.howMighty(nextTileUp) < 1 && X.howMighty(nextTileDown) > 0) {
                    y = nextTileYUp;
                    if(y - nextTileYUp == 0) x -= speed;
                    //x -= speed;
                    //System.out.println(x + " " + y);
                    return true;
                } else if (nextTileYDown - y < delta && X.howMighty(nextTileDown) < 1 && X.howMighty(nextTileUp) > 0) {
                    y = nextTileYDown;
                    if(y - nextTileYDown == 0) x -= speed;
                    //x -= speed;
                    //System.out.println(x + " " + y);
                    return true;
                }
                if(X.howMighty(nextTileDown) > 0 || X.howMighty(nextTileUp) > 0) {
                    x = nextTileX + 1;
                    return true;
                }
                return false;
            }
            case "right":{
                int nextTileX = (int)Math.ceil(x + 1);
                int nextTileYUp = (int)Math.floor(y);
                int nextTileYDown = nextTileYUp + 1;

                char nextTileUp = BombermanGame.getGameplay().keymap.charAt(nextTileX + nextTileYUp * BombermanGame.getGameplay().WIDTH);
                char nextTileDown = BombermanGame.getGameplay().keymap.charAt(nextTileX + nextTileYDown * BombermanGame.getGameplay().WIDTH);

                if(y - nextTileYUp < delta && X.howMighty(nextTileUp) < 1 && X.howMighty(nextTileDown) > 0) {
                    y = nextTileYUp;
                    if(y - nextTileYUp == 0) x += speed;
                    //x += speed;
                    //System.out.println(x + " " + y);
                    return true;
                } else if (nextTileYDown - y < delta && X.howMighty(nextTileDown) < 1 && X.howMighty(nextTileUp) > 0) {
                    y = nextTileYDown;
                    if(y - nextTileYDown == 0) x += speed;
                    //x += speed;
                    //System.out.println(x + " " + y);
                    return true;
                }
                if(X.howMighty(nextTileDown) > 0 || X.howMighty(nextTileUp) > 0) {
                    x = nextTileX - 1;
                    return true;
                }
                return false;
            }
            case "up":{
                int nextTileY = (int)Math.floor(y - 1);
                int nextTileXUp = (int)Math.floor(x);
                int nextTileXDown = nextTileXUp + 1;

                char nextTileUp = BombermanGame.getGameplay().keymap.charAt(nextTileXUp + nextTileY * BombermanGame.getGameplay().WIDTH);
                char nextTileDown = BombermanGame.getGameplay().keymap.charAt(nextTileXDown + nextTileY * BombermanGame.getGameplay().WIDTH);

                if(x - nextTileXUp < delta && X.howMighty(nextTileUp) < 1 && X.howMighty(nextTileDown) > 0) {
                    x = nextTileXUp;
                    if(x - nextTileXUp == 0) y -= speed;
                    //y -= speed;
                    //System.out.println(x + " " + y);
                    return true;
                } else if (nextTileXDown - x < delta && X.howMighty(nextTileDown) < 1 && X.howMighty(nextTileUp) > 0) {
                    x = nextTileXDown;
                    if(x - nextTileXDown == 0) y -= speed;
                    //y -= speed;
                    //System.out.println(x + " " + y);
                    return true;
                }
                if(X.howMighty(nextTileDown) > 0 || X.howMighty(nextTileUp) > 0) {
                    y = nextTileY + 1;
                    return true;
                }
                return false;
            }
            case "down":{
                int nextTileY = (int)Math.ceil(y + 1);
                int nextTileXUp = (int)Math.floor(x);
                int nextTileXDown = nextTileXUp + 1;

                char nextTileUp = BombermanGame.getGameplay().keymap.charAt(nextTileXUp + nextTileY * BombermanGame.getGameplay().WIDTH);
                char nextTileDown = BombermanGame.getGameplay().keymap.charAt(nextTileXDown + nextTileY * BombermanGame.getGameplay().WIDTH);

                if(x - nextTileXUp < delta && X.howMighty(nextTileUp) < 1 && X.howMighty(nextTileDown) > 0) {
                    x = nextTileXUp;
                    if(x - nextTileXUp == 0) y += speed;
                    //y += speed;
                    //System.out.println(x + " " + y);
                    return true;
                } else if (nextTileXDown - x < delta && X.howMighty(nextTileDown) < 1 && X.howMighty(nextTileUp) > 0) {
                    x = nextTileXDown;
                    if(x - nextTileXDown == 0) y += speed;
                    //y += speed;
                    //System.out.println(x + " " + y);
                    return true;
                }
                if(X.howMighty(nextTileDown) > 0 || X.howMighty(nextTileUp) > 0) {
                    y = nextTileY - 1;
                    return true;
                }
                return false;
            }
            default: return true;
        }
    }

    /**
     * Di chuyển bomber.
     */
    public void moveLeft() {
        if (!alive) return;
        if(left / 3 == 0) {
            this.img = Sprite.player_left_1.getFxImage();
        } else {
            this.img = Sprite.player_left_2.getFxImage();
        }
        left = ++left%6;
        if (x - speed >= Math.floor(x) || !encounterObstacle("left")) {
            x-=speed;
        }
        //System.out.println(x + " " + y);
    }
    public void moveRight() {
        if (!alive) return;
        if(right / 3 == 0) {
            this.img = Sprite.player_right_1.getFxImage();
        } else {
            this.img = Sprite.player_right_2.getFxImage();
        }
        right = ++right%6;
        if (x + speed < Math.ceil(x) || !encounterObstacle("right")) {
            x += speed;
        }
        //System.out.println(x + " " + y);
    }
    public void moveUp() {
        if (!alive) return;
        if(up / 3 == 0) {
            this.img = Sprite.player_up_1.getFxImage();
        } else {
            this.img = Sprite.player_up_2.getFxImage();
        }
        up = ++up%6;
        if (y - speed >= Math.floor(y) || !encounterObstacle("up")) {
            y -= speed;
        }
        //System.out.println(x + " " + y);
    }
    public void moveDown() {
        if (!alive) return;
        if(down / 3 == 0) {
            this.img = Sprite.player_down_1.getFxImage();
        } else {
            this.img = Sprite.player_down_2.getFxImage();
        }
        down = ++down%6;
        if (y + speed < Math.ceil(y) || !encounterObstacle("down")) {
            y+=speed;
        }
        //System.out.println(x + " " + y);
    }

    /**
     * đặt bomb, lưu bomb vào list.
     */
    public void createBomb(){
        if(++bombCounted <= maxBomb && X.currentTime - bombSet > 160) {
            bombSet = X.currentTime;
            // Chọn tọa độ hợp lí cho quả bomb
            int bombX = (int)(x + 0.5);
            int bombY = (int)(y + 0.5);
            // Đánh dấu vị trí của bomb trong bản đồ dạng string
            int charPos = bombX + bombY * BombermanGame.getGameplay().WIDTH;
            // Nếu tại ô đó chưa có bomb thì add bomb vào stillObject để nó hiển thị được lên màn hình
            try {
                if(BombermanGame.getGameplay().keymap.charAt(charPos) != 'B') {
                    Bomb bomb = new Bomb(bombX, bombY, Sprite.bomb, isP1, flamePower);
                    BombermanGame.getGameplay().getAnimations().add(bomb);
                    BombermanGame.getGameplay().keymap = X.replace(BombermanGame.getGameplay().keymap, charPos, 'B');
                } else bombCounted--;
            } catch (Exception e) {
                bombCounted--;
            }
        } else bombCounted--;
    }

    public void dying() {
        timeDie = X.currentTime;
        alive = false;
        isImmuneToFlame = false;
        img = Sprite.movingSprite1PeriodOnly(fadingTime / 4, timeDie, Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3).getFxImage();
    }

    @Override
    public void update() {
        if(!alive) {
            // die
            img = Sprite.movingSprite1PeriodOnly(fadingTime / 4, timeDie, Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3).getFxImage();
            if(X.currentTime - timeDie >= fadingTime) {
                img = null;
                BombermanGame.getGameplay().getAnimations().remove(this);
                BombermanGame.getGameplay().setStatus(-1);
            }
        } else {
            List<Entity> entities = BombermanGame.getGameplay().getAnimations();

            // Keyboard Handle
            if (isP1) {
                if (X.currentTime - updateTime >= updateDelay) {
                    updateTime = X.currentTime;
                    if (Gameplay.keyHandle.isP1up()) moveUp();
                    if (Gameplay.keyHandle.isP1down()) moveDown();
                    if (Gameplay.keyHandle.isP1left()) moveLeft();
                    if (Gameplay.keyHandle.isP1right()) moveRight();
                }
                if(Gameplay.keyHandle.isP1setBomb()) createBomb();
                if(detonatePower && Gameplay.keyHandle.isP1detonateBomb() && (X.currentTime - detonateTime > detonateDelay)) {
                    detonateTime = X.currentTime;
                    for (Entity e : entities) {
                        if (e instanceof Bomb && ((Bomb) e).isFromP1()) {
                            ((Bomb) e).explode();
                            break;
                        }
                    }
                }
            } else {
                if (X.currentTime - updateTime >= updateDelay) {
                    updateTime = X.currentTime;
                    if (Gameplay.keyHandle.isP2up()) moveUp();
                    if (Gameplay.keyHandle.isP2down()) moveDown();
                    if (Gameplay.keyHandle.isP2left()) moveLeft();
                    if (Gameplay.keyHandle.isP2right()) moveRight();
                }
                if(Gameplay.keyHandle.isP2setBomb()) createBomb();
                if(detonatePower && Gameplay.keyHandle.isP2detonateBomb() && (X.currentTime - detonateTime > detonateDelay)) {
                    detonateTime = X.currentTime;
                    for (Entity e : entities) {
                        if (e instanceof Bomb && !((Bomb) e).isFromP1()) {
                            ((Bomb) e).explode();
                            break;
                        }
                    }
                }
            }

            // Checking enemies nearby.
            int n = entities.size();
            for (int i = n - 1; i >= 0; --i) {
                Entity e = entities.get(i);
                if (X.twoSqrCollide(x, y, e.x, e.y)) {
                    if ( (e instanceof Enemy && ((Enemy) e).isAlive()) || (e instanceof BombFlame && !isImmuneToFlame)) {
                        dying();
                        break;
                    }
                }
            }

            // Looking for items
            entities = BombermanGame.getGameplay().getMayVanish();
            n = entities.size();
            for (int i = n - 1; i >= 0; --i) {
                Entity e = entities.get(i);
                if (X.twoSqrCollide(x, y, e.x, e.y)) {
                    if (e instanceof Portal && Enemy.getNumOfEnemy() == 0) {
                        System.out.println("You win.");
                        BombermanGame.getGameplay().setStatus(1);
                        Enemy.reduceEnemy();
                        break;
                    } else if (e instanceof BombsItem) {
                        ++maxBomb;
                        ((BombsItem) e).disappear();
                    } else if (e instanceof FlameItem) {
                        ++flamePower;
                        ((FlameItem) e).disappear();
                    } else if (e instanceof SpeedItem) {
                        speed += 0.02;
                        ((SpeedItem) e).disappear();
                    } else if (e instanceof BrickPassItem) {
                        brickPass = true;
                        ((BrickPassItem) e).disappear();
                    } else if (e instanceof DetonatorItem) {
                        detonatePower = true;
                        ((DetonatorItem) e).disappear();
                    } else if (e instanceof BombPassItem) {
                        bombPass = true;
                        ((BombPassItem) e).disappear();
                    } else if (e instanceof FlamePassItem) {
                        isImmuneToFlame = true;
                        ((FlamePassItem) e).disappear();
                    }
                }
            }
        }
    }
}
