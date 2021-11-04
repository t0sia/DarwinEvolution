package agh.ics.oop;

public class RectangularMap implements IWorldMap{
    private int width;
    private int heigth;

    public boolean canMoveTo(Vector2d position) {
        return false;
    }
    public boolean place(Animal animal) {
        return false;
    }
    public boolean isOccupied(Vector2d position) {
        return false;
    }
    public Object objectAt(Vector2d position) { return null; }

}
