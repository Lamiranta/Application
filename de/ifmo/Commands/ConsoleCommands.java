package de.ifmo.Commands;

import java.io.File;

public interface ConsoleCommands
{
    void clear();
    boolean save(File file);
    void history();
}
