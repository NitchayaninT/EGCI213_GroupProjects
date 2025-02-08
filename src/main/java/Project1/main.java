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
class Product{
    // Class variable
    protected String product_code;
    protected String name;
    protected int unit_price;
    // static HashMap to store total_sales of each products
    protected static HashMap<String,Integer> tol_sales_price = new HashMap<>();
    protected static HashMap<String,Integer> tol_sales_unit = new HashMap<>();

    // Constructor
    public Product(){ super();} // default
    public Product(String pc, String name, int unit_price){
        this.product_code = pc;
        this.name = name;
        this.unit_price = unit_price;
        // If it is the first time that this product is mention create in the HashMap
        if(!tol_sales_price.containsKey(pc)){
            tol_sales_price.put(pc,0);
            tol_sales_unit.put(pc,0);
        }
    }

    // Methods
    // update the total_sales value
    public void record_sales(int unit){
        // Update the price by unit*unit_price
        tol_sales_price.put(this.product_code,tol_sales_price.get(product_code)+(unit*this.unit_price));
        tol_sales_unit.put(this.product_code,tol_sales_unit.get(product_code)+1);
    }

    //Setter Getter
    public int get_unit_price(){return this.unit_price;}
    public String get_name(){return this.name;}
    public String get_product_code(){return this.product_code;}
    // Use product code to get total_sales
    public int get_tol_sales_price(String pc){
        return tol_sales_price.get(pc);
    }
    public int get_tol_sales_unit(String pc){
        return tol_sales_unit.get(pc);
    }

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
        // this update the tol_sales
        this.product.record_sales(unit);
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
    public static void main(String[] args) {
        System.out.println("hello world i am p");
    }

    Map<String, Product> productMap = new HashMap<>();
    Map<Integer, Installment> installmentMap = new HashMap<>();
    ArrayList<Order> orders = new ArrayList<>();

    // function use to read file from products
    public void readProduct(){
        boolean done = false;
        String path = "src/main/java/Project1/";
        String file_name = "products.txt";
        do {
            String in_path = path + file_name;
            System.out.println();

            // Declare line here because using in catch
            String line;
            try {
                File infile = new File(in_path);
                Scanner scan = new Scanner(infile);

                // Skip the first line of the file
                scan.nextLine();

                // Scan until there is no nextline
                while (scan.hasNext()) {
                    line = scan.nextLine();
                    String[] col = line.split(",");
                    String product_code = col[0];
                    String name = col[1];
                    int unit_price = Integer.parseInt(col[2].trim());
                    // if unit_price negative throw exception
                    if (unit_price < 0) {
                        throw new InvalidInputException("For unit_price: \"" + unit_price + "\"");
                    }
                    // put in the Hashmap
                    productMap.put(product_code, new Product(product_code, name, unit_price));
                }
                done = true;
            } catch (InvalidInputException e) {
                System.out.println(e);
                System.out.printf("%s --> skip line\n\n", line);
            } catch (FileNotFoundException e) {
                System.err.println(e);
                System.out.println("Enter correct file name =");
                Scanner key_scan = new Scanner(System.in);
                file_name = key_scan.nextLine();
            }
        }while(!done);
    }

    public void readOrder() {
        String path = "src/main/Java/Project1/";
        String fileName = "orders.txt";
        String inputFile = path + fileName;
        System.out.println("Read from " + inputFile);
        try {
            File infile = new File(inputFile);
            Scanner scan = new Scanner(infile);
            if (scan.hasNextLine()) {
                scan.nextLine();  // Skip the first line (header)
            }
            while (scan.hasNext()) {
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
        } catch (FileNotFoundException e) {
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
        program.readOrder();
        System.out.println("test branch");
        //calculate sub-total (1) from product price and units purchased
        //calculate sub-total (2) from sub-total (1) - discount
    }
}
