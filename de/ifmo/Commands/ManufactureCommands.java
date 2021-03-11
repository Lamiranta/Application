package de.ifmo.Commands;

import de.ifmo.Collection.Collection;
import de.ifmo.Product.Product;

public interface ManufactureCommands
{
    public Integer averageOfManufactureCost(Collection collection);
    public Product maxByManufacturer(Collection collection);
    public Integer countLessThanManufactureCost(Integer manufactureCost);
}
