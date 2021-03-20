package de.ifmo.Commands;

import java.io.File;

public interface ConsoleCommands
{
    public void clear();
    public boolean save(File file);
    public void history();
}
