package de.ifmo.Collection;

import de.ifmo.Interface.InfoCommands;
import de.ifmo.Product.Product;

/**
 * This class is used as storage for methods returning the specified information of collection as a text in console.
 */
public class Printable implements InfoCommands
{
    /**
     * Constructs a new object of class Printable
     */
    public Printable() {}

    /**
     * Prints the specified product from collection.
     * @param p the specified product
     */
    public String printProduct(Product p)
    {
        return "Product ID: " + p.getId().toString() + '\n' +
        "Name: " + p.getName() + '\n' +
        "Creation date: " + p.getCreationDate().toString() + '\n' +
        "Price: " + p.getPrice().toString() + '\n' +
        "Coordinates: " + p.getCoordinates().getX() + " " + p.getCoordinates().getY().toString() + '\n' +
        "Manufacturer: " + p.getManufacturer().getId() + " " + p.getManufacturer().getName() + '\n' +
        "Manufacture cost: " + p.getManufactureCost().toString() + '\n' +
        "Unit of measure: " + p.getUnitOfMeasure().toString() + '\n';
    }

    /**
     * Prints the information about available commands.
     */
    @Override
    public String help()
    {
        return "help - show the information about available commands\n" +
        "info - show the information about collection (type, data initialization etc.)\n" +
        "show - show all elements of collection through string representation\n" +
        "insert null {element} - add new element with given key\n" +
        "update id {element} - update value of an element with given id\n" +
        "remove_key null - remove an element with given key from collection\n" +
        "clear - clear the collection\n" +
        "save - save current collection to the file\n" +
        "execute_script file_name - compile and execute script from given file\n" +
        "exit - exit the program (without saving)\n" +
        "history - show the recent 14 commands (without arguments)\n" +
        "replace_if_greater null {element} - replace value by the key, if new value is greater than old one\n" +
        "replace_if_lower null {element} - replace value by the key, if new value is lower than old one\n" +
        "average_of_manufacture_cost - show an average value of manufactureCost field for all elements in the collection\n" +
        "max_by_manufacturer - show an element with max value of manufacturer field\n" +
        "count_less_than_manufacture_cost manufactureCost - show number of elements with lower value of manufactureCost field than for the given\n";
    }

    /**
     * Prints the information about specified collection.
     * @param collection the specified collection
     */
    @Override
    public String info(Collection collection)
    {
        return "Collection type: Hashtable<String, Product>\n" +
        "Creation date: " + collection.getCreateDate().toString() + '\n' +
        "Collection size: " + collection.getHashtable().size() + '\n';
    }

    /**
     * Prints all elements containing in the collection in format "key -> id, name".
     * @param collection the specified collection
     */
    @Override
    public String show(Collection collection)
    {
        if (collection.getHashtable().size() == 0) return "Collection is empty!";
        else
        {
            StringBuilder str = new StringBuilder("key_value -> Product_id, Product_name\n");
            for (String key : collection.sortedHashtable())
            {
                str.append(key).append(" -> ").append(collection.getHashtable().get(key).getId()).append(", ").append(collection.getHashtable().get(key).getName()).append('\n');
            }
            return str.toString();
        }
    }
}
