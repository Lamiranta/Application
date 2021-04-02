package de.ifmo.Commands;

import de.ifmo.Collection.Collection;
import de.ifmo.Product.Product;

public interface InfoCommands
{
    public void help();
    public void info(Collection collection);
    public void show(Collection collection);
}
