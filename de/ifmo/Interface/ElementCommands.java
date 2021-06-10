package de.ifmo.Interface;

import de.ifmo.Product.Product;

public interface ElementCommands
{
    String insert(String key, Product product);
    String updateId(Integer id, Product product);
    String removeKey(String key);
}
