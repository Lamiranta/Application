package de.ifmo.Commands;

import de.ifmo.Product.Product;

public interface ManufactureCommands
{
    Integer averageOfManufactureCost();
    Product maxByManufacturer();
    Integer countLessThanManufactureCost(Integer manufactureCost);
}
