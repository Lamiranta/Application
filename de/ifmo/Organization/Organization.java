package de.ifmo.Organization;

import java.security.InvalidParameterException;

/**
 * This class stores the information about specific organization. This information is used for description of product.
 */
public class Organization
{
    /** Id of specific organization. This field is unique and generated automatically. */
    private int id;
    /** Name of specific organization. */
    private String name;
    /** Number of employees working on specific organization. */
    private int employeesCount;
    /** Type of specific organization. */
    private OrganizationType type;
    /** Postal address of specific organization. */
    private Address postalAddress;

    /**
     * Constructs a new, empty object of this class and fills the name, employeesCount and id fields.
     * The fields name and employeesCount are defined by user in initial file or while running "insert"-command.
     * @param name name of specific organization
     * @param employeesCount number of employees of specific organization.
     */
    public Organization(String name, int employeesCount)
    {
        setName(name);
        setEmployeesCount(employeesCount);
        this.id = Math.abs(name.hashCode());
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public int getEmployeesCount() { return this.employeesCount; }
    public OrganizationType getType() { return this.type; }
    public Address getPostalAddress() { return this.postalAddress; }

    protected void setName(String name) throws NullPointerException
    {
        if (name.equals(""))
            throw new NullPointerException("The organisation must have a name!");
        else this.name = name;
    }

    protected void setEmployeesCount(int employeesCount) throws InvalidParameterException
    {
        if (employeesCount < 1)
            throw new InvalidParameterException("The number of employees must be a positive number!");
        else this.employeesCount = employeesCount;
    }

    public void setType(OrganizationType type) throws NullPointerException, EnumConstantNotPresentException
    {
        if (type == null)
            throw new NullPointerException("The organization type must have a value!");
        for (OrganizationType t : OrganizationType.values())
        {
            if (type == t)
            {
                this.type = type;
                break;
            }
        }
        if (this.type == null) throw new EnumConstantNotPresentException(type.getClass(), type.name());
    }

    public void setPostalAddress(Address postalAddress) throws NullPointerException
    {
        if (postalAddress == null)
            throw new NullPointerException("The organization must have an address!");
        else this.postalAddress = postalAddress;
    }
}
