package Project2;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

//individual travel agency
//each iteration of a loop = 1 day
public class AgencyThread extends Thread{
    private String thread_name;
    private int remaining_cus;
    public static int max_arrival;
    private CyclicBarrier barrier;
    //private ArrayList<Tour> tours

    //constructor
    AgencyThread(String name, int max){super(name); thread_name=name; max_arrival=max;}

    //setters
    public void setBarrier(CyclicBarrier ba) { barrier = ba; }

    //run method for each agency thread
    public void run()
    {
        while(true)
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

            //printing send xx customers to Tour ...
        }
    }
    private void printNewArrival(int arriving)
    {
        System.out.printf("%17s" + " >> new arrival = "+arriving+"%15s remaining customers = "+remaining_cus+"\n",Thread.currentThread().getName(),"");
    }
    @Override
    public String toString() {
        return thread_name;
    }
}
