package agh.ics.oop;

import java.util.Comparator;

public class MapComp implements Comparator<Vector2d>
{
    public int compare(Vector2d bound1, Vector2d bound2) {
        if (bound1.x != bound2.x) { return(bound1.x-bound2.x); }
        else if (bound1.y != bound2.y) { return(bound1.y-bound2.y); }
        return 0;
    }
}