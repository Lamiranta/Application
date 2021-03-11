package de.ifmo.Collection;

import de.ifmo.Commands.ConsoleCommands;
import de.ifmo.Commands.InfoCommands;
import de.ifmo.Product.Product;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Hashtable;

public class Collection implements InfoCommands, ConsoleCommands
{
    private Hashtable<String, Product> hashtable;
    private String[] commandLog;
    private java.time.LocalDate createDate;

    public Collection(Product... product)
    {
        this.createDate = LocalDate.now();
        this.commandLog = new String[14];
        this.hashtable = new Hashtable<String, Product>(32,(float) 0.5);
    }

    public Hashtable<String, Product> getHashtable() { return this.hashtable; }

    public String[] getCommandLog() { return this.commandLog; }

    public void addCommandToLog(String command)
    {
        boolean isSaved = false;
        for (String str : commandLog)
        {
            if (str.equals(""))
            {
                str = command;
                isSaved = true;
                break;
            }
        }
        if (!isSaved)
        {
            String[] temp = new String[14];
            System.arraycopy(commandLog, 1, temp, 0, 13);
            temp[13] = command;
            this.commandLog = temp;
        }
    }

    @Override
    public void help()
    {
        File file = new File("\\help.txt"); /// need to change path!
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = br.readLine()) != null) System.out.println(line);
        } catch (IOException e) { System.out.println("File 'help' not found!"); }
    }

    @Override
    public void info()
    {
        System.out.println("Type of collection: Hashtable");
        System.out.println("Initialization date: " + createDate.toString());
        System.out.println("Size of collection: " + hashtable.size());
    }

    @Override
    public void show() { System.out.println(hashtable.toString()); }

    @Override
    public void clear()
    {
        this.hashtable.clear();
        System.out.println("The collection was cleared successful!");
    }

    @Override
    public void save(File file) throws IOException
    {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for (Product p : hashtable.values())
            {
                String temp = p.getId() + "," + p.getName() + "," + p.getCoordinates() + "," + p.getCreationDate() + ",";
                temp += p.getPrice() + "," + p.getManufactureCost() + "," + p.getUnitOfMeasure() + ",";
                temp += p.getManufacturer() + "\n";
                byte[] translate = temp.getBytes();
                fileOutputStream.write(translate);
            }
            System.out.println("File was saved successful!");
        } catch(IOException e) { System.out.println("Error while writing the file!"); }
    }

    @Override
    public void history() { System.out.println(Arrays.toString(commandLog)); }
}
