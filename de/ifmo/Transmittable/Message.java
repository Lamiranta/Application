package de.ifmo.Transmittable;

import de.ifmo.Product.Product;
import java.io.Serializable;

public class Message implements Serializable
{
    private final String command;
    private final String key;
    private Product product;
    private String result;

    public Message(String command, String key, Product product)
    {
        this.command = command;
        this.key = key;
        this.product = product;
    }

    public Message(String command, String key) { this(command, key, null); }
    public Message(String command) { this(command, null, null); }

    public String getCommand() { return command; }
    public String getKey() { return key; }
    public Product getProduct() { return product; }
    public String getResult() { return result; }

    public void setProduct(Product product) { this.product = product; }
    public void setResult(String result) { this.result = result; }
}