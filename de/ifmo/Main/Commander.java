package de.ifmo.Main;

import de.ifmo.Collection.Collection;
import de.ifmo.Commands.ConsoleCommands;
import de.ifmo.Commands.ReplaceCommands;
import de.ifmo.Organization.Address;
import de.ifmo.Organization.Organization;
import de.ifmo.Organization.OrganizationType;
import de.ifmo.Product.Coordinates;
import de.ifmo.Product.Product;
import de.ifmo.Product.UnitOfMeasure;

import java.io.*;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Commander implements ConsoleCommands, ReplaceCommands
{
    private String[] commandLog;
    private final String fileName;

    public Commander(String fileName)
    {
        this.fileName = fileName;
    }

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

    public Product initializeProduct(BufferedReader br, Product p) throws IOException
    {
        String[] param;


        System.out.println("Enter the x-y coordinates:");
        param = br.readLine().split(" ",-1);
        Coordinates c = new Coordinates(Integer.parseInt(param[0]), Float.valueOf(param[1]));
        p.setCoordinates(c);

        System.out.println("Enter the price:");
        p.setPrice(Float.parseFloat(br.readLine()));

        System.out.println("Enter the manufacture cost:");
        p.setManufactureCost(Integer.parseInt(br.readLine()));

        System.out.println("Enter the unit of measure. The possible variants are: " + Arrays.toString(UnitOfMeasure.values()));
        p.setUnitOfMeasure(UnitOfMeasure.valueOf(br.readLine()));

        System.out.println("Enter the organization name and number of employments:");
        param = br.readLine().split(" ",-1);
        Organization org = new Organization(param[0],Integer.parseInt(param[1]));

        System.out.println("Enter the organization type. The possible variants are: " + Arrays.toString(OrganizationType.values()));
        org.setType(OrganizationType.valueOf(br.readLine()));

        System.out.println("Enter the postal address:");
        param = br.readLine().split(" ",-1);
        org.setPostalAddress(new Address(param[0]));
        p.setManufacturer(org);
        return p;
    }

    public void workspace(Collection collection) throws IOException
    {
        String[] commandLine = {""};
        boolean noExceptions;
        while(!commandLine[0].equals("exit"))
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String command = br.readLine();
            commandLine = command.split(" ",-1);
            switch (commandLine[0]) {
                case "help":
                    noExceptions = collection.help();
                    if(noExceptions) addCommandToLog(commandLine[0]);
                case "info":
                {
                    collection.info();
                    addCommandToLog(commandLine[0]);
                }
                case "show":
                {
                    collection.show();
                    addCommandToLog(commandLine[0]);
                }
                case "clear":
                {
                    collection.show();
                    addCommandToLog(commandLine[0]);
                }
                case "save":
                    noExceptions = collection.save(new File(this.fileName));
                    if(noExceptions) addCommandToLog(commandLine[0]);
                case "history":
                {
                    this.history();
                    addCommandToLog(commandLine[0]);
                }
                case "insert":
                {
                    Product p = new Product();
                    Integer key = Integer.valueOf(commandLine[1]);
                    String name = commandLine[2];
                    p.setName(name);
                    p = initializeProduct(br, p);
                    collection.insert(key, p);
                    addCommandToLog(commandLine[0]);
                }
                case "update":
                {
                    Integer id = Integer.valueOf(commandLine[1]);
                    Product pUpd = new Product();
                    String name = commandLine[2];
                    pUpd.setName(name);
                    pUpd = initializeProduct(br, pUpd);
                    noExceptions = collection.updateId(id, pUpd);
                    if(noExceptions) addCommandToLog(commandLine[0]);
                }
                case "remove_key":
                {
                    noExceptions = collection.removeKey(Integer.valueOf(commandLine[1]));
                    if(noExceptions) addCommandToLog(commandLine[0]);
                }
            }
        }
    }

    @Override
    public void clear() {}

    @Override
    public boolean save(File file) throws IOException { return false; }

    @Override
    public void history() { System.out.println(Arrays.toString(commandLog)); }

    @Override
    public boolean replaceIfGreater(Integer key) throws NoSuchElementException
    {
        return false;
    }

    @Override
    public boolean replaceIfLower(Integer key) throws NoSuchElementException
    {
        return false;
    }
}
