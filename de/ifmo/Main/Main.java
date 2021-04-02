package de.ifmo.Main;

import de.ifmo.Collection.Collection;

import java.io.FileNotFoundException;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        try {
            String fileName = System.getenv("fileName");  /// Fuck this program!!!
            String[] temp = fileName.split("\\.");
            if (fileName.equals("") || !temp[1].equals("csv") || temp.length > 2)
                throw new FileNotFoundException();
            Commander commander = new Commander(fileName);
            commander.workspace(new Collection(), 0, "");
        } catch (Exception e) { System.out.println("You must enter the valid file name before program launch!"); }
    }
}
