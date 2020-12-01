package Bomberman;

import jdk.nashorn.internal.runtime.arrays.ArrayIndex;

import java.util.ArrayList;

/**
 * Hàm tự chế để đây, để static
 */
public class X {
    public static int currentTime;
    public static int globalTime;

    /**
     * kiểm tra 2 Entity chạm nhau hay không bằng 2 hitbox hình vuông.
     * @param x1 hoành độ hình vuông 1
     * @param y1 tung độ hình vuông 1
     * @param x2 hoành độ hình vuông 2
     * @param y2 tung độ hình vuông 2
     * @return true nếu 2 hình vuông cắt nhau
     */
    public static boolean twoSqrCollide(double x1, double y1, double x2, double y2) {
        double dx = (x1 - x2) * (x1 - x2);
        double dy = (y1 - y2) * (y1 - y2);
        return dx < 0.7 && dy < 0.7;
    }

    /**
     * Kiểm tra Entity bằng char đại diện cho nó.
     * @param entity represent an entity.
     * @return 3 if Wall, 2 if Bomb, 1 if Brick and 0 for others
     */
    public static int howMighty(char entity) {
        if (entity == '#') return 3;
        if (entity == 'B') return 2;
        if (entity == '*' || entity == 'x' || entity == 'b' || entity == 'f' || entity == 's'
         || entity == 'w' || entity == 'd' || entity == 'o' || entity == 'i' || entity == '?') return 1;
        else return 0;
    }

    /**
     * check tọa độ hiện tại có thể chuyển hướng đc ko
     * @param x hoành độ.
     * @param y tung độ.
     * @param delta sai số va vấp.
     * @return true nếu đứng đúng ngã tư
     */
    public static boolean isBothCoordinateOddInt(double x, double y, double delta) {
        int X = (int)(x + 0.5);
        int Y = (int)(y + 0.5);
        return (-delta < x-X && x-X < delta
                && -delta < y-Y && y-Y < delta
                &&  X*Y % 2 == 1);
    }
    /**
     * @param oldStr string cần thay kí tự.
     * @param indexPos vị trí cần thay.
     * @param newChar kí tự mới.
     * @return String mới.
     */
    public static String replace(String oldStr, int indexPos, char newChar) {
        char[] strToChars = oldStr.toCharArray();
        strToChars[indexPos] = newChar;
        return String.valueOf(strToChars);
    }

    public static ArrayList<int[]> findWay(double xEnemy, double yEnemy, double xBomber, double yBomber) {
        int x1 = (int)(xEnemy + 0.5);
        int y1 = (int)(yEnemy + 0.5);
        int x2 = (int)(xBomber + 0.5);
        int y2 = (int)(yBomber + 0.5);

        ArrayList<int[]> ans = new ArrayList<>();
        return ans;
    }
}
