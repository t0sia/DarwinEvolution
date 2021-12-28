package agh.ics.oop;

public enum MapDirection {
    NORTH,
    NORTH_EAST,
    NORTH_WEST,
    SOUTH,
    SOUTH_WEST,
    SOUTH_EAST,
    WEST,
    EAST;


    public MapDirection turn45() {
        return getMapDirection(NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST, NORTH);
    }

    public MapDirection turn90() {
        return getMapDirection(EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST, NORTH, NORTH_EAST);
    }

    public MapDirection turn135() {
        return getMapDirection(SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST, NORTH, NORTH_EAST, EAST);
    }

    public MapDirection turn180() {
        return getMapDirection(SOUTH, SOUTH_WEST, WEST, NORTH_WEST, NORTH,NORTH_EAST, EAST, SOUTH_EAST);
    }

    public MapDirection turn225() {
        return getMapDirection(SOUTH_WEST, WEST, NORTH_WEST, NORTH,NORTH_EAST, EAST, SOUTH_EAST, SOUTH);
    }

    public MapDirection turn270() {
        return getMapDirection(WEST, NORTH_WEST, NORTH,NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST);
    }

    private MapDirection getMapDirection(MapDirection east, MapDirection southEast, MapDirection south, MapDirection southWest, MapDirection west, MapDirection northWest, MapDirection north, MapDirection northEast) {
        return switch (this) {
            case NORTH -> east;
            case NORTH_EAST -> southEast;
            case EAST -> south;
            case SOUTH_EAST -> southWest;
            case SOUTH -> west;
            case SOUTH_WEST -> northWest;
            case WEST -> north;
            default -> northEast;
        };
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0,1);
            case NORTH_EAST -> new Vector2d(1,1);
            case NORTH_WEST -> new Vector2d(-1,1);
            case WEST -> new Vector2d(-1,0);
            case SOUTH_EAST -> new Vector2d(1,-1);
            case SOUTH_WEST -> new Vector2d(-1,-1);
            case SOUTH -> new Vector2d(0,-1);
            default -> new Vector2d(1,0);
        };
    }

    public String toString() {
        return switch (this) {
            case NORTH -> "0";
            case NORTH_EAST -> "1";
            case NORTH_WEST -> "2";
            case WEST -> "3";
            case SOUTH_EAST -> "4";
            case SOUTH_WEST -> "5";
            case SOUTH -> "6";
            default -> "7";
        };
    }
}