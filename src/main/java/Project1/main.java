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
    public void setPoints(int pt){point = pt;}//calculate from purchased order
    public String getName(){return name;}
    public int getPoints(){return point;}
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

    public Installment(int month, double interest){this.months = month; this.interest = interest;}
    public void setmonths(int m){months = m;}
    public void setInterest(double i){interest = i;}
    public int getMonths(){return this.months;}
    public double getInterest(){return this.interest;}

    public void print(){System.out.printf("%2d-month plan %4s monthly interest = %.2f%% \n",this.months," ",this.interest);}
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
        String path = "src/main/java/Project1/";
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
    public void readInstallments()
    {
        String path = "src/main/java/Project1/";
        String fileName  = "installments.txt";
        String inputFile = path+fileName;
        System.out.println("Read from "+inputFile);
        try{
            File inFile = new File(inputFile);
            Scanner fileScan = new Scanner(inFile);

            int count = 0;
            while(fileScan.hasNext())
            {
                String line = fileScan.nextLine();
                if(count == 0)
                {
                    count++;
                    continue;
                }
                try {
                    String[] cols = line.split(",");
                    int months = Integer.parseInt(cols[0].trim());

                    double interest = Double.parseDouble(cols[1].trim());
                    installmentMap.put(months,new Installment(months,interest));
                    installmentMap.get(months).print();
                    count++;
                }
                catch(RuntimeException e)//includes all the child classes of RuntimeException
                {
                    System.out.println(e.toString());
                    System.out.printf("%s --> skip line\n\n", line);
                    count++;
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
        main program = new main();
        program.readInstallments();
        //calculate sub-total (1) from product price and units purchased
        //calculate sub-total (2) from sub-total (1) - discount

    }
}
