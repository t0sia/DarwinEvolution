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
        Vector2d tempF = position.add(orientation.toUnitVector());
        Vector2d tempB = position.subtract(orientation.toUnitVector());
        switch (direction) {
            case RIGHT: orientation = orientation.next(); break;
            case LEFT: orientation = orientation.previous(); break;
            case FORWARD:
                if(tempF.x <= 4 && tempF.x >= 0 && tempF.y <= 4 && tempF.y >= 0) {
                    position = position.add(orientation.toUnitVector());
                } break;
            case BACKWARD:
                if(tempB.x <= 4 && tempB.x >= 0 && tempB.y <= 4 && tempB.y >= 0) {
                    position = position.subtract(orientation.toUnitVector());
                } break;
        }
    }
}