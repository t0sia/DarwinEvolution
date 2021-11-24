package agh.ics.oop;
import java.util.Random;
import java.util.ArrayList;
import java.lang.Math;

public class GrassField extends AbstractWorldMap implements IWorldMap{

    public GrassField(int grass){
        int n = (int)Math.sqrt(10*grass);
        int i = 0;
        Random rand = new Random();
        while (i < grass){
            Vector2d newGrassVector = new Vector2d(rand.nextInt(n), rand.nextInt(n));
            if(this.objectAt(newGrassVector) == null){
                i += 1;
                grasses.add(new Grass(newGrassVector));
                downLeft = downLeft.lowerLeft(newGrassVector);
                upRight = upRight.upperRight(newGrassVector);
            }
        }
    }

    public boolean canMoveTo(Vector2d position) {
        return !this.isOccupied(position);
    }

    public boolean place(Animal animal) {
        if (this.canMoveTo(animal.getPosition())) {
            downLeft = downLeft.lowerLeft(animal.getPosition());
            upRight = upRight.upperRight(animal.getPosition());
            animals.add(animal);
            return true;
        }
        return false;
    }

    public Object objectAt(Vector2d position) {
        if (animals.size() != 0) for(Animal animal: animals) if(animal.isAt(position)) return animal;
        if (grasses.size() != 0) for(Grass grass: grasses) if(grass.getPosition().equals(position)) return grass;

        return null;
    }

}
