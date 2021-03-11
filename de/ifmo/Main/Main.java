package de.ifmo.Main;

import de.ifmo.Collection.Collection;

import java.io.FileNotFoundException;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        try {
            String fileName = System.getenv(args[0]);
            String[] temp = fileName.split("\\.");
            if (fileName.equals("") || !temp[1].equals("csv"))
                throw new FileNotFoundException();
            Commander commander = new Commander(fileName);
            commander.workspace(new Collection());
        } catch (Exception e) { System.out.println("You must enter the valid file name before program launch!"); }
    }
}
