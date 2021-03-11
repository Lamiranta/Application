package de.ifmo.Commands;

import java.io.File;
import java.io.IOException;

public interface ConsoleCommands
{
    public void clear();
    public void save(File file) throws IOException;
    public void history();
}
