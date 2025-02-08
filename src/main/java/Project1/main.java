package Project1;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
class Customer
{
    private String name;
    private int point;
    private boolean firsttime;
    public Customer(String name)
    {
        this.name = name;
    }
    public void setname(String n){name =n;}
}
class Product{
    // Class variable
    protected String product_code;
    protected String name;
    protected int unit_price;
    protected static ArrayList <Integer> tol_sales_price = new ArrayList<>();
    protected static ArrayList <Integer> tol_sales_unit = new ArrayList<>();

    // Constructor
    public Product(){ super();}
    public Product(String pc, String name, int unit_price){
        this.product_code = pc;
        this.name = name;
        this.unit_price = unit_price;
    }

    // Methods
    public static void update_product(String pc, int units){

    }

}
class Installment
{
    private int months;
    private double interest;

    public void setmonths(int m){months = m;}
}
class Order
{
    private int id;
    private Customer customer;
    private Product product;
    private int unit;
    private Installment installment;
    public Order(){}
    public Order(int i, Customer customer, Product product,int unit, Installment installment)
    {
        this.id = i;
        this.customer = customer;
        this.product = product;
        this.unit = unit;
        this.installment = installment;
    }
    public int getID(){return id;}
    public Customer getCustomer(){return customer;}
    public Product getProduct(){return product;}
    public int getUnit(){return unit;}
    public Installment getInstall(){return installment;}

}
class InvalidInputException extends Exception {

public class main {
    public static void main(String []args)
    {
        System.out.println("hello world i am p");
    }

    // Read file From product.txt
    File productFile = new File()
    Scanner productScan
}
