//Akkhrawin Nair 6580013
//Pakin Panawattanakul 6580043
//Nitchayanin Thamkunanon 6580081
//Pibhu Chitburanachart 6580195
//Panupong Sangaphunchai 6580587

package Project2_6580081;

//an individual tour
public class Tour implements Comparable<Tour>{
    private String name;
    private static int capacity;
    private int seat;
    private int total_customer;

    //constructor
    Tour(int c, String name){capacity=c; this.name=name; seat = 0;  total_customer = 0;}

    //setter
    synchronized public void setCapacity(int c){capacity = c;}
    public void setSeat(int s){seat = s;}
    public void setCustomer(int c){total_customer = c;}

    //getter
    public String getName(){return name;}
    public int getCapacity(){return capacity;}
    synchronized public int getSeat(){return seat;}
    public int getCustomer(){return total_customer;}

    //update methods
    synchronized public void updateSeat(int s){seat+=s;}
    synchronized public void updateCustomer(int customer){total_customer+=customer;}

    //compareTo
    public int compareTo(Tour other)
    {
        return Integer.compare(other.getCustomer(),this.getCustomer());
    }
}
