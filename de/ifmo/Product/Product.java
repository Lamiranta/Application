package de.ifmo.Product;

import de.ifmo.Organization.Organization;

import java.time.LocalDate;

/**
 * This class stores the information about specific product including the classes Coordinates and Organization.
 * Objects of this class are used as the elements of user collection. The changes user can do are made through access to
 * the specific Product-object, whenever it is sufficient to work with fields of Coordinates or Organization.
 */
public class Product
{
    /** Id of specific product. This field is unique and generated automatically. */
    private Integer id;
    /** Name of specific product. */
    private String name;
    /** Coordinates of specific product. This class contains x- and y-coordinate. */
    private Coordinates coordinates;
    /** Creation date of specific product. It is filled when the object was constructed automatically. */
    private java.time.LocalDate creationDate;
    /** Price of specific product. */
    private Float price;
    /** Manufacture cost of specific product. */
    private Integer manufactureCost;
    /** Unit of measure used for specific product. */
    private UnitOfMeasure unitOfMeasure;
    /** Organization that manufacturing specific product. */
    private Organization manufacturer;

    /**
     * Constructs a new object of this class and fills creationDate and id fields.
     */
    public Product()
    {
        this.creationDate = LocalDate.now();
        this.id = (int) Math.floor(1e9 * Math.random());
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
        if (name.equals(""))
            throw new NullPointerException("The product must have a name!");
        else this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) throws NullPointerException
    {
        if (coordinates == null)
            throw new NullPointerException("The product must have coordinates!");
        else this.coordinates = coordinates;
    }

    public void setPrice(Float price) throws NullPointerException
    {
        if (price == null)
            throw new NullPointerException("The product must have a price!");
        else this.price = price;
    }

    public void setManufactureCost(Integer manufactureCost) throws NullPointerException
    {
        if (manufactureCost == null)
            throw new NullPointerException("The product must have manufacture cost!");
        else this.manufactureCost = manufactureCost;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) throws EnumConstantNotPresentException
    {
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
    }

    public void setManufacturer(Organization manufacturer)
    {
        this.manufacturer = manufacturer;
    }
}
