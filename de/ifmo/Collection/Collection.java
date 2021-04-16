package de.ifmo.Collection;

import de.ifmo.Commands.ConsoleCommands;
import de.ifmo.Commands.ElementCommands;
import de.ifmo.Commands.ManufactureCommands;
import de.ifmo.Product.Product;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * This class is used as collection of elements defined by users through file or command line.
 * Also there are methods which are used for working with collection in real-time.
 * As a storage of user-defined elements is hashtable used.
 */
public class Collection implements ConsoleCommands, ElementCommands, ManufactureCommands
{
    private Hashtable<String, Product> hashtable;
    private final java.time.LocalDate createDate;

    /**
     * Constructs a new collection with empty hashtable and creation date.
     */
    public Collection()
    {
        this.createDate = LocalDate.now();
        this.hashtable = new Hashtable<>();
    }

    public Hashtable<String, Product> getHashtable() { return this.hashtable; }
    public LocalDate getCreateDate() { return createDate; }

    /**
     * Returns a sorted list of keys form this collection.
     * @return sorted list of keys
     */
    public LinkedList<String> sortedHashtable()
    {
        LinkedList<String> keys = new LinkedList<>(getHashtable().keySet());
        keys.sort(Comparator.naturalOrder());
        return keys;
    }

    /**
     * Checks whenever there exists a value for a specified key.
     * @param key specified key
     * @return true if there exists an associated value
     */
    public boolean checkKeyExistence(String key)
    {
        Product p = getHashtable().get(key);
        return p == null;
    }

    /**
     * Clears the hashtable of this collection.
     */
    @Override
    public void clear()
    {
        getHashtable().clear();
        System.out.println("The collection was cleared successful!");
    }

    /**
     * Saves this collection to the chosen file.
     *
     * @param file user-defined file
     * @return true if saving was successful done
     */
    @Override
    public boolean save(File file)
    {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for (Product p : getHashtable().values())
            {
                String temp = p.getName() + "," + p.getCoordinates().getX() + "," + p.getCoordinates().getY() + ",";
                temp += p.getPrice() + "," + p.getManufactureCost() + "," + p.getUnitOfMeasure() + ",";
                temp += p.getManufacturer().getName() + "," + p.getManufacturer().getEmployeesCount() + ",";
                temp += p.getManufacturer().getType() + "," + p.getManufacturer().getPostalAddress().getZipCode() + "\n";
                byte[] translate = temp.getBytes();
                fileOutputStream.write(translate);
            }
            System.out.println("File was saved successful!");
            return true;
        } catch (IOException e) { System.out.println("Error while writing the file!"); }
        return false;
    }

    @Override
    public void history() {}

    /**
     * If the value of specified key is not contained, attempts to insert to this collection a new user-defined product.
     * @param key value of specified key
     * @param product user-defined product
     */
    @Override
    public void insert(String key, Product product) { getHashtable().put(key,product); }

    /**
     * If there is a product with specified id, attempts to update its value.
     * @param id value of specified id
     * @param product updating product
     * @return true if the product was successful updated
     */
    @Override
    public boolean updateId(Integer id, Product product)
    {
        boolean doesExist = false;
        for (String key : getHashtable().keySet())
        {
            if (getHashtable().get(key).getId().equals(id))
            {
                doesExist = true;
                getHashtable().replace(key,getHashtable().get(key),product);
            }
        }
        if (doesExist)
        {
            System.out.println("The element was successful updated!");
            return true;
        }
        else System.out.println("There is no elements with such id!");
        return false;
    }

    /**
     * Removes the element from the collection by key only if it is mapped to specified value.
     * @param key key with which specified value is associated
     * @return true if the element was successful removed
     * @throws NoSuchElementException if there is no element associated with this key
     */
    @Override
    public boolean removeKey(String key) throws NoSuchElementException
    {
        try {
            if (getHashtable().get(key) == null)
                throw new NoSuchElementException();
            else getHashtable().remove(key);
            System.out.println("The element was successful removed!");
            return true;
        } catch (NoSuchElementException e) { System.out.println("There is no elements with such key!"); }
        return false;
    }

    /**
     * Returns the average value of field ManufactureCost for all products in this collection.
     * @return the average value of field ManufactureCost
     */
    @Override
    public Integer averageOfManufactureCost()
    {
        Integer avg = 0;
        for (Product p : getHashtable().values())
        {
            avg += p.getManufactureCost();
        }
        return avg / getHashtable().size();
    }

    /**
     * Returns the product with max value of employees in associated organisation. If there is more than one, returns any of them.
     * @return the product with max value of employees
     */
    @Override
    public Product maxByManufacturer()
    {
        Product p_max = null;
        for (Product p : getHashtable().values())
        {
            if (p_max == null || p_max.getManufacturer().getEmployeesCount() < p.getManufacturer().getEmployeesCount())
                p_max = p;
        }
        return p_max;
    }

    /**
     * Returns the number of products for which the field ManufactureCost is less than corresponding value.
     * @param manufactureCost corresponding value of ManufactureCost
     * @return the number of products appropriated with defined condition
     */
    @Override
    public Integer countLessThanManufactureCost(Integer manufactureCost)
    {
        Integer count = 0;
        for (Product p : getHashtable().values())
        {
            if (p.getManufactureCost() < manufactureCost)
                count++;
        }
        return count;
    }
}