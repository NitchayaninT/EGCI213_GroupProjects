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
        this.firsttime = true;
        this.point = 0;
    }
    public void setName(String n){name =n;}
    public void setPoints(int pt){point = pt;}//calculate from purchased order
    public boolean getFirstTime(){return this.firsttime;}
    public String getName(){return name;}
    public int getPoints(){return point;}
}
class Product{
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

class WrongfileException extends Exception{
    public WrongfileException(String message){super(message);}
}
public class main {
    Map<String, Product> productMap = new HashMap<>();
    Map<Integer, Installment> installmentMap = new HashMap<>();
    ArrayList<Order> orders = new ArrayList<>();

    // function use to read file from products
    public void readProduct(){
        boolean done = false;
        String path = "src/main/java/Project1/";
        String file_name = "product.txt";

        do {
            // Declare line here because using in catch
            String line = "";
            try {
                String in_path = path + file_name;
                File infile = new File(in_path);
                Scanner scan = new Scanner(infile);
                if(!Objects.equals(file_name, "products.txt")) {
                    throw new WrongfileException("You Entered the wrong file!\n");
                }
                System.out.println("Read form "+in_path);
                // Skip the first line of the file
                if(scan.hasNext()) {
                    scan.nextLine();
                }
                // Scan until there is no nextline
                while (scan.hasNext()) {
                    line = scan.nextLine();
                    String[] col = line.split(",");
                    String product_code = col[0].trim();
                    String name = col[1].trim();
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
            } catch (FileNotFoundException | WrongfileException e) {
                System.out.println();
                System.out.println(e);
                System.out.println("Enter correct file name =");
                Scanner key_scan = new Scanner(System.in);
                file_name = key_scan.nextLine();
            }

        }while(!done);
        for (Map.Entry<String, Product> entry : productMap.entrySet()) {
            Product product = entry.getValue();
            System.out.printf("%s (%s) unit price %d\n", product.get_name(), product.get_product_code(), product.get_unit_price());
        }
    }

    public void readInstallments() {
        boolean done = false;
        String path = "src/main/java/Project1/";
        String fileName = "installment.txt";

        while (!done) {
            try {
                String inputFile = path + fileName;
                File inFile = new File(inputFile);
                Scanner fileScan = new Scanner(inFile);
                if(!Objects.equals(fileName, "installments.txt")) {
                    throw new WrongfileException("You Entered the wrong file!");
                }
                System.out.println("Read from " + inputFile);

                int count = 0;
                while (fileScan.hasNext()) {
                    String line = fileScan.nextLine();
                    if (count == 0) {
                        count++;
                        continue;
                    }
                    try {
                        String[] cols = line.split(",");
                        int months = Integer.parseInt(cols[0].trim());

                        double interest = Double.parseDouble(cols[1].trim());
                        installmentMap.put(months, new Installment(months, interest));
                        installmentMap.get(months).print();
                        count++;
                    } catch (RuntimeException e)//includes all the child classes of RuntimeException
                    {
                        System.out.println(e.toString());
                        System.out.printf("%s --> skip line\n\n", line);
                        count++;
                    }
                } done = true;

            } catch (FileNotFoundException | WrongfileException e) {
                System.out.println();
                System.out.println(e);
                System.out.println("Enter correct file name = ");
                Scanner keyboard = new Scanner(System.in);
                fileName = keyboard.nextLine();
            }
        }
    }
    public void readOrder() {
        boolean done = false;
        String path = "src/main/Java/Project1/";
        String fileName = "orders_error.txt";
        while (!done) {
            try {
                String inputFile = path + fileName;
                File infile = new File(inputFile);
                Scanner scan = new Scanner(infile);
                if(!Objects.equals(fileName, "orders.txt") & !Objects.equals(fileName, "orders_errors.txt")) {
                    throw new WrongfileException("You Entered the wrong file!");
                }
                System.out.println("Read from " + inputFile);
                if (scan.hasNextLine()) {
                    scan.nextLine();  // Skip the first line (header)
                }
                while (scan.hasNext()) {
                    String line = "";
                    try {
                        line = scan.nextLine();
                        String[] col = line.split(",");

                        int id = Integer.parseInt(col[0].trim());
                        String name = col[1].trim();
                        String code = col[2].trim();
                        if (productMap.get(code) == null) {
                            throw new InvalidInputException("For product: \"" + col[2].trim() + "\"");
                        }

                        int unit = Integer.parseInt(col[3].trim());

                        if (unit < 0) {
                            throw new InvalidInputException("For units: \"" + col[3].trim() + "\"");
                        }
                        else{
                            // the Product code is valid and Unit is valid update the total sales
                            productMap.get(code).record_sales(unit);
                        }
                        int month = Integer.parseInt(col[4].trim());

                        if (installmentMap.get(month) == (null)) {
                            throw new InvalidInputException("For installment plan: \"" + col[4].trim() + "\"");
                        }

                        orders.add(new Order(id, new Customer(name), productMap.get(code), unit, installmentMap.get(month)));
                        //WILL FIX THIS
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.printf("%s --> skip line\n\n", line);
                    }
                }
                done = true;
            } catch (FileNotFoundException | WrongfileException e) {
                System.out.println();
                System.out.println(e);
                System.out.println("Enter correct file name = ");
                Scanner keyboard = new Scanner(System.in);
                fileName = keyboard.nextLine();
            }
        }
    }

    public void processOrder()
    {
        //read from ArrayList of orders
        for(Order order : orders) {
            System.out.printf("%d. %6s(%6d pts)  %8s= %15sx%3d  %16s=%,14.2f  (+%6d pts next order)\n", order.getID(), order.getCustomer().getName(), order.getCustomer().getPoints(), "order", order.getProduct().get_name(), order.getUnit(), "sub-total(1)", (double)order.getProduct().get_unit_price() * order.getUnit(), (int) (order.getProduct().get_unit_price() * order.getUnit()) / 500);
            double discount = 0;
            String pointDeduct = "";
            if (!order.getCustomer().getFirstTime()) {
                if (order.getCustomer().getPoints() >= 100) {
                    order.getCustomer().setPoints(order.getCustomer().getPoints() - 100);
                    discount = order.getProduct().get_unit_price() * order.getUnit() * 0.05;
                    pointDeduct = "(-   100 pts)";
                } else discount = 0;
            } else discount = 200;
            System.out.printf("%24s%8s= %14.2f%10s%16s=%,14.2f %s\n", "", "discount", discount, "", "sub-total(2)", (order.getProduct().get_unit_price() * order.getUnit()) - discount, pointDeduct);
            double totalInterest = 0;
            String totalInterestString = "";
            if (order.getInstall().getMonths() > 0) {
                totalInterest = ((order.getProduct().get_unit_price() * order.getUnit()) - discount) * order.getInstall().getInterest() * order.getInstall().getMonths() * 0.01;
                String s = String.format("%14.2f", totalInterest);
                totalInterestString = "total interest =" + s;
            }
            double total = order.getProduct().get_unit_price() * order.getUnit() - discount + totalInterest;
            System.out.printf("%24s%d-month installments%12s%16s", "", order.getInstall().getMonths(), "", (order.getInstall().getMonths() > 0) ? totalInterestString : "");
            System.out.printf("%24s%8s=%,14.2f%12s%8s=%,14.2f\n", "", "total", total, "", "monthly total", total / order.getInstall().getMonths());

        }
    }

    public static void main(String []args)
    {
        main program = new main();
        program.readProduct();
        program.readInstallments();
        program.readOrder();
        program.processOrder();
        //calculate sub-total (1) from product price and units purchased
        //calculate sub-total (2) from sub-total (1) - discount
    }
}
