package agh.ics.oop;
import java.util.ArrayList;

public class RectangularMap implements IWorldMap {

    private int width;
    private int heigth;
    private MapVisualizer mapDraw;
    private ArrayList<Animal> animals;

    public RectangularMap(int width, int heigth) {
        this.width = width;
        this.heigth = heigth;
        this.animals = new ArrayList<>();
        this.mapDraw = new MapVisualizer(this);
    }

    public boolean canMoveTo(Vector2d position) {
        return position.x < width && position.y < heigth && position.y >= 0 && position.x >= 0 && !this.isOccupied(position);
    }

    public boolean place(Animal animal) {
        if (this.canMoveTo(animal.getPosition())) {
            animals.add(animal);
            return true;
        }
        return false;
    }

    public boolean isOccupied(Vector2d position) {
        if (animals.size() == 0) return false;
        for(Animal animal: animals){
            if(animal.isAt(position)) return true;
        }
        return false;
    }

    public Object objectAt(Vector2d position) {
        if (animals.size() == 0) return null;
        for(Animal animal: animals){
            if(animal.isAt(position)) return animal;
        }
        return null;
    }

    public String toString() {
        return this.mapDraw.draw(new Vector2d(0,0), new Vector2d(width, heigth));
    }
}
