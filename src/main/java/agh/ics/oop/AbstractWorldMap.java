package agh.ics.oop;

import java.util.ArrayList;

public abstract class AbstractWorldMap implements IWorldMap{
    protected Vector2d downLeft = new Vector2d(1,1);
    protected Vector2d upRight = new Vector2d(0,0);
    protected ArrayList<Animal> animals = new ArrayList<>();
    protected ArrayList<Grass> grasses = new ArrayList<>();
    protected MapVisualizer mapDraw = new MapVisualizer(this);

    public boolean isOccupied(Vector2d position) {
        if (animals.size() != 0){
            for(Animal animal: animals) {
                if (animal.isAt(position)) return true;
            }
        }
        if(this instanceof GrassField) {
            if (grasses.size() != 0) {
                for (Grass grass : grasses) {
                    if (grass.getPosition().equals(position)) return true;
                }
            }
        }
        return false;
    }

    public String toString(){
        return this.mapDraw.draw(downLeft, upRight);
    }
}
