package Project2;

import java.util.concurrent.CyclicBarrier;

//individual tour as thread
public class OperatorThread extends Thread{
    private String name;
    private CyclicBarrier barrier;
    OperatorThread(String name, Tour t){super(name); this.name=name; this.tour=t;}
    Tour tour;

    //set barrier
    public void setBarrier(CyclicBarrier ba) { barrier = ba; }

    @Override
    public void run()
    {
        while(true)
        {
           try {
                barrier.await();
            } catch (Exception e) {}
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
