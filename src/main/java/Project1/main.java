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
class Product
{
    private String name;
    private String code;
    private double price;
    
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

    public InvalidInputException(String message) {
        super(message);
    }
}
public class main {
    Map<String, Product> productMap = new HashMap<>();
    Map<Integer,Installment> installmentMap = new HashMap<>();
    ArrayList<Order> orders = new ArrayList<>();
    public void readOrder()
    {
        String path = "src/main/Java/Project1/";
        String fileName  = "orders.txt";
        String inputFile = path+fileName;
        System.out.println("Read from "+inputFile);
        try
        {
            File infile = new File(inputFile);
            Scanner scan = new Scanner(infile);
             if (scan.hasNextLine()) {
                scan.nextLine();  // Skip the first line (header)
            }
            while(scan.hasNext())
            {
                String line = "";
                try
                {
                line = scan.nextLine();
                String []col = line.split(",");
                int id = Integer.parseInt(col[0].trim());
                String name = col[1].trim();
                
                String code = col[2].trim();
                if(productMap.get(code)==null)
                {
                    throw new InvalidInputException("For product: \"" + col[2].trim() + "\"");
                }
                
                int unit = Integer.parseInt(col[3].trim());
                if(unit<0)
                {
                    throw new InvalidInputException("For units: \"" + col[3].trim() + "\"");
                }
                int month = Integer.parseInt(col[4].trim());
                orders.add(new Order(id,new Customer(name),productMap.get(code),unit,installmentMap.get(month)));
                }catch(Exception e)
                {
                 System.out.println(e);
                 System.out.printf("%s --> skip line\n\n", line);
                }
                  
                
                
            }
             
        }catch(FileNotFoundException e)
        {
            System.out.print(e + " --> ");
            System.out.println("Enter correct file name = ");
            Scanner keyboard = new Scanner(System.in);
            fileName = keyboard.next();
        } 
    }
    public static void main(String []args)
    {
        
    }
}
