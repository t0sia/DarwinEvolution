package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected Vector2d downLeft = new Vector2d(1,1);
    protected Vector2d upRight = new Vector2d(0,0);
    protected HashMap<Vector2d,Animal> animals = new HashMap<>();
    protected ArrayList<Animal> animalsArray = new ArrayList<>();
    protected MapVisualizer mapDraw = new MapVisualizer(this);

    public boolean isOccupied(Vector2d position) {
        if(animals.containsKey(position)) return true;
        return false;
    }

    public boolean place(Animal animal) {
        if (this.canMoveTo(animal.getPosition())) {
            downLeft = downLeft.lowerLeft(animal.getPosition());
            upRight = upRight.upperRight(animal.getPosition());
            animals.put(animal.getPosition(), animal);
            return true;
        }
        return false;
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = animals.get(oldPosition);
        animals.remove(oldPosition);
        animals.put(newPosition, animal);
    }

    public Object objectAt(Vector2d position) {
        return animals.get(position);
    }

    public String toString(){
        return this.mapDraw.draw(downLeft, upRight);
    }
}
