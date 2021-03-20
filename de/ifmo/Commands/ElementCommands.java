package de.ifmo.Commands;

import de.ifmo.Product.Product;

public interface ElementCommands
{
    public void insert(Integer key, Product product);
    public boolean updateId(Integer id, Product product);
    public boolean removeKey(Integer key);
}
