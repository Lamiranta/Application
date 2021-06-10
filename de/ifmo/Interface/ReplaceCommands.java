package de.ifmo.Interface;

import de.ifmo.Product.Product;

public interface ReplaceCommands
{
    String replaceIfGreater(String key, Product p);
    String replaceIfLower(String key, Product p);
}
