package de.ifmo.Commands;

import de.ifmo.Product.Product;

public interface ElementCommands
{
    boolean insert(Integer key, Product product);
    boolean updateId(Integer id, Product product);
    boolean removeKey(Integer key);
}
