package de.ifmo.Organization;

public class Address
{
    private String zipCode;

    public Address(String zipCode) { setZipCode(zipCode); }

    public void setZipCode(String zipCode) throws NullPointerException
    {
        if (zipCode.equals(""))
            throw new NullPointerException("The zip code must have a value!");
        else this.zipCode = zipCode;
    }

    public String getZipCode() { return this.zipCode; }
}
