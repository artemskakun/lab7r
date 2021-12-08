package Lib.Data;

import java.io.Serializable;

/**
 * X-Y coordinates.
 */
public class Coordinates implements Serializable {
    private Long x;
    private Long y;

    public Coordinates(Long x, Long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return X-coordinate.
     */
    public Long getX() {
        return x;
    }

    /**
     * @return Y-coordinate.
     */
    public Long getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int hashCode() {
        return y.hashCode() + x.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Coordinates) {
            Coordinates coordinates = (Coordinates) obj;
            return (x == coordinates.getX()) && y.equals(coordinates.getY());
        }
        return false;
    }
}