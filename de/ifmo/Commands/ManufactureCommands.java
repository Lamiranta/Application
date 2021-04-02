package de.ifmo.Commands;

import de.ifmo.Collection.Collection;
import de.ifmo.Product.Product;

public interface ManufactureCommands
{
    public Integer averageOfManufactureCost();
    public Product maxByManufacturer();
    public Integer countLessThanManufactureCost(Integer manufactureCost);
}
