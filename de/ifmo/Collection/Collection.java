package de.ifmo.Collection;

import de.ifmo.Interface.ElementCommands;
import de.ifmo.Interface.ManufactureCommands;
import de.ifmo.Interface.ReplaceCommands;
import de.ifmo.Product.Product;

import java.time.LocalDate;
import java.util.*;

/**
 * This class is used as collection of elements defined by users through file or command line.
 * Also there are methods which are used for working with collection in real-time.
 * As a storage of user-defined elements is hashtable used.
 */
public class Collection implements ElementCommands, ManufactureCommands, ReplaceCommands
{
    /**
     * The main storage of elements of specific collection. Each specific key is mapped to specific product defined by
     * user through initial file, script file or "insert"-command in command prompt.
     * The keys are contained in string format that allows to user to define specific comfortable key to each product.
     */
    private Hashtable<String, Product> hashtable;
    /** Creation date of the collection. It is filled when the collection was constructed automatically. */
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
        return p != null;
    }

    /**
     * Clears the hashtable of this collection.
     */
    public String clear()
    {
        getHashtable().clear();
        return "The collection was cleared successful!";
    }

    /**
     * If the value of specified key is not contained, attempts to insert to this collection a new user-defined product.
     * @param key value of specified key
     * @param product user-defined product
     */
    @Override
    public String insert(String key, Product product)
    {
        if (!checkKeyExistence(key))
        {
            getHashtable().put(key, product);
            return "The product was successful inserted!";
        }
        else return "There exists an element with such key!";
    }

    /**
     * If there is a product with specified id, attempts to update its value.
     * @param id value of specified id
     * @param product updating product
     * @return true if the product was successful updated
     */
    @Override
    public String updateId(Integer id, Product product)
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
            return "The element was successful updated!";
        }
        else return "There is no elements with such id!";
    }

    /**
     * Removes the element from the collection by key only if it is mapped to specified value.
     * @param key key with which specified value is associated
     * @return true if the element was successful removed
     * @throws NoSuchElementException if there is no element associated with this key
     */
    @Override
    public String removeKey(String key) throws NoSuchElementException
    {
        try {
            if (getHashtable().get(key) == null)
                throw new NoSuchElementException();
            else getHashtable().remove(key);
            return "The element was successful removed!";
        } catch (NoSuchElementException e) { e.printStackTrace(); }
        return "There is no elements with such key!";
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

    /**
     * If there is a value to specified key in this collection, attempts to replace it, when the new value is greater.
     * @param key the specified key
     * @param p the replacing product
     * @return true if the product was successful replaced
     * @throws NoSuchElementException if there is no associated value
     */
    @Override
    public String replaceIfGreater(String key, Product p) throws NoSuchElementException
    {
        try {
            Product target = getHashtable().get(key);
            if (target == null) throw new NoSuchElementException("There is no elements with such key!");
            else if (target.getPrice() < p.getPrice())
            {
                getHashtable().replace(key, target, p);
                return "The element was replaced successfully!";
            }
            else
                return "The current element has greater value!";
        } catch (NoSuchElementException e) { return e.getMessage(); }
    }

    /**
     * If there is a value to specified key in this collection, attempts to replace it, when the new value is lower.
     * @param key the specified key
     * @param p the replacing product
     * @return true if the product was successful replaced
     * @throws NoSuchElementException if there is no associated value
     */
    @Override
    public String replaceIfLower(String key, Product p) throws NoSuchElementException
    {
        try {
            Product target = getHashtable().get(key);
            if (target == null) throw new NoSuchElementException("There is no elements with such key!");
            else if (target.getPrice() > p.getPrice())
            {
                getHashtable().replace(key, target, p);
                return "The element was replaced successfully!";
            }
            else
                return "The current element has greater value!";
        } catch (NoSuchElementException e) { return e.getMessage(); }
    }
}