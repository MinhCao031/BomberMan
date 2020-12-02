package Bomberman;

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
     * kiểm tra Bomber có "nằm vùng" enemy không, nếu có sẽ bị đuổi.
     * @param x1 hoành độ hình vuông 1
     * @param y1 tung độ hình vuông 1
     * @param x2 hoành độ hình vuông 2
     * @param y2 tung độ hình vuông 2
     * @return true nếu 2 hình vuông cắt nhau
     */
    public static boolean inRange(double x1, double y1, double x2, double y2) {
        double dx = (x1 - x2) * (x1 - x2);
        double dy = (y1 - y2) * (y1 - y2);
        return dx < 81 && dy < 81;
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
     * Xac dinhj huong di
     * @param x hoanh do dau
     * @param y tung do dau
     * @param X hoanh do dich
     * @param Y tung do dich
     * @return huong di.
     */
    public static int findDirection(double x, double y, int X, int Y, double delta) {
        double dx = Math.abs(x - X);
        double dy = Math.abs(y - Y);

//        double epsilon = 0.001;
//        System.out.println(x + "-" + y + "    ->    " + X + "-" + Y);
        if (dx < delta && dy < delta) return -1;

        if (dx < dy) {
            if (y < Y) return 1;
            if (y > Y) return 3;
        }
        if (dy < dx) {
            if (x < X) return 0;
            if (x > X) return 2;
        }
        return 10;
    }


    public static boolean isAlmostThere(double x, double y, int X, int Y, double delta) {
        double dx = (x - X) * (x - X);
        double dy = (y - Y) * (y - Y);
        double d = delta * delta;
        return dx < d && dy < d;
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

    public static ArrayList<int[]> findWay(String s, double xEnemy, double yEnemy, double xBomber, double yBomber) {
        int x1 = (int)(xEnemy + 0.5);
        int y1 = (int)(yEnemy + 0.5);
        int x2 = (int)(xBomber + 0.5);
        int y2 = (int)(yBomber + 0.5);
        int i, j, wayLongX = 999, wayLongY;
        boolean found_U;// = false;
        boolean found_C;// = false;

        char[][] chars = new char[31][];
        for (int k = 0; k < 13; ++k) {
            chars[k] = s.substring(31*k, 31*k + 31).toCharArray();
        }

        ArrayList<int[]> ans = new ArrayList<>(3);
        int w = 31;
        int h = 13;

        // --------------------------------------------------------------------------------------- c
        int farRight1, farLeft1, farRight2, farLeft2;
        for (i = 1;; ++i) if (howMighty(chars[y1][x1 + i]) > 0) {
            farRight1 = x1 + i - 1;
            break;
        }
        for (i = 1;; ++i) if (howMighty(chars[y1][x1 - i]) > 0) {
            farLeft1 = x1 - i + 1;
            break;
        }
        for (i = 1;; ++i) if (howMighty(chars[y2][x2 + i]) > 0) {
            farRight2 = x2 + i - 1;
            break;
        }
        for (i = 1;; ++i) if (howMighty(chars[y2][x2 - i]) > 0) {
            farLeft2 = x2 - i + 1;
            break;
        }

        // -----------------------------------******--------------------------------------------- c
        if ((farRight1 - farLeft2) * (farLeft1 - farRight2) <= 0) {
            int[] possibleX = new int[30];
            int numOfPossibleX = 0;
            int commonLeft = Math.max(farLeft1, farLeft2);
            int commonRight = Math.min(farRight1, farRight2);
            int up = Math.min(y1,y2);
            int down = Math.max(y1,y2);

//            Main.pr("Case c", commonLeft, commonRight, up, down);

            for(i = commonLeft; i <= commonRight; ++i) {
                found_C = true;
                for(j = up; j <= down; ++j) if (howMighty(chars[j][i]) > 0) {
                    found_C = false;
                    break;
                }
                if (found_C) {
                    possibleX[numOfPossibleX] = i;
                    numOfPossibleX++;
                }
            }
            if (numOfPossibleX > 0) {
                int X = possibleX[0];
                wayLongX = 999;
//                Main.pr("Case c has found",possibleX);
                for (int f: possibleX) {
                    if (f==0) break;
                    if ( (f - x1) * (f - x2) > 0 ) {
                        int temp = Math.min(Math.abs(f-x1), Math.abs(f-x2));
                        if (temp < wayLongX) {
                            wayLongX = temp;
                            X = f;
//                            Main.pr("Case c: Longer than original", f, wayLongX);
                        }
                    } else {
                        wayLongX = 0;
                        X = f;
                        break;
                    }
                }
                //ans.add(new int[] {x1,y1});
                if (x1 != X)    ans.add(new int[] {X,y1});
                if (y1 != y2)   ans.add(new int[] {X,y2});
                if (X != x2)    ans.add(new int[] {x2,y2});
                // most optimized way found
                if (wayLongX == 0) return ans;
            }
        }

        // --------------------------------------------------------------------------------------- u
        int farUp1, farDown1, farUp2, farDown2;
        for (i = 1;; ++i) if (howMighty(chars[y1 + i][x1]) > 0) {
            farDown1 = y1 + i - 1;
            break;
        }
        for (i = 1;; ++i) if (howMighty(chars[y1 - i][x1]) > 0) {
            farUp1 = y1 - i + 1;
            break;
        }
        for (i = 1;; ++i) if (howMighty(chars[y2 + i][x2]) > 0) {
            farDown2 = y2 + i - 1;
            break;
        }
        for (i = 1;; ++i) if (howMighty(chars[y2 - i][x2]) > 0) {
            farUp2 = y2 - i + 1;
            break;
        }

        if ((farDown1 - farUp2) * (farUp1 - farDown2) <= 0)  {
            int[] possibleY = new int[30];
            int numOfPossibleY = 0;
            int commonUp = Math.max(farUp1, farUp2);
            int commonDown = Math.min(farDown1, farDown2);
            int left = Math.min(x1,x2);
            int right = Math.max(x1,x2);
//            Main.pr("case u", commonUp, commonDown, left, right);

            for(i = commonUp; i <= commonDown; ++i) {
                found_U = true;
                for(j = left; j <= right; ++j) if (howMighty(chars[i][j]) > 0) {
                    found_U = false;
                    break;
                }
                if (found_U) {
                    possibleY[numOfPossibleY] = i;
                    numOfPossibleY++;
                }
            }
            if (numOfPossibleY > 0) {
                int Y = possibleY[0];
                wayLongY = 999;
//                Main.pr("Case u has found", possibleY);
                for (int f: possibleY) {
                    if (f==0) break;
                    if ( (f - y1) * (f - y2) > 0 ) {
                        int temp = Math.min(Math.abs(f-y1), Math.abs(f-y2));
                        if (temp < wayLongY) {
                            wayLongY = temp;
                            Y = f;
//                            Main.pr("Case u: Longer than original", f, wayLongY);
                        }
                    } else {
                        wayLongY = 0;
                        Y = f;
                        break;
                    }
                }
                if (wayLongX < wayLongY) return ans;
                else {
                    ans.clear();
                    //ans.add(new int[]{x1, y1});
                    if (y1 != Y)    ans.add(new int[]{x1, Y});
                    if (x1 != x2)   ans.add(new int[]{x2, Y});
                    if (Y != y2)    ans.add(new int[]{x2, y2});
                }
            }
        }
        return ans;
    }

    public static final String[] infoMenu = {
            "TUTORIAL\n" +
            "1. FOR ONE PLAYER\n" +
            "* Use button \"UP\", \"DOWN\", \"RIGHT\", \"LEFT\" to move your character up, down, right, left.\n" +
            "* Use button \"b\" to place bomb.\n" +
            "* If you have the \"Detonate bomb\" item, use button \"z\" to activate the bomb.\n" +
            "\n" +
            "2. FOR TWO PLAYERS.\n" +
            "* Player 1 (started at the top-left of the screen)\n" +
            "    - Use button \"W\", \"S\", \"D\", \"A\" to move your character up, down, right, left.\n" +
            "    - Use button \"B\" to place bomb.\n" +
            "    - If you have the \"Detonate bomb\" item, use button \"Z\" to activate the bomb.\n" +
            "* Player 2 (started at the bottom-right of the screen)\n" +
            "    - Use button \"UP\", \"DOWN\", \"RIGHT\", \"LEFT\" to move your character up, down, right, left.\n" +
            "    - Use button \"ENTER\" to place bomb.\n" +
            "    - If you have the \"Detonate bomb\" item, use button \"/\" to activate the bomb.\n" +
            "\n" +
            "** REMINDER **\n" +
            "TRY TO FIND THE DOOR, WHERE YOU HAVE TO GO TO AFTER ALL ENEMIES ARE KILLED, AS SOON AS YOU CAN.\n" +
            "\n" +
            "DO NOT TOUCH YOUR ENEMIES OR LET THE TIME RUN OUT.",

            "Credits:\n" +
            "Nguyễn Trọng Đạt\n" +
            "Trần Thị Hằng\n" +
            "Nghiêm Thị Quỳnh Hoa\n" +
            "Cao Đình Hoàng Minh\n",
    };
}
