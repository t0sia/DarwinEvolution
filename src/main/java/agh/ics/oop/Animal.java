package agh.ics.oop;

import java.util.ArrayList;

public class Animal {
    private Vector2d position = new Vector2d(2, 2);
    private MapDirection orientation = MapDirection.NORTH;
    private IWorldMap map;
    private ArrayList<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(IWorldMap map){
        this.map = map;
    }

    public Animal(IWorldMap map, Vector2d initialPosition){
        this.map = map;
        this.position = initialPosition;
    }

    public Vector2d getPosition() {
        return position;
    }

    public String toString() {
        return (orientation.toString());
    }

    public boolean isAt(Vector2d position) {
        return (position.equals(this.position));
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver observer: observers){
            observer.positionChanged(oldPosition, newPosition);
        }
    }

    public void move(MoveDirection direction) {
        Vector2d tempF = position.add(orientation.toUnitVector());
        Vector2d tempB = position.subtract(orientation.toUnitVector());
        switch (direction) {
            case RIGHT: orientation = orientation.next(); break;
            case LEFT: orientation = orientation.previous(); break;
            case FORWARD:
                if(this.map.canMoveTo(tempF)) {
                    positionChanged(position, tempF);
                    position = tempF;
                } break;
            case BACKWARD:
                if(this.map.canMoveTo(tempB)) {
                    positionChanged(position, tempB);
                    position = tempB;
                } break;
        }
    }
}