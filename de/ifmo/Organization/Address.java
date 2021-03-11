package de.ifmo.Organization;

public class Address
{
    private String zipCode;

    public Address(String zipCode) { setZipCode(zipCode); }

    public void setZipCode(String zipCode) throws NullPointerException
    {
        try {
            if (zipCode.equals(""))
                throw new NullPointerException("The zip code must have a value!");
            else this.zipCode = zipCode;
        } catch(NullPointerException e) { System.out.println(e.getMessage()); }
    }

    public String getZipCode() { return this.zipCode; }
}
