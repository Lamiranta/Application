package de.ifmo.Organization;

import java.security.InvalidParameterException;

public class Organization
{
    private int id;
    private String name;
    private int employeesCount;
    private OrganizationType type;
    private Address postalAddress;

    public Organization() {}

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public int getEmployeesCount() { return this.employeesCount; }
    public OrganizationType getType() { return this.type; }
    public Address getPostalAddress() { return this.postalAddress; }

    public void setName(String name) throws NullPointerException
    {
        try {
            if (name.equals(""))
                throw new NullPointerException("The organisation must have a name!");
            else this.name = name;
        } catch(NullPointerException e) { System.out.println(e.getMessage()); }
    }

    public void setEmployeesCount(int employeesCount) throws InvalidParameterException
    {
        try {
            if (employeesCount < 1)
                throw new InvalidParameterException("The number of employees must be a positive number!");
            else this.employeesCount = employeesCount;
        } catch (InvalidParameterException e) { System.out.println(e.getMessage()); }
    }

    public void setType(OrganizationType type) throws NullPointerException, EnumConstantNotPresentException
    {
        try {
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
        } catch(Exception e) { System.out.println(e.getMessage()); }
    }

    public void setPostalAddress(Address postalAddress) throws NullPointerException
    {
        try {
            if (postalAddress == null)
                throw new NullPointerException("The organization must have an address!");
            else this.postalAddress = postalAddress;
        } catch(NullPointerException e) { System.out.println(e.getMessage()); }
    }
}