package agh.ics.oop;

public enum MapDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST;

    public String toString() {
        return switch (this) {
            case NORTH -> "Polnoc";
            case EAST -> "Wschod";
            case WEST -> "Zachod";
            default -> "Poludnie";
        };
    }

    public MapDirection next() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case WEST -> NORTH;
            default -> WEST;
        };
    }

    public MapDirection previous() {
        return switch (this) {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            default -> NORTH;
        };
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0,1);
            case WEST -> new Vector2d(-1,0);
            case SOUTH -> new Vector2d(0,-1);
            default -> new Vector2d(1,0);
        };
    }
}