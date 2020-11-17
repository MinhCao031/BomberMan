package Bomberman.entities;

import javafx.scene.image.Image;
import Bomberman.BombermanGame;
import Bomberman.graphics.Sprite;

import java.util.List;

public class Bomber extends Entity {
    /**
     * Số bomb hiện có trên màn hình.
     */
    public static int bombCounted = 0;
    /**
     * các biến tạo hoạt ảnh cho bomber.
     */
    private int left, right, up, down;
    /**
     * Số bomb tối đa người chơi có thể đặt tại 1 thời điểm.
     */
    private int maxBomb;
    /**
     * Tốc độ của người chơi, đơn vị là ô vuông trên 1 lần bấm mũi tên.
     */
    private double speed;
    /**
     * Sai số để bomber đi bị vấp.
     */
    private final double delta;

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

                char nextTileUp = BombermanGame.keymap.charAt(nextTileX + nextTileYUp * BombermanGame.WIDTH);
                char nextTileDown = BombermanGame.keymap.charAt(nextTileX + nextTileYDown * BombermanGame.WIDTH);

                if(y - nextTileYUp < delta && nextTileUp != '#' && nextTileUp != '*'
                && (nextTileDown == '#' || nextTileDown == '*')) {
                    y = nextTileYUp;
                    x -= speed;
                    //System.out.println(x + " " + y);
                    return true;
                } else if (nextTileYDown - y < delta && nextTileDown != '#' && nextTileDown != '*'
                && (nextTileUp == '#' || nextTileUp == '*')) {
                    y = nextTileYDown;
                    x -= speed;
                    //System.out.println(x + " " + y);
                    return true;
                }
                if(nextTileUp == '#' || nextTileUp == '*' || nextTileDown == '#' || nextTileDown == '*') {
                    x = nextTileX + 1;
                    return true;
                }
                return false;
            }
            case "right":{
                int nextTileX = (int)Math.ceil(x + 1);
                int nextTileYUp = (int)Math.floor(y);
                int nextTileYDown = nextTileYUp + 1;

                char nextTileUp = BombermanGame.keymap.charAt(nextTileX + nextTileYUp * BombermanGame.WIDTH);
                char nextTileDown = BombermanGame.keymap.charAt(nextTileX + nextTileYDown * BombermanGame.WIDTH);

                if(y - nextTileYUp < delta && nextTileUp != '#' && nextTileUp != '*'
                && (nextTileDown == '#' || nextTileDown == '*')) {
                    y = nextTileYUp;
                    x += speed;
                    //System.out.println(x + " " + y);
                    return true;
                } else if (nextTileYDown - y < delta && nextTileDown != '#' && nextTileDown != '*'
                && (nextTileUp == '#' || nextTileUp == '*')) {
                    y = nextTileYDown;
                    x += speed;
                    //System.out.println(x + " " + y);
                    return true;
                }
                if(nextTileUp == '#' || nextTileUp == '*' || nextTileDown == '#' || nextTileDown == '*') {
                    x = nextTileX - 1;
                    return true;
                }
                return false;
            }
            case "up":{
                int nextTileY = (int)Math.floor(y - 1);
                int nextTileXUp = (int)Math.floor(x);
                int nextTileXDown = nextTileXUp + 1;

                char nextTileUp = BombermanGame.keymap.charAt(nextTileXUp + nextTileY * BombermanGame.WIDTH);
                char nextTileDown = BombermanGame.keymap.charAt(nextTileXDown + nextTileY * BombermanGame.WIDTH);

                if(x - nextTileXUp < delta && nextTileUp != '#' && nextTileUp != '*'
                && (nextTileDown == '#' || nextTileDown == '*')) {
                    x = nextTileXUp;
                    y -= speed;
                    //System.out.println(x + " " + y);
                    return true;
                } else if (nextTileXDown - x < delta && nextTileDown != '#' && nextTileDown != '*'
                && (nextTileUp == '#' || nextTileUp == '*')) {
                    x = nextTileXDown;
                    y -= speed;
                    //System.out.println(x + " " + y);
                    return true;
                }
                if(nextTileUp == '#' || nextTileUp == '*' || nextTileDown == '#' || nextTileDown == '*') {
                    y = nextTileY + 1;
                    return true;
                }
                return false;
            }
            case "down":{
                int nextTileY = (int)Math.ceil(y + 1);
                int nextTileXUp = (int)Math.floor(x);
                int nextTileXDown = nextTileXUp + 1;

                char nextTileUp = BombermanGame.keymap.charAt(nextTileXUp + nextTileY * BombermanGame.WIDTH);
                char nextTileDown = BombermanGame.keymap.charAt(nextTileXDown + nextTileY * BombermanGame.WIDTH);

                if(x - nextTileXUp < delta && nextTileUp != '#' && nextTileUp != '*'
                && (nextTileDown == '#' || nextTileDown == '*')) {
                    x = nextTileXUp;
                    y += speed;
                    //System.out.println(x + " " + y);
                    return true;
                } else if (nextTileXDown - x < delta && nextTileDown != '#' && nextTileDown != '*'
                && (nextTileUp == '#' || nextTileUp == '*')) {
                    x = nextTileXDown;
                    y += speed;
                    //System.out.println(x + " " + y);
                    return true;
                }
                if(nextTileUp == '#' || nextTileUp == '*' || nextTileDown == '#' || nextTileDown == '*') {
                    y = nextTileY - 1;
                    return true;
                }
                return false;
            }
            default: return true;
        }
    }

    /**
     * Tạo ra 1 Bomber.
     * @param x hoành độ (Trục Ox hướng sang phải).
     * @param y tung độ (Trục Oy hướng xuống dưới).
     * @param img ảnh của Bomber.
     */
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        left = 0; right = 0;
        up = 0; down = 0;
        maxBomb = 10;
        speed = 0.1; delta = 0.25;
    }

    /**
     * Di chuyển bomber.
     */
    public void moveLeft() {
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

    /**
     * Di chuyển bomber.
     */
    public void moveRight() {
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

    /**
     * Di chuyển bomber.
     */
    public void moveUp() {
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

    /**
     * Di chuyển bomber.
     */
    public void moveDown() {
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
     * đặt bomb.
     * @param stillObjects
     */
    public void createBomb(List<Entity> stillObjects){
        if(++bombCounted <= maxBomb) {
            // Chọn tọa độ hợp lí cho quả bomb
            int bombX = (int)(x + 0.5);
            int bombY = (int)(y + 0.5);
            // Đánh dấu vị trí của bomb trong bản đồ dạng string
            int charPos = bombX + bombY * BombermanGame.WIDTH;
            // Tạo biến Bomb
            Bomb bomb = new Bomb(bombX, bombY, Sprite.bomb.getFxImage());
            // Nếu tại ô đó chưa có bomb thì add bomb vào stillObject để nó hiển thị được lên màn hình
            if(BombermanGame.keymap.charAt(charPos) != 'b') {
                stillObjects.add(bomb);
                String left = BombermanGame.keymap.substring(0,charPos);
                String right = BombermanGame.keymap.substring(charPos + 1);
                BombermanGame.keymap = left.concat("b").concat(right);
            }
        }
    }

    @Override
    public void update() {
        //Coming soon.
    }
}
