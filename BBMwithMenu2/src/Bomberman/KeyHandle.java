package Bomberman;

public class KeyHandle {
    private boolean P1up;
    private boolean P1down;
    private boolean P1left;
    private boolean P1right;
    private boolean P1setBomb;
    private boolean P1detonateBomb;
    private boolean P2up;
    private boolean P2down;
    private boolean P2left;
    private boolean P2right;
    private boolean P2setBomb;
    private boolean P2detonateBomb;

    public KeyHandle() {
        P1up = false; P1down = false;
        P1left = false; P1right = false;
        P1setBomb = false; P1detonateBomb = false;
        P2up = false; P2down = false;
        P2left = false; P2right = false;
        P2setBomb = false; P2detonateBomb = false;
    }

    public boolean isP1detonateBomb() {
        return P1detonateBomb;
    }

    public boolean isP1down() {
        return P1down;
    }

    public boolean isP1left() {
        return P1left;
    }

    public boolean isP1right() {
        return P1right;
    }

    public boolean isP1setBomb() {
        return P1setBomb;
    }

    public boolean isP1up() {
        return P1up;
    }

    public boolean isP2detonateBomb() {
        return P2detonateBomb;
    }

    public boolean isP2down() {
        return P2down;
    }

    public boolean isP2left() {
        return P2left;
    }

    public boolean isP2right() {
        return P2right;
    }

    public boolean isP2setBomb() {
        return P2setBomb;
    }

    public boolean isP2up() {
        return P2up;
    }

    public void setP1detonateBomb(boolean p1detonateBomb) {
        P1detonateBomb = p1detonateBomb;
    }

    public void setP1setBomb(boolean p1setBomb) {
        P1setBomb = p1setBomb;
    }

    public void setP1up(boolean p1up) {
        P1up = p1up;
    }

    public void setP1down(boolean p1down) {
        P1down = p1down;
    }

    public void setP1left(boolean p1left) {
        P1left = p1left;
    }

    public void setP1right(boolean p1right) {
        P1right = p1right;
    }

    public void setP2detonateBomb(boolean p2detonateBomb) {
        P2detonateBomb = p2detonateBomb;
    }

    public void setP2setBomb(boolean p2setBomb) {
        P2setBomb = p2setBomb;
    }

    public void setP2up(boolean p2up) {
        P2up = p2up;
    }

    public void setP2down(boolean p2down) {
        P2down = p2down;
    }

    public void setP2left(boolean p2left) {
        P2left = p2left;
    }

    public void setP2right(boolean p2right) {
        P2right = p2right;
    }

    public void reset() {
        P1up = false; P1down = false;
        P1left = false; P1right = false;
        P1setBomb = false; P1detonateBomb = false;
        P2up = false; P2down = false;
        P2left = false; P2right = false;
        P2setBomb = false; P2detonateBomb = false;
    }

}

