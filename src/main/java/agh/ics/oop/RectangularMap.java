package agh.ics.oop;

public class RectangularMap extends AbstractWorldMap implements IWorldMap {

    private int width;
    private int heigth;

    public RectangularMap(int width, int heigth) {
        this.width = width;
        this.heigth = heigth;
        this.downLeft = new Vector2d(0,0);
        this.upRight = new Vector2d(width - 1,heigth - 1);
    }

    public boolean canMoveTo(Vector2d position) {
        return position.x < width && position.y < heigth && position.y >= 0 && position.x >= 0 && !this.isOccupied(position);
    }


}
