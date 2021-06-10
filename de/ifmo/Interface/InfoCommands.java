package de.ifmo.Interface;

import de.ifmo.Collection.Collection;

public interface InfoCommands
{
    String help();
    String info(Collection collection);
    String show(Collection collection);
}
