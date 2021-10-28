package agh.ics.oop;

class Animal {
    private Vector2d position = new Vector2d(2, 2);
    private MapDirection orientation = MapDirection.NORTH;

    public String toString() {
        return ("Position: " + position.toString() + " Orientation: " + orientation.toString());
    }

    boolean isAt(Vector2d position) {
        return (position.equals(this.position));
    }

    void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
            case FORWARD -> position = position.add(orientation.toUnitVector());
            case BACKWARD -> position = position.subtract(orientation.toUnitVector());
        }
        while (position.x > 4) position = position.add(new Vector2d(-1,0));
        while (position.x < 0) position = position.add(new Vector2d(1,0));
        while (position.y > 4) position = position.add(new Vector2d(0,-1));
        while (position.y < 0) position = position.add(new Vector2d(0,1));
    }
}