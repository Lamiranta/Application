package de.ifmo.Product;

import de.ifmo.Organization.Organization;
import de.ifmo.Organization.OrganizationType;

import java.time.LocalDate;

public class Product
{
    private Integer id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDate creationDate;
    private Float price;
    private Integer manufactureCost;
    private UnitOfMeasure unitOfMeasure;
    private Organization manufacturer;

    public Product()
    {
        this.creationDate = LocalDate.now();
        this.id = creationDate.hashCode();
    }

    public Integer getId() { return this.id; }
    public String getName() { return this.name; }
    public Coordinates getCoordinates() { return this.coordinates; }
    public java.time.LocalDate getCreationDate() { return this.creationDate; }
    public Float getPrice() { return this.price; }
    public Integer getManufactureCost() { return this.manufactureCost; }
    public UnitOfMeasure getUnitOfMeasure() { return this.unitOfMeasure; }
    public Organization getManufacturer() { return this.manufacturer; }

    public void setName(String name) throws NullPointerException
    {
        try {
            if (name.equals(""))
                throw new NullPointerException("The product must have a name!");
            else this.name = name;
        } catch(NullPointerException e) { System.out.println(e.getMessage()); }
    }

    public void setCoordinates(Coordinates coordinates) throws NullPointerException
    {
        try {
            if (coordinates == null)
                throw new NullPointerException("The product must have coordinates!");
            else this.coordinates = coordinates;
        } catch(NullPointerException e) { System.out.println(e.getMessage()); }
    }

    public void setPrice(Float price) throws NullPointerException
    {
        try {
            if (price == null)
                throw new NullPointerException("The product must have a price!");
            else this.price = price;
        } catch(NullPointerException e) { System.out.println(e.getMessage()); }
    }

    public void setManufactureCost(Integer manufactureCost) throws NullPointerException
    {
        try {
            if (manufactureCost == null)
                throw new NullPointerException("The product must have manufacture cost!");
            else this.manufactureCost = manufactureCost;
        } catch(NullPointerException e) { System.out.println(e.getMessage()); }
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) throws EnumConstantNotPresentException
    {
        try {
            if (unitOfMeasure == null) return;
            for (UnitOfMeasure t : UnitOfMeasure.values())
            {
                if (unitOfMeasure == t)
                {
                    this.unitOfMeasure = unitOfMeasure;
                    break;
                }
            }
            if (this.unitOfMeasure == null) throw new EnumConstantNotPresentException(unitOfMeasure.getClass(), unitOfMeasure.name());
        } catch(EnumConstantNotPresentException e) { System.out.println(e.getMessage()); }
    }

    public void setManufacturer(Organization manufacturer)
    {
        this.manufacturer = manufacturer;
    }
}
