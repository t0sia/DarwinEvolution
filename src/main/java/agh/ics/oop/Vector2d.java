package agh.ics.oop;

import java.util.Objects;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    public String toString() {
        return ("(" + this.x + "," + this.y + ")");
    }

    public boolean precedes(Vector2d other) {
        return (other.x <= this.x && other.y <= this.y);
    }

    public boolean follows(Vector2d other) {
        return (other.x >= this.x && other.y >= this.y);
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(other.x, this.x), Math.max(other.y, this.y));
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(other.x, this.x), Math.min(other.y, this.y));
    }

    public Vector2d add(Vector2d other) {
        return (new Vector2d(this.x + other.x, this.y + other.y));
    }

    public Vector2d subtract(Vector2d other) {
        return (new Vector2d(Math.abs(this.x - other.x), Math.abs(this.y - other.y)));
    }

    public boolean equals(Object other) {
        if (other instanceof Vector2d) {
            String res1 = other.toString();
            String res2 = this.toString();
            if (res2.equals(res1)) return true;
        }
        return false;
    }

    public Vector2d opposite() {
        return (new Vector2d(this.x * (-1), this.y * (-1)));
    }
}