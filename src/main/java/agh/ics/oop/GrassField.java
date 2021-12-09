package agh.ics.oop;
import java.util.Random;
import java.util.ArrayList;
import java.lang.Math;

public class GrassField extends AbstractWorldMap implements IWorldMap{

    private ArrayList<Grass> grasses;
    private MapBoundary grassBoundary = new MapBoundary();

    public GrassField(int grass){
        grasses = new ArrayList<>();
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
                grassBoundary.addToBoundariesList(new Grass(newGrassVector));
            }
        }
    }

    public boolean canMoveTo(Vector2d position) {
        return !this.isOccupied(position);
    }


    public Object objectAt(Vector2d position) {
        Object thing = super.objectAt(position);
        if(thing != null) return thing;
        if (grasses.size() != 0) for (Grass grass : grasses) if (grass.getPosition().equals(position)) return grass;
        return null;
    }

    public boolean isOccupied(Vector2d position){
        if (super.isOccupied(position)) return true;
        if (grasses.size() != 0) {
            for (Grass grass : grasses) {
                if (grass.getPosition().equals(position)) return true;
            }
        }
        return false;
    }

}
