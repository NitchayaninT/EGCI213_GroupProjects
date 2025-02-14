//Akkhrawin Nair 6580013
//Pakin Panawattanakul 6580043
//Nitchayanin Thamkunanon 6580081
//Pibhu Chitburanachart 6580195
//Panupong Sangaphunchai 6580587

package Project1_6580081;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

class Product implements Comparable<Product>{
    // Class variable
    protected String product_code;
    protected String name;
    protected int unit_price;
    // static HashMap to store total_sales of each products
    int tol_sales_price;
    int tol_sales_unit;

    // Constructor
    public Product(){ super();} // default
    public Product(String pc, String name, int unit_price){
        this.product_code = pc;
        this.name = name;
        this.unit_price = unit_price;
        // If it is the first time that this product is mention create in the HashMap
        tol_sales_price = 0;
        tol_sales_unit = 0;
    }

    // Methods
    // update the total_sales value
    public void record_sales(int unit){
        // Update the price by unit*unit_price
        this.tol_sales_price += this.unit_price * unit;
        this.tol_sales_unit += unit;
    }

    //Setter Getter
    public int get_unit_price(){return this.unit_price;}
    public String get_name(){return this.name;}
    public String get_product_code(){return this.product_code;}

    // Get total sales for product summary
    public int get_tol_sales_price(){
        return tol_sales_price;
    }
    public int get_tol_sales_unit() {
        return tol_sales_unit;
    }

    /// lucky draw per product
    public Order luckyDraw(ArrayList<Order> orders)
    {
        //involves order class and customer class
        ArrayList<Order> luckyDraw = new ArrayList<>();
        for(Order order : orders)
        {
            if(Objects.equals(order.getProduct().get_name(), name))
            {
                luckyDraw.add(order);
            }
        }
        //Random
        Random R = new Random();
        int randomIndex = R.nextInt(luckyDraw.size());
        return luckyDraw.get(randomIndex);
    }

    @Override
    public int compareTo(Product p) {
        return Integer.compare(p.tol_sales_unit,this.get_tol_sales_unit());
    }
}