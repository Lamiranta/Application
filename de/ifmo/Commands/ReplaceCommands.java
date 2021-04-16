package de.ifmo.Commands;

import de.ifmo.Collection.Collection;
import de.ifmo.Product.Product;

public interface ReplaceCommands
{
    boolean replaceIfGreater(Collection collection, String key, Product p);
    boolean replaceIfLower(Collection collection, String key, Product p);
}
