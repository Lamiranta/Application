package de.ifmo.Main;

import de.ifmo.Collection.Collection;
import de.ifmo.Collection.Printable;
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
        this.commandLog = new String[14];
        this.fileName = fileName;
    }

    public void addCommandToLog(String command)
    {
        boolean isSaved = false;
        for (int i = 0; i < 13; ++i)
        {
            if (this.commandLog[i].equals(""))
            {
                this.commandLog[i] = command;
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

    // In progress!!!
    public void fillCollection() throws IOException
    {
        InputStreamReader input = new InputStreamReader(new FileInputStream(new File(fileName)));
        StringBuilder temp;
        Integer count;
        while (input.read() != -1)
        {
            temp = new StringBuilder();
            count = 0;
            while (input.read() != '\n' || input.read() != ',')
            {
                temp.append(input.read());
            }
        }
    }

    public Product initializeProduct(BufferedReader br, Product p)
    {
        String[] param;

        while(true) {
            try {
                System.out.println("Enter the x-y coordinates:");
                param = br.readLine().split("\\s+");
                Coordinates c = new Coordinates(Integer.parseInt(param[0]), Float.valueOf(param[1]));
                p.setCoordinates(c);
                break;
            } catch(Exception e) { System.out.println(e.getMessage()); }
        }

        while(true) {
            try {
                System.out.println("Enter the price:");
                p.setPrice(Float.parseFloat(br.readLine()));
                break;
            } catch(Exception e) { System.out.println(e.getMessage()); }
        }

        while(true) {
            try {
                System.out.println("Enter the manufacture cost:");
                p.setManufactureCost(Integer.parseInt(br.readLine()));
                break;
            } catch(Exception e) { System.out.println(e.getMessage()); }
        }

        while(true) {
            try {
                System.out.println("Enter the unit of measure. The possible variants are: " + Arrays.toString(UnitOfMeasure.values()));
                p.setUnitOfMeasure(UnitOfMeasure.valueOf(br.readLine()));
                break;
            } catch(Exception e) { System.out.println(e.getMessage()); }
        }

        while(true) {
            try {
                System.out.println("Enter the organization name and number of employments:");
                param = br.readLine().split("\\s+");
                Organization org = new Organization(param[0], Integer.parseInt(param[1]));

                System.out.println("Enter the organization type. The possible variants are: " + Arrays.toString(OrganizationType.values()));
                org.setType(OrganizationType.valueOf(br.readLine()));

                System.out.println("Enter the postal address:");
                org.setPostalAddress(new Address(br.readLine()));

                p.setManufacturer(org);
                break;
            } catch(Exception e) { System.out.println(e.getMessage()); }
        }
        return p;
    }

    public void workspace(Collection collection, Integer recursion_count, String commandFile) throws IOException
    {
        String[] commandLine = {""};
        boolean noExceptions;
        Printable printable = new Printable();

        while(!commandLine[0].equals("exit"))
        {
            BufferedReader br;
            if (commandFile.equals(""))
                br = new BufferedReader(new InputStreamReader(System.in));
            else
                br = new BufferedReader(new FileReader(commandFile));
            String command = br.readLine();
            commandLine = command.split("\\s+");
            switch (commandLine[0]) {
                case "help":
                    printable.help();
                    addCommandToLog(commandLine[0]);
                case "info":
                {
                    printable.info(collection);
                    addCommandToLog(commandLine[0]);
                }
                case "show":
                {
                    printable.show(collection);
                    addCommandToLog(commandLine[0]);
                }
                case "clear":
                {
                    collection.clear();
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
                case "replace_if_greater":
                {
                    Integer key = Integer.valueOf(commandLine[1]);
                    Product p = new Product();
                    String name = commandLine[2];
                    p.setName(name);
                    p = initializeProduct(br, p);
                    replaceIfGreater(collection, key, p);
                }
                case "replace_if_lower":
                {
                    Integer key = Integer.valueOf(commandLine[1]);
                    Product p = new Product();
                    String name = commandLine[2];
                    p.setName(name);
                    p = initializeProduct(br, p);
                    replaceIfLower(collection, key, p);
                }
                case "average_of_manufacture_cost":
                {
                    if (collection.getHashtable().size() > 0)
                    {
                        System.out.println(collection.averageOfManufactureCost());
                    }
                    else
                        System.out.println("The collection is empty! Please, add the elements!");
                }
                case "max_by_manufacturer":
                {
                    if (collection.getHashtable().size() > 0)
                    {
                        Product p_max = collection.maxByManufacturer();
                        printable.printProduct(p_max);
                    }
                    else
                        System.out.println("The collection is empty! Please, add the elements!");
                }
                case "count_less_than_manufacture_cost":
                {
                    if (collection.getHashtable().size() > 0)
                    {
                        System.out.println(collection.countLessThanManufactureCost(Integer.valueOf(commandLine[1])));
                    }
                    else
                        System.out.println("The collection is empty! Please, add the elements!");
                }
                case "execute_script":
                {
                    if (recursion_count < 20)
                        this.workspace(collection, recursion_count++, commandLine[1]);
                    else
                        System.out.println("The free number of recursion is ended! For more recursions, please donate :)");
                }
            }
        }
    }

    @Override
    public void clear() {}

    @Override
    public boolean save(File file) { return false; }

    @Override
    public void history() { System.out.println(Arrays.toString(commandLog)); }

    @Override
    public boolean replaceIfGreater(Collection collection, Integer key, Product p) throws NoSuchElementException
    {
        try {
            Product target = collection.getHashtable().get(key);
            if (target == null) throw new NoSuchElementException("There is no elements with such key!");
            else if (target.getPrice() < p.getPrice())
            {
                collection.getHashtable().replace(key, target, p);
                System.out.println("The element was replaced successfully!");
            }
            else
                System.out.println("The current element has greater value!");
            return true;
        } catch(NoSuchElementException e) { System.out.println(e.getMessage()); }
        return false;
    }

    @Override
    public boolean replaceIfLower(Collection collection, Integer key, Product p) throws NoSuchElementException
    {
        try {
            Product target = collection.getHashtable().get(key);
            if (target == null) throw new NoSuchElementException("There is no elements with such key!");
            else if (target.getPrice() > p.getPrice())
            {
                collection.getHashtable().replace(key,target,p);
                System.out.println("The element was replaced successfully!");
            }
            else
                System.out.println("The current element has lower value!");
            return true;
        } catch(NoSuchElementException e) { System.out.println(e.getMessage()); }
        return false;
    }
}
