//Akkhrawin Nair 6580013
//Pakin Panawattanakul 6580043
//Nitchayanin Thamkunanon 6580081
//Pibhu Chitburanachart 6580195
//Panupong Sangaphunchai 6580587

package Project2_6580081;

import java.util.*;
import java.util.concurrent.CyclicBarrier;

//individual travel agency
//each iteration of a loop = 1 day
public class AgencyThread extends Thread{
    private String thread_name;
    private int remaining_cus;
    public static int max_arrival;
    private CyclicBarrier barrier;
    private static ArrayList<Tour> tours;

    //constructor
    AgencyThread(String name, int max){super(name); thread_name=name; max_arrival=max;}

    //setters
    public void setBarrier(CyclicBarrier ba) { barrier = ba;}
    public void setTour(ArrayList<Tour>t){tours = t;}

    //run method for each agency thread
    public void run()
    {
        while(Project2_main.days_simulation_loop>0)
        {
            //receive customers and update remaining customers(from prev days + today)
            Random rand = new Random();
            int arriving_cus = rand.nextInt(max_arrival);
            remaining_cus+=arriving_cus;

            //wait for main to print day
            try {
                barrier.await();
            } catch (Exception e) {}
            
            //printing new arrival and remaining customers
            printNewArrival(arriving_cus);
            try {
                barrier.await(); //threads waiting at the barrier before printing next thing
            } catch (Exception e) {}
            
            //send customers to tour
            CustomerToTour(rand);

            //wait for all agency thread to print Customer to Tour
            try {
                int ArrivingThreads = barrier.await();
                if(ArrivingThreads==0) System.out.printf("%17s"+" >> \n",Thread.currentThread().getName());
            } catch (Exception e) {}
            //wait for all operator threads to send customers to places
            try {
                barrier.await(); //
            } catch (Exception e) {}

        }
    }
    private void printNewArrival(int arriving)
    {
        System.out.printf("%17s" + " >> new arrival = %-2d%19s remaining customers = %-2d\n",Thread.currentThread().getName(),arriving,"",remaining_cus);
    }
    private void CustomerToTour(Random rand)
    {
        Tour randomTour = tours.get(rand.nextInt(tours.size()));
        synchronized(randomTour)
        {
            int seat_available = randomTour.getCapacity()-randomTour.getSeat();
            int send_customers = 0;
            if(seat_available>=remaining_cus)//if seat >= customer, can send all customer
            {
               send_customers = remaining_cus;
               randomTour.updateSeat(send_customers);
               remaining_cus = 0;
            }else{
                send_customers = seat_available;
                randomTour.updateSeat(send_customers);//if seat < customer, fill seats and subtract customer
                remaining_cus-=send_customers;
            }
            System.out.printf("%17s" + " >> send%4d customers to "+randomTour.getName()+"%8sseats taken = %-2d\n",Thread.currentThread().getName(),send_customers,"",randomTour.getSeat());
        }
    }
    @Override
    public String toString() {
        return thread_name;
    }
}
