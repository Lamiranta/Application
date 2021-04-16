package de.ifmo.Commands;

import de.ifmo.Product.Product;

public interface ElementCommands
{
    void insert(String key, Product product);
    boolean updateId(Integer id, Product product);
    boolean removeKey(String key);
}
