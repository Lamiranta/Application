package de.ifmo.Organization;

import java.security.InvalidParameterException;

public class Organization
{
    private int id;
    private String name;
    private int employeesCount;
    private OrganizationType type;
    private Address postalAddress;

    public Organization(String name, int employeesCount)
    {
        setName(name);
        setEmployeesCount(employeesCount);
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public int getEmployeesCount() { return this.employeesCount; }
    public OrganizationType getType() { return this.type; }
    public Address getPostalAddress() { return this.postalAddress; }

    public void setName(String name) throws NullPointerException
    {
        if (name.equals(""))
            throw new NullPointerException("The organisation must have a name!");
        else this.name = name;
    }

    public void setEmployeesCount(int employeesCount) throws InvalidParameterException
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
