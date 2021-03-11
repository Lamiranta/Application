package de.ifmo.Main;

import de.ifmo.Collection.Collection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Commander
{
    private String command;
    private String fileName;

    public Commander(String fileName)
    {
        this.command = "";
        this.fileName = fileName;
    }

    public void setCommand() throws IOException
    {
        try {
            InputStreamReader input = new InputStreamReader(new FileInputStream(this.fileName));
            StringBuilder temp = new StringBuilder();
            while (!String.valueOf(input.read()).equals(" ")) {
                temp.append(String.valueOf(input.read()));
            }
            String[] moreTemp = temp.toString().split(" ", -1);
            this.command = moreTemp[0];
        } catch (IOException e) { System.out.println("Error while reading the file!"); }
    }

    public void workspace(Collection collection) throws Exception
    {
        while(!this.command.equals("exit"))
        {
            setCommand();
            switch (this.command) {
                case "help":
                    collection.help();
                case "info":
                    collection.info();
                case "show":
                    collection.show();
                case "clear":
                    collection.clear();
                case "save":
                    collection.save(new File(this.fileName));
            }
        }
    }
}
