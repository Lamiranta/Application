package de.ifmo.Server;

import de.ifmo.Transmittable.Message;
import de.ifmo.Collection.Collection;
import de.ifmo.Collection.Printable;
import de.ifmo.Product.*;

import java.io.*;
import java.util.Arrays;

/**
 * The class of commander is used as a main class for work with user-defined collections.
 * The commander contains the methods that allow to user to change the structure of collection and parameters inside its values.
 * While working with collection user can through commander saves the changes to the file that was chosen
 * at the beginning of the work session.
 */
public class Commander
{
    private Collection set;
    /** Command log contained last 14 commands entered by user. */
    private String[] commandLog;
    /** Initial file name defined by user. */
    private final String fileName;

    /**
     * Constructs a commander and gets access to the file with initial collection.
     * @param fileName commander-used file
     */
    public Commander(String fileName)
    {
        this.commandLog = new String[0];
        this.fileName = fileName;
        try {
            this.set = fillCollection();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public Collection getSet() { return set; }

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

    public String executeCommand(Message msg)
    {
        String[] splitting = msg.getCommand().split("\\s+");
        String method = splitting[0];

        addCommandToLog(method);
        switch (method)
        {
            case "help": return new Printable().help();
            case "info": return new Printable().info(getSet());
            case "show": return new Printable().show(getSet());
            case "insert": return getSet().insert(msg.getKey(), msg.getProduct());
            case "update": return getSet().updateId(Integer.valueOf(msg.getKey()), msg.getProduct());
            case "remove_key": return getSet().removeKey(msg.getKey());
            case "clear": return getSet().clear();
            case "exit": return "Closing the session...";
            case "execute_script": return "Executing commands from " + msg.getKey();
            case "history": return history();
            case "replace_if_greater": return getSet().replaceIfGreater(msg.getKey(), msg.getProduct());
            case "replace_if_lower": return getSet().replaceIfLower(msg.getKey(), msg.getProduct());
            case "average_of_manufacture_cost": return getSet().averageOfManufactureCost().toString();
            case "max_by_manufacturer": return new Printable().printProduct(getSet().maxByManufacturer());
            case "count_less_than_manufacture_cost":
                return getSet().countLessThanManufactureCost(Integer.valueOf(msg.getKey())).toString();
            default: return "Invalid name of command! Try to type 'help' for more info!";
        }
    }

    protected Collection fillCollection() throws IOException
    {
        InputStreamReader input = new InputStreamReader(new FileInputStream(new File(fileName)));
        StringBuilder temp;
        Collection set = new Collection();
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

            switch (count)
            {
                case 1:
                    p.setName(temp.toString());
                    break;
                case 2:
                case 7:
                    helpingTemp = temp.toString();
                    break;
                case 3:
                    p.setCoordinates(new Coordinates(Long.parseLong(helpingTemp), Float.parseFloat(temp.toString())));
                    break;
                case 4:
                    p.setPrice(Float.parseFloat(temp.toString()));
                    break;
                case 5:
                    p.setManufactureCost(Integer.parseInt(temp.toString()));
                    break;
                case 6:
                    p.setUnitOfMeasure(UnitOfMeasure.valueOf(temp.toString()));
                    break;
                case 8:
                    p.setManufacturer(new Organization(helpingTemp, Integer.parseInt(temp.toString())));
                    break;
                case 9:
                    p.getManufacturer().setType(OrganizationType.valueOf(temp.toString()));
                    break;
                case 10:
                    p.getManufacturer().setPostalAddress(new Address(temp.toString()));
                    break;
            }
            if (c == (int) '\n' || c == -1)
            {
                set.getHashtable().put("key_" + key_count, p);
                count = 0;
                key_count++;
            }
        } while (c != -1);
        return set;
    }

    /**
     * Saves this collection to the chosen file.
     *
     * @return true if saving was successful done
     */
    public String save()
    {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName));
            for (Product p : set.getHashtable().values())
            {
                String temp = p.getName() + "," + p.getCoordinates().getX() + "," + p.getCoordinates().getY() + ",";
                temp += p.getPrice() + "," + p.getManufactureCost() + "," + p.getUnitOfMeasure() + ",";
                temp += p.getManufacturer().getName() + "," + p.getManufacturer().getEmployeesCount() + ",";
                temp += p.getManufacturer().getType() + "," + p.getManufacturer().getPostalAddress().getZipCode() + "\n";
                byte[] translate = temp.getBytes();
                fileOutputStream.write(translate);
            }
            return "File was saved successful!";
        } catch (IOException e) { e.printStackTrace(); }
        return "Error while writing the file!";
    }

    /**
     * Returns the command log containing the last 14 entered commands.
     * They could be written by user either in command prompt or in script file.
     *
     * @return string containing name of last 14 commands
     */
    public String history() { return Arrays.toString(commandLog); }
}
