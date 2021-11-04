package agh.ics.oop;

public class Animal {
    private Vector2d position = new Vector2d(2, 2);
    private MapDirection orientation = MapDirection.NORTH;
    private IWorldMap map;

    public Animal(IWorldMap map){
        this.map = map;
    }

    public Animal(IWorldMap map, Vector2d initialPosition){
        this.map = map;
        this.position = initialPosition;
    }

    public String toString() {
        return (orientation.toString());
    }

    boolean isAt(Vector2d position) {
        return (position.equals(this.position));
    }

    public void move(MoveDirection direction) {
        Vector2d tempF = position.add(orientation.toUnitVector());
        Vector2d tempB = position.subtract(orientation.toUnitVector());
        switch (direction) {
            case RIGHT: orientation = orientation.next(); break;
            case LEFT: orientation = orientation.previous(); break;
            case FORWARD:
                if(this.map.canMoveTo(tempF)) { //if canMoveTo ...
                    position = tempF;
                } break;
            case BACKWARD:
                if(this.map.canMoveTo(tempB)) {
                    position = tempB;
                } break;
        }
    }
}