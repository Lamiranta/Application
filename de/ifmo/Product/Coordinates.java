package de.ifmo.Product;

import org.w3c.dom.ranges.RangeException;

import java.io.Serializable;

/**
 * This class contains the information about the coordinates of specific product.
 */
public class Coordinates implements Serializable
{
    /** The x-coordinate of specific product. */
    private long x;
    /** The y-coordinate of specific product. The value cannot be greater than 261. */
    private Float y;

    /**
     * Constructs a new object of this class and sets specific coordinates to it.
     * @param x specific x-coordinate
     * @param y specific y-coordinate
     */
    public Coordinates(long x, Float y)
    {
        setX(x);
        setY(y);
    }

    public void setX(long x) { this.x = x; }

    public void setY(Float y) throws NullPointerException, RangeException
    {
        if (y == null)
            throw new NullPointerException("The y-coordinate must have a value!");
        else if (y > 261)
            throw new RangeException(RangeException.BAD_BOUNDARYPOINTS_ERR, "Invalid Range! The y-coordinate must be less than 262.");
        else
            this.y = y;
    }

    public long getX() { return this.x; }
    public Float getY() { return this.y; }
}
