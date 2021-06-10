package de.ifmo.Client;

import de.ifmo.Product.*;
import de.ifmo.Transmittable.Message;

import java.io.*;
import java.util.Arrays;

public class Reader
{
    private String scriptFile;

    public Reader() { this.scriptFile = ""; }

    public String getScriptFile() { return scriptFile; }
    public void setScriptFile(String scriptFile) { this.scriptFile = scriptFile; }

    public Message readCommand(String command) throws Exception
    {
        String[] splitting = command.split("\\s+");
        String method = splitting[0];
        String arg = splitting.length > 1 ? splitting[1] : null;

        switch (method)
        {
            case "replace_if_greater":
            case "replace_if_lower":
            case "insert": return new Message(command, arg, initializeProduct());
            case "update":
            {
                if (isAnInteger(arg)) return new Message(command, arg, initializeProduct());
                else throw new Exception("The id value must be an integer!");
            }
            case "count_less_than_manufacture_cost":
            {
                if (isAnInteger(arg)) return new Message(command, arg);
                else throw new Exception("The argument value must be an integer!");
            }
            case "remove_key": return new Message(command, arg);
            case "execute_script":
            {
                if (arg != null) return new Message(command, arg);
                else throw new Exception("The file name must be entered!");
            }
            default: return new Message(command);
        }
    }

    private boolean isAnInteger(String number)
    {
        if (number == null) return false;
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException nfe) { return false; }
    }

    public Product initializeProduct()
    {
        if (this.scriptFile.equals(""))
            return initializeProductThroughConsole();
        else
            return initializeProductThroughFile(this.scriptFile);
    }

    protected Product initializeProductThroughConsole()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Product p = new Product();
        String[] param;

        while(true) {
            try {
                System.out.println("Enter the product name:");
                p.setName(br.readLine());
                break;
            } catch(Exception e) { System.out.println(e.getMessage()); }
        }

        while(true) {
            try {
                System.out.print("Enter the coordinates. ");
                System.out.println("The value of x must be an integer, the value of y must be less than 262:");
                param = br.readLine().split("\\s+");
                Coordinates c = new Coordinates(Integer.parseInt(param[0]), Float.valueOf(param[1]));
                p.setCoordinates(c);
                break;
            } catch(Exception e) { System.out.println(e.getMessage()); }
        }

        while(true) {
            try {
                System.out.println("Enter the price:");
                p.setPrice(Float.parseFloat(br.readLine()));
                break;
            } catch(Exception e) { System.out.println(e.getMessage()); }
        }

        while(true) {
            try {
                System.out.println("Enter the manufacture cost. The value of manufacture cost must be an integer:");
                p.setManufactureCost(Integer.parseInt(br.readLine()));
                break;
            } catch(Exception e) { System.out.println(e.getMessage()); }
        }

        while(true) {
            try {
                System.out.print("Enter the unit of measure. The possible variants are: ");
                System.out.println(Arrays.toString(UnitOfMeasure.values()));
                p.setUnitOfMeasure(UnitOfMeasure.valueOf(br.readLine()));
                break;
            } catch(Exception e) { System.out.println(e.getMessage()); }
        }

        while(true) {
            try {
                System.out.println("Enter the organization name and number of employments:");
                param = br.readLine().split("\\s+");
                Organization org = new Organization(param[0], Integer.parseInt(param[1]));

                System.out.print("Enter the organization type. The possible variants are: ");
                System.out.println(Arrays.toString(OrganizationType.values()));
                org.setType(OrganizationType.valueOf(br.readLine()));

                System.out.println("Enter the postal address:");
                org.setPostalAddress(new Address(br.readLine()));

                p.setManufacturer(org);
                break;
            } catch (Exception e) { System.out.println(e.getMessage()); }
        }
        return p;
    }

    protected Product initializeProductThroughFile(String fileName)
    {
        Product p = new Product();
        String[] param;

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            p.setName(br.readLine());

            param = br.readLine().split("\\s+");
            Coordinates c = new Coordinates(Integer.parseInt(param[0]), Float.valueOf(param[1]));
            p.setCoordinates(c);

            p.setPrice(Float.parseFloat(br.readLine()));

            p.setManufactureCost(Integer.parseInt(br.readLine()));

            p.setUnitOfMeasure(UnitOfMeasure.valueOf(br.readLine()));

            param = br.readLine().split("\\s+");
            Organization org = new Organization(param[0], Integer.parseInt(param[1]));

            org.setType(OrganizationType.valueOf(br.readLine()));

            org.setPostalAddress(new Address(br.readLine()));

            p.setManufacturer(org);
        } catch (Exception e) { System.out.println(e.getMessage()); }

        return p;
    }
}