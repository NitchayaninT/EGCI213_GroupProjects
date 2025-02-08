package Project1;
import java.io.*;
import java.util.*;
class Customer
{
    private String name;
    private int point;
    private boolean firsttime;
    public Customer(String name)
    {
        this.name = name;
    }
    public void setName(String n){name =n;}
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
    public void setMonths(int m){months = m;}
    public void setInterest(double i){interest = i;}
    public int getMonths(){return this.months;}
    public double getInterest(){return this.interest;}
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
    public void processOrder()
    {
        System.out.printf("%d. %6s(%6d pts)  %8s= %15sx%3d  %16s=%,14.2f  (+%6d pts next order)\n",this.id,customer.getname(),customer.getPoint(),"order",product.getName(),this.unit,"sub-total(1)",product.getPrice()*this.unit,(int)(product.getPrice()*this.unit)/500);
        double discount = 0;
        String pointDeduct = "";
        if(!this.customer.getFirsttime())
            {
                if(customer.getPoint()>=100)
                {
                    customer.setPoint(customer.getPoint()-100);
                    discount = product.getPrice()*this.unit*0.05;
                    pointDeduct = "(-   100 pts)";
                }else discount = 0;
            }else discount = 200;
        System.out.printf("%24s%8s= %14.2f%10s%16s=%,14.2f %s\n","","discount",discount,"","sub-total(2)",(product.getPrice()*this.unit)-discount,pointDeduct);
        double totalInterest = 0;
        String totalInterestString ="";
        if(installment.getMonths()>0)
        {
            totalInterest = product.getPrice()*this.unit*installment.getInterest()*0.01;
            String s = String.format("%14.2f", totalInterest);
            totalInterestString = "total interest ="+s;
        }
        double total = product.getPrice()*this.unit-discount+totalInterest;
        System.out.printf("%24s%d-month installments%12s%16s%16s%30s","",installment.getMonths(),"",(installment.getMonths()>0)?totalInterestString:"");
        System.out.printf("%24s%8s=%,14.2f%12s%8s=%,14.2f\n","","total",total,"","monthly total",total/installment.getMonths());
    }
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
                    //orders.add(new Order(id,new Customer(name),productMap.get(code),unit,installmentMap.get(month)));
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
                    if(months > 10 || months < 0) throw new InvalidInputException(cols[0].trim());

                    double interest = Double.parseDouble(cols[1].trim());
                    installmentMap.put(months,new Installment(months,interest));
                    count++;
                }
                catch(RuntimeException e)//includes all the child classes of RuntimeException
                {
                    System.out.println(e.toString());
                    System.out.println(line);
                    System.out.println();
                    count++;
                } catch (InvalidInputException e) {
                    throw new RuntimeException(e);
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
        main newprog = new main();
        newprog.readOrder();
    }
}
