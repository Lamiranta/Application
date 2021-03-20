package de.ifmo.Product;

import org.w3c.dom.ranges.RangeException;

public class Coordinates
{
    private long x;
    private Float y;

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
