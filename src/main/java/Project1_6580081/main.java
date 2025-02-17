//Akkhrawin Nair 6580013
//Pakin Panawattanakul 6580043
//Nitchayanin Thamkunanon 6580081
//Pibhu Chitburanachart 6580195
//Panupong Sangaphunchai 6580587

package Project1_6580081;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    Map<String, Customer> customerMap = new HashMap<>();
    //

    // function use to read file from products
    public void readProduct(){
        boolean done = false;
        String path = "src/main/java/Project1_6580081/";
        String file_name = "products.txt";

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
            System.out.printf("%-15s (%-2s) unit price = %6d\n", product.get_name(), product.get_product_code(), product.get_unit_price());
        }
    }

    public void readInstallments() {
        boolean done = false;
        String path = "src/main/java/Project1_6580081/";
        String fileName = "installments.txt";

        while (!done) {
            try {
                String inputFile = path + fileName;
                File inFile = new File(inputFile);
                Scanner fileScan = new Scanner(inFile);
                if(!Objects.equals(fileName, "installments.txt")) {
                    throw new WrongfileException("You Entered the wrong file!");
                }
                System.out.println("\nRead from " + inputFile);

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
        String path = "src/main/Java/Project1_6580081/";
        String fileName = "orders.txt";
        while (!done) {
            try {
                String inputFile = path + fileName;
                File infile = new File(inputFile);
                Scanner scan = new Scanner(infile);
                if(!Objects.equals(fileName, "orders.txt") & !Objects.equals(fileName, "orders_errors.txt")) {
                    throw new WrongfileException("You Entered the wrong file!");
                }
                System.out.println("\nRead from " + inputFile);
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
                        int month = Integer.parseInt(col[4].trim());

                        if (installmentMap.get(month) == (null)) {
                            throw new InvalidInputException("For installment plan: \"" + col[4].trim() + "\"");
                        }


                        // the Product code is valid and Unit is valid update the total sales
                        productMap.get(code).record_sales(unit);
                        // Add order to order ArrayList
                        Customer customer = customerMap.get(name);
                        if (customer == null) {
                            // New customer: apply 200 discount and mark as no longer first-time

                            customer = new Customer(name);
                            customer.setFirsttime(true);  // Set as first-time customer
                            customerMap.put(name, customer);  // Add to the customerMap
                        }
                        orders.add(new Order(id, customer, productMap.get(code), unit, installmentMap.get(month)));
                        System.out.printf("Order%3d >>%6s%16s x%3d%5d-month installments\n",id,name,productMap.get(code).get_name(),unit,month);
                        //WILL FIX THIS
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.printf("%-35s%11s skip this line\n\n", line,"-->");
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
        System.out.println("\n=== Order processing ===");
        //read from ArrayList of orders
        for(Order order : orders) {
            System.out.printf(
                    "\n%2d. %-7s(%6d pts)  %-12s= %-16sx%-2d  %-15s=%,14.2f  (+%6d pts next order)\n",
                    order.getID(),
                    order.getCustomer().getName(),
                    order.getCustomer().getPoints(),
                    "order",
                    order.getProduct().get_name(),
                    order.getUnit(),
                    "sub-total(1)",
                    (double)order.getProduct().get_unit_price() * order.getUnit(),
                    (int) (order.getProduct().get_unit_price() * order.getUnit()) / 500
            );
            double discount = 0;
            String pointDeduct = "";
            if (!order.getCustomer().getFirstTime()) {
                if (order.getCustomer().getPoints() >= 100) {
                    order.getCustomer().setPoints(order.getCustomer().getPoints() - 100 + (int) (order.getProduct().get_unit_price() * order.getUnit()/500));
                    discount = order.getProduct().get_unit_price() * order.getUnit() * 0.05;
                    pointDeduct = " (-   100 pts)";
                } else discount = 0;
            } else {
                discount = 200;
                order.getCustomer().setFirsttime(false);
                order.getCustomer().setPoints((int) (order.getProduct().get_unit_price() * order.getUnit() / 500));
            }
            System.out.printf(
                    "%24s %-12s= %,12.2f  %2s  %-15s  =%,14.2f %s\n",
                    "",
                    "discount",
                    discount,
                    "   ",
                    "  sub-total(2)",
                    (order.getProduct().get_unit_price() * order.getUnit()) - discount,
                    pointDeduct
            );
            double totalInterest = 0;
            String totalInterestString = "";
            if (order.getInstall().getMonths() > 0) {
                totalInterest = ((order.getProduct().get_unit_price() * order.getUnit()) - discount) * order.getInstall().getInterest() * order.getInstall().getMonths() * 0.01;
                String s = String.format("%,14.2f", totalInterest);
                totalInterestString = String.format("%-20s", "total interest = " + s);
            }
            double total = order.getProduct().get_unit_price() * order.getUnit() - discount + totalInterest;
            double monthlyTotal = total / order.getInstall().getMonths();

            if(order.getInstall().getMonths() == 0)
            {
                monthlyTotal = total; //if theres no discount, monthly total = total
                System.out.printf("%37s\n","Full Payment");
            }
            else System.out.printf(
                    "%24s %-2d %-20s %10s " + "%-15s=%,14.2f\n", "", order.getInstall().getMonths(), "- month installments", "", (order.getInstall().getMonths() > 0) ? "total interest" : "", totalInterest);

            System.out.printf(
                    "%24s %-12s=%,13.2f%9s%-15s=%,14.2f\n",
                    "",
                    "total",
                    total,
                    "",
                    "monthly total",
                    monthlyTotal
            );

        }
    }

    public void productSummary()
    {
        System.out.println("\n=== Product Summary ===");
        ArrayList<Product> productList = new ArrayList<>(productMap.values());
        Collections.sort(productList); //sort products by total unit sales
        for(Product p : productList)
        {
            Order luckyOrder = p.luckyDraw(orders);
            System.out.printf("%-16s%-13s%3d units  =  %,13.2f THB  lucky draw winner = %6s (order %2d)\n",p.get_name(),"total sales = ",p.get_tol_sales_unit(),(float)p.get_tol_sales_price(),luckyOrder.getCustomer().getName(),luckyOrder.getID());
        }

    }
    public void customerSummary(){
        System.out.println("\n=== Customer Summary ===");

        ArrayList<Customer> customerList = new ArrayList<>(customerMap.values());
        Collections.sort(customerList, new Comparator<Customer>() {
            public int compare(Customer c1, Customer c2) {
                if (c2.getPoints() != c1.getPoints()) {
                    return c2.getPoints() - c1.getPoints();
                } else {
                    return c1.getName().compareTo(c2.getName());
                }
            }
        });

        for (Customer c : customerList) {
            System.out.printf("%-7s %7s %,5d \n", c.getName(), "remaining points =", c.getPoints());
        }


    }

    public static void main(String []args)
    {
        main program = new main();
        program.readProduct();
        program.readInstallments();
        program.readOrder();
        program.processOrder();
        program.productSummary();
        program.customerSummary();

    }
}