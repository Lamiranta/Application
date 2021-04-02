package de.ifmo.Collection;

import de.ifmo.Commands.InfoCommands;
import de.ifmo.Product.Product;

public class Printable implements InfoCommands
{
    public Printable() {}

    public void printProduct(Product p)
    {
        System.out.println("Product ID: " + p.getId().toString());
        System.out.println("Name: " + p.getName());
        System.out.println("Creation date: " + p.getCreationDate().toString());
        System.out.println("Price: " + p.getPrice().toString());
        System.out.println("Coordinates: " + p.getCoordinates().getX() + " " + p.getCoordinates().getY().toString());
        System.out.println("Manufacturer: " + p.getManufacturer().getName());
        System.out.println("Manufacture cost: " + p.getManufactureCost().toString());
        System.out.println("Unit of measure: " + p.getUnitOfMeasure().toString());
    }

    @Override
    public void help()
    {
        System.out.println("help - show the information about available commands");
        System.out.println("info - show the information about collection (type, data initialization etc.)");
        System.out.println("show - show all elements of collection through string representation");
        System.out.println("insert null {element} - add new element with given key");
        System.out.println("update id {element} - update value of an element with given id");
        System.out.println("remove_key null - remove an element with given key from collection");
        System.out.println("clear - clear the collection");
        System.out.println("save - save current collection to the file");
        System.out.println("execute_script file_name - compile and execute script from given file");
        System.out.println("exit - exit the program (without saving)");
        System.out.println("history - show the recent 14 commands (without arguments)");
        System.out.println("replace_if_greater null {element} - replace value by the key, if new value is greater than old one");
        System.out.println("replace_if_lower null {element} - replace value by the key, if new value is lower than old one");
        System.out.println("average_of_manufacture_cost - show an average value of manufactureCost field for all elements in the collection");
        System.out.println("max_by_manufacturer - show an element with max value of manufacturer field");
        System.out.println("count_less_than_manufacture_cost manufactureCost - show number of elements with lower value of manufactureCost field than for the given");
    }

    @Override
    public void info(Collection collection)
    {
        System.out.println("Collection type: Hashtable<String, Product>");
        System.out.println("Create date: " + collection.getCreateDate().toString());
        System.out.println("Collection size: " + collection.getHashtable().size());
    }

    @Override
    public void show(Collection collection)
    {
        System.out.println(collection.getHashtable().values().toString());
    }
}
