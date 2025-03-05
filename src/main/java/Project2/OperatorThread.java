package Project2;

import java.util.*;
import java.util.concurrent.CyclicBarrier;

//individual tour as thread
public class OperatorThread extends Thread{
    private String name;
    private CyclicBarrier barrier;
    OperatorThread(String name, Tour t){super(name); this.name=name; this.tour=t;}
    Tour tour;
    private static ArrayList<Place> place ;

    //set barrier
    public void setBarrier(CyclicBarrier ba) {barrier = ba;}
    public void setPlace(ArrayList<Place>p){place = p;}

    @Override
    public void run()
    {
        while(Project2_main.days_simulation_loop>0)
        {
           //wait for main to print day
           try {
                barrier.await();
            } catch (Exception e) {}
           //wait for agency threads to finish printing
           try {
                barrier.await();
            } catch (Exception e) {}
           
           //wait for all agency threads to send customers to tours
            try {
                barrier.await(); //
            } catch (Exception e) {}

            //take customers to place
            CustomertoPlace();
            
            //wait for all operator threads to send customers to places
            try {
                barrier.await(); //
            } catch (Exception e) {}
         
        }
    }
    private void CustomertoPlace()
    {
        Random rand = new Random();
        Place randomPlace = place.get(rand.nextInt(place.size()));
        synchronized(randomPlace)
        {
        int customer = tour.getSeat();//# of customer sent = # of seat taken
        randomPlace.updateVisitor(customer);
        tour.updateCustomer(customer);
        String s = "";
        if(customer==0)s="no customer";
        else s = String.format("take%4d customers to "+randomPlace.getName()+"%3svisitor count =%6d",customer,"",randomPlace.getVisitor());
        System.out.printf("%17s" + " >> %s\n",Thread.currentThread().getName(),s);
        tour.setSeat(0);//after sent, seats are emptied
        }
    }
    @Override
    public String toString() {
        return name;
    }
}
