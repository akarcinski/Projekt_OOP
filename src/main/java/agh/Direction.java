package agh;

public enum Direction {
    NN(0),
    NE(1),
    EE(2),
    SE(3),
    SS(4),
    SW(5),
    WW(6),
    NW(7);
    private final int dir;
    Direction(int s) {
        this.dir = s;
    }
}
