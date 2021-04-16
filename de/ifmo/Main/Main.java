package de.ifmo.Main;

import de.ifmo.Collection.Collection;

import java.io.FileNotFoundException;

public class Main
{
    public static void main(String[] args)
    {
        try {
            String fileName = System.getenv("fileName");  /// Fuck this program!!!
            String[] temp = fileName.split("\\.");
            if (fileName.equals("") || !temp[1].equals("csv") || temp.length > 2)
                throw new FileNotFoundException("The name of your file is invalid!");

            Commander commander = new Commander(fileName);
            Collection collection = new Collection();
            commander.fillCollection(collection);

            System.out.println("Welcome to the application tab! Your collection was successful read!");
            commander.workspace(collection, 0, "");
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }
}
