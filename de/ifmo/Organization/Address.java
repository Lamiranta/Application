package de.ifmo.Organization;

public class Address
{
    /** Postal address of specific organization. */
    private String zipCode;

    /**
     * Constructs the object of this class and sets the specific postal address.
     * @param zipCode specific postal address.
     */
    public Address(String zipCode) { setZipCode(zipCode); }

    public String getZipCode() { return this.zipCode; }

    protected void setZipCode(String zipCode) throws NullPointerException
    {
        if (zipCode.equals(""))
            throw new NullPointerException("The zip code must have a value!");
        else this.zipCode = zipCode;
    }
}
