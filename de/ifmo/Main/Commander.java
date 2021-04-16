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

/**
 * The class of commander is used as a main class for work with user-defined collections.
 * The commander contains the methods that allow to user to change the structure of collection and parameters inside its values.
 * While working with collection user can through commander saves the changes to the file that was chosen
 * at the beginning of the work session.
 */
public class Commander implements ConsoleCommands, ReplaceCommands
{
    private String[] commandLog;
    private final String fileName;

    /**
     * Constructs a commander and gets access to the file with initial collection.
     * @param fileName commander-used file
     */
    public Commander(String fileName)
    {
        this.commandLog = new String[0];
        this.fileName = fileName;
    }

    /**
     * Adds last entered command to the command log.
     * @param command last entered command
     */
    public void addCommandToLog(String command)
    {
        int length = this.commandLog.length;
        String[] temp;
        if (length < 14)
        {
            temp = new String[length + 1];
            if (length > 0) System.arraycopy(commandLog, 0, temp, 0, length);
            temp[length] = command;
        }
        else
        {
            temp = new String[14];
            System.arraycopy(commandLog, 1, temp, 0, 13);
            temp[13] = command;
        }
        this.commandLog = temp;
    }

    /**
     * Fills the collection that was defined by user in commander-used file.
     * @param collection the user-defined collection
     * @throws IOException if there is an error while initializing the reader
     */
    public void fillCollection(Collection collection) throws IOException
    {
        InputStreamReader input = new InputStreamReader(new FileInputStream(new File(fileName)));
        StringBuilder temp;
        String helpingTemp = "";
        Product p = new Product();
        int count = 0, key_count = 0, c;
        do {
            temp = new StringBuilder();
            do {
                c = input.read();
                if (c == (int) ',' || c == (int) '\n' || c == -1) { count++; break; }
                else
                    temp.append(Character.toChars(c));
            } while (true);

            if (count == 1) p = new Product();

            switch (count) {
                case 1 -> p.setName(temp.toString());
                case 2, 7 -> helpingTemp = temp.toString();
                case 3 -> p.setCoordinates(new Coordinates(Long.parseLong(helpingTemp), Float.parseFloat(temp.toString())));
                case 4 -> p.setPrice(Float.parseFloat(temp.toString()));
                case 5 -> p.setManufactureCost(Integer.parseInt(temp.toString()));
                case 6 -> p.setUnitOfMeasure(UnitOfMeasure.valueOf(temp.toString()));
                case 8 -> p.setManufacturer(new Organization(helpingTemp, Integer.parseInt(temp.toString())));
                case 9 -> p.getManufacturer().setType(OrganizationType.valueOf(temp.toString()));
                case 10 -> p.getManufacturer().setPostalAddress(new Address(temp.toString()));
            }
            if (c == (int) '\n' || c == -1)
            {
                collection.getHashtable().put("key_" + key_count, p);
                count = 0;
                key_count++;
            }
        } while (c != -1);
    }

    /**
     * Initializes the new product defined by user through command prompt.
     * @param br reader that used to read line entered in command prompt
     * @param p product that used for storing the information about user-defined product
     * @return product defined by user
     */
    public Product initializeProduct(BufferedReader br, Product p)
    {
        String[] param;

        while(true) {
            try {
                System.out.print("Enter the coordinates. ");
                System.out.println("The value of x must be an integer, the value of y must be less than 262:");
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
                System.out.println("Enter the manufacture cost. The value of manufacture cost must be an integer:");
                p.setManufactureCost(Integer.parseInt(br.readLine()));
                break;
            } catch(Exception e) { System.out.println(e.getMessage()); }
        }

        while(true) {
            try {
                System.out.print("Enter the unit of measure. The possible variants are: ");
                System.out.println(Arrays.toString(UnitOfMeasure.values()));
                p.setUnitOfMeasure(UnitOfMeasure.valueOf(br.readLine()));
                break;
            } catch(Exception e) { System.out.println(e.getMessage()); }
        }

        while(true) {
            try {
                System.out.println("Enter the organization name and number of employments:");
                param = br.readLine().split("\\s+");
                Organization org = new Organization(param[0], Integer.parseInt(param[1]));

                System.out.print("Enter the organization type. The possible variants are: ");
                System.out.println(Arrays.toString(OrganizationType.values()));
                org.setType(OrganizationType.valueOf(br.readLine()));

                System.out.println("Enter the postal address:");
                org.setPostalAddress(new Address(br.readLine()));

                p.setManufacturer(org);
                break;
            } catch(Exception e) { System.out.println(e.getMessage()); }
        }
        return p;
    }

    /**
     * Initializes the new product defined by user through script file.
     * The method is used when command "insert" is called in script file.
     * @param br reader that used to read line in script file
     * @param p product that used for storing the information about user-defined product
     * @return product defined by user
     */
    public Product initializeProductThroughScript(BufferedReader br, Product p)
    {
        String[] param;

        try {
            param = br.readLine().split("\\s+");
            Coordinates c = new Coordinates(Integer.parseInt(param[0]), Float.valueOf(param[1]));
            p.setCoordinates(c);

            p.setPrice(Float.parseFloat(br.readLine()));

            p.setManufactureCost(Integer.parseInt(br.readLine()));

            p.setUnitOfMeasure(UnitOfMeasure.valueOf(br.readLine()));

            param = br.readLine().split("\\s+");
            Organization org = new Organization(param[0], Integer.parseInt(param[1]));

            org.setType(OrganizationType.valueOf(br.readLine()));

            org.setPostalAddress(new Address(br.readLine()));

            p.setManufacturer(org);
        } catch(Exception e) { System.out.println(e.getMessage()); }

        return p;
    }

    /**
     * The main method used as a workspace while working through command prompt.
     * It is used for entering commands and calling corresponding methods.
     * @param collection the user-defined collection with which the commander works
     * @param recursion_count count the number of "execute_script" commands that were entered
     * @param commandFile file used for reading commands while "execute_script" is active
     * @throws IOException if there is an error while initializing the reader
     */
    public void workspace(Collection collection, Integer recursion_count, String commandFile) throws IOException
    {
        String[] commandLine = {""};
        Printable printable = new Printable();
        BufferedReader br;
        if (commandFile.equals(""))
            br = new BufferedReader(new InputStreamReader(System.in));
        else
        {
            try {
                br = new BufferedReader(new FileReader(commandFile));
            } catch (IOException e) {
                System.out.println("Incorrect name of file!");
                return;
            }
        }

        while(!commandLine[0].equals("exit"))
        {
            String command;
            if ((command = br.readLine()) == null)
                return;
            else commandLine = command.split("\\s+");

            switch (commandLine[0]) {
                case "help" -> {
                    printable.help();
                    addCommandToLog(commandLine[0]);
                }
                case "info" -> {
                    printable.info(collection);
                    addCommandToLog(commandLine[0]);
                }
                case "show" -> {
                    printable.show(collection);
                    addCommandToLog(commandLine[0]);
                }
                case "clear" -> {
                    collection.clear();
                    addCommandToLog(commandLine[0]);
                }
                case "save" -> { if (collection.save(new File(this.fileName))) addCommandToLog(commandLine[0]); }
                case "history" -> {
                    this.history();
                    addCommandToLog(commandLine[0]);
                }
                case "insert" -> {
                    Product p = new Product();
                    String key = commandLine[1];
                    String name = commandLine[2];
                    p.setName(name);
                    if (commandFile.equals("") && collection.checkKeyExistence(key))
                        p = initializeProduct(br, p);
                    else p = initializeProductThroughScript(br, p);

                    if (collection.checkKeyExistence(key))
                    {
                        collection.insert(key, p);
                        System.out.println("The product was successful inserted!");
                    } else System.out.println("The product with such key is already existed!");
                    addCommandToLog(commandLine[0]);
                }
                case "update" -> {
                    Integer id = Integer.valueOf(commandLine[1]);
                    Product pUpd = new Product();
                    String name = commandLine[2];
                    pUpd.setName(name);
                    pUpd = initializeProduct(br, pUpd);
                    if (collection.updateId(id, pUpd))
                        addCommandToLog(commandLine[0]);
                }
                case "remove_key" -> {
                    if (collection.removeKey(commandLine[1]))
                        addCommandToLog(commandLine[0]);
                }
                case "replace_if_greater" -> {
                    String key = commandLine[1];
                    Product p = new Product();
                    String name = commandLine[2];
                    p.setName(name);
                    p = initializeProduct(br, p);
                    if (replaceIfGreater(collection, key, p))
                        addCommandToLog(commandLine[0]);
                }
                case "replace_if_lower" -> {
                    String key = commandLine[1];
                    Product p = new Product();
                    String name = commandLine[2];
                    p.setName(name);
                    p = initializeProduct(br, p);
                    if (replaceIfLower(collection, key, p))
                        addCommandToLog(commandLine[0]);
                }
                case "average_of_manufacture_cost" -> {
                    if (collection.getHashtable().size() > 0)
                        System.out.println(collection.averageOfManufactureCost());
                    else
                        System.out.println("The collection is empty! Please, add the elements!");
                    addCommandToLog(commandLine[0]);
                }
                case "max_by_manufacturer" -> {
                    if (collection.getHashtable().size() > 0) {
                        Product p_max = collection.maxByManufacturer();
                        printable.printProduct(p_max);
                    } else
                        System.out.println("The collection is empty! Please, add the elements!");
                    addCommandToLog(commandLine[0]);
                }
                case "count_less_than_manufacture_cost" -> {
                    if (collection.getHashtable().size() > 0) {
                        System.out.println(collection.countLessThanManufactureCost(Integer.valueOf(commandLine[1])));
                    } else
                        System.out.println("The collection is empty! Please, add the elements!");
                    addCommandToLog(commandLine[0]);
                }
                case "execute_script" -> {
                    if (recursion_count < 10)
                    {
                        addCommandToLog(commandLine[0]);
                        this.workspace(collection, recursion_count + 1, commandLine[1]);
                    }
                    else
                        System.out.println("The free trial of recursions is ended! For more recursions, please donate!");
                }
                case "exit" -> {}
                default -> System.out.println("There is no such command! Try to type 'help' for more information!");
            }
        }
    }

    @Override
    public void clear() {}

    @Override
    public boolean save(File file) { return false; }

    /**
     * Returns the command log containing the last 14 entered commands.
     * They could be written by user either in command prompt or in script file.
     */
    @Override
    public void history() { System.out.println(Arrays.toString(commandLog)); }

    /**
     * If there is a value to specified key in this collection, attempts to replace it, when the new value is greater.
     * @param collection the user-defined collection
     * @param key the specified key
     * @param p the replacing product
     * @return true if the product was successful replaced
     * @throws NoSuchElementException if there is no associated value
     */
    @Override
    public boolean replaceIfGreater(Collection collection, String key, Product p) throws NoSuchElementException
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

    /**
     * If there is a value to specified key in this collection, attempts to replace it, when the new value is lower.
     * @param collection the user-defined collection
     * @param key the specified key
     * @param p the replacing product
     * @return true if the product was successful replaced
     * @throws NoSuchElementException if there is no associated value
     */
    @Override
    public boolean replaceIfLower(Collection collection, String key, Product p) throws NoSuchElementException
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
