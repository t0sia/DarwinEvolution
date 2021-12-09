package agh.ics.oop;

import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver{
    private SortedSet<Vector2d> X = new TreeSet<>(new MapComp());
    private SortedSet<Vector2d> Y = new TreeSet<>(new MapComp());

    public void addToBoundariesList(Animal animal) {
        Vector2d animalPosition = animal.getPosition();
        this.X.add(new Vector2d (animalPosition.x, animalPosition.y));
        this.Y.add(new Vector2d (animalPosition.y, animalPosition.x));
    }

    public void addToBoundariesList(Grass grass) {
        Vector2d grass_position = grass.getPosition();
        this.X.add(new Vector2d (grass_position.x, grass_position.y));
        this.Y.add(new Vector2d (grass_position.y, grass_position.x));
    }
    public Vector2d upperBoundary() { return new Vector2d(this.X.last().x, this.Y.last().y); }
    public Vector2d lowerBoundary() { return new Vector2d(this.X.first().x, this.Y.first().y); }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        this.X.remove(oldPosition);
        this.X.add(newPosition);
        this.Y.remove(new Vector2d(oldPosition.y, oldPosition.x));
        this.Y.add(new Vector2d(newPosition.y, newPosition.x));
    }
}
