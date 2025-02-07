package Project1;

import java.util.*;
import java.io.*;

public class main {
    public static void main(String []args)
    {
        System.out.println("hello world i am p");

        // Read file From product.txt
        File productFile = new File()
        Scanner productScan
    }
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