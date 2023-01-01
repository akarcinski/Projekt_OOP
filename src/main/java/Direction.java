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
    public static final Direction[] values=values();
    Direction(int s) {
        this.dir = s;
    }

    public static Vector2d toVector2d(Direction direction){
        return switch (direction) {
            case NN -> new Vector2d(0, 1);
            case NE -> new Vector2d(1, 1);
            case EE -> new Vector2d(1, 0);
            case SE -> new Vector2d(1, -1);
            case SS -> new Vector2d(0, -1);
            case SW -> new Vector2d(-1, -1);
            case WW -> new Vector2d(-1, 0);
            case NW -> new Vector2d(-1, 1);
        };
    }
}
