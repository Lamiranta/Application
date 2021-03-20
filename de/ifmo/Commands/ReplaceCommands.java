package de.ifmo.Commands;

import de.ifmo.Collection.Collection;
import de.ifmo.Product.Product;

public interface ReplaceCommands
{
    public boolean replaceIfGreater(Collection collection, Integer key, Product p);
    public boolean replaceIfLower(Collection collection, Integer key, Product p);
}
