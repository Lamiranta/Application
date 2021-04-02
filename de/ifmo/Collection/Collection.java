package de.ifmo.Collection;

import de.ifmo.Commands.ConsoleCommands;
import de.ifmo.Commands.ElementCommands;
import de.ifmo.Commands.ManufactureCommands;
import de.ifmo.Product.Product;

import java.io.*;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.NoSuchElementException;

public class Collection implements ConsoleCommands, ElementCommands, ManufactureCommands
{
    private Hashtable<Integer, Product> hashtable;
    private final java.time.LocalDate createDate;

    public Collection()
    {
        this.createDate = LocalDate.now();
        this.hashtable = new Hashtable<>();
    }

    public Hashtable<Integer, Product> getHashtable() { return this.hashtable; }
    public LocalDate getCreateDate() { return createDate; }

    @Override
    public void clear()
    {
        getHashtable().clear();
        System.out.println("The collection was cleared successful!");
    }

    @Override
    public boolean save(File file)
    {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for (Product p : getHashtable().values())
            {
                String temp = p.getId() + "," + p.getName() + "," + p.getCoordinates().getX() + "," + p.getCoordinates().getY() + ",";
                temp += p.getCreationDate() + "," + p.getPrice() + ",";
                temp += p.getManufactureCost() + "," + p.getUnitOfMeasure() + ",";
                temp += p.getManufacturer().getId() + "," + p.getManufacturer().getName() + "," + p.getManufacturer().getEmployeesCount() + ",";
                temp += p.getManufacturer().getType() + "," + p.getManufacturer().getPostalAddress() + "\n";
                byte[] translate = temp.getBytes();
                fileOutputStream.write(translate);
            }
            System.out.println("File was saved successful!");
            return true;
        } catch(IOException e) { System.out.println("Error while writing the file!"); }
        return false;
    }

    @Override
    public void history() {}

    @Override
    public void insert(Integer key, Product product) { getHashtable().put(key,product); }

    @Override
    public boolean updateId(Integer id, Product product)
    {
        boolean doesExist = false;
        for (Integer key : getHashtable().keySet())
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

    @Override
    public boolean removeKey(Integer key) throws NoSuchElementException
    {
        try {
            if (getHashtable().get(key) == null)
                throw new NoSuchElementException();
            else getHashtable().remove(key);
            return true;
        } catch (NoSuchElementException e) { System.out.println("There is no elements with such key!"); }
        return false;
    }

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
