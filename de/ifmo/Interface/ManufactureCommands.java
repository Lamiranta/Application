package de.ifmo.Interface;

import de.ifmo.Product.Product;

public interface ManufactureCommands
{
    Integer averageOfManufactureCost();
    Product maxByManufacturer();
    Integer countLessThanManufactureCost(Integer manufactureCost);
}
