//Akkhrawin Nair 6580013
//Pakin Panawattanakul 6580043
//Nitchayanin Thamkunanon 6580081
//Pibhu Chitburanachart 6580195
//Panupong Sangaphunchai 6580587
package Project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

//main class
public class Project2_main {
    int days_simulation; //days of simulation
    ArrayList<AgencyThread> AgencyThreads;//array of Agency threads
    ArrayList<OperatorThread> OperatorThreads; //array of Operator threads
    CyclicBarrier sharedBarrier; //barrier for both AgencyThreads and OperatorThreads to communicate
    ArrayList<Place> places;

    //MAIN METHOD
    public static void main(String []args)
    {
        Project2_main program = new Project2_main();
        program.readSimulation();
        try{
            program.runSimulation();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //initialize
    private void init()
    {
        AgencyThreads = new ArrayList<>();
        OperatorThreads = new ArrayList<>();
        places = new ArrayList<>();
    }
    //initialize and set barrier
    private void setBarrier()
    {
        sharedBarrier = new CyclicBarrier(AgencyThreads.size()+OperatorThreads.size()+1);
        for (AgencyThread A: AgencyThreads) {A.setBarrier( sharedBarrier );}//set barrier to AgencyThreads
        for (OperatorThread O : OperatorThreads){O.setBarrier((sharedBarrier));}//set barrier to OperatorThreads
    }
    //method to read from file
    private void readSimulation()
    {
        boolean done = false;
        String path = "src/main/java/Project2/";
        String file_name = "config.txt";
        init();//initialized thread array objects
        do {
            try {
                String in_path = path + file_name;
                File infile = new File(in_path);
                Scanner scan = new Scanner(infile);

                //reading from file
                int lineCount = 0;
                while (scan.hasNext()) {
                    String line = scan.nextLine();
                    String []cols = line.split(",");
                    if(lineCount==0) { //line 0 = for number of days
                        days_simulation = Integer.parseInt(cols[1].trim());
                    }
                    else if (lineCount==1) { //line 1 = for agencies
                        int agency_num = Integer.parseInt(cols[1].trim());
                        int max_daily_arrival = Integer.parseInt(cols[2].trim());
                        for(int i=0;i<agency_num;i++)
                        {
                            String agency_name = "AgencyThread_"+Integer.toString(i);
                            AgencyThreads.add(new AgencyThread(agency_name,max_daily_arrival));//create thread object
                        }
                    }
                    else if(lineCount==2){//line 2 = for tours
                        int tour_num = Integer.parseInt(cols[1].trim());
                        int tour_capacity = Integer.parseInt(cols[2].trim());
                        for(int i=0;i<tour_num;i++)
                        {
                            String tour_name = "Tour_"+Integer.toString(i);
                            String operator_name = "OperatorThread_"+Integer.toString(i);
                            OperatorThreads.add(new OperatorThread(operator_name,new Tour(tour_capacity,tour_name)));
                        }
                    }
                    else if(lineCount==3){//line 3 = for places
                        int place_num = Integer.parseInt(cols[1].trim());
                        for(int i=0;i<place_num;i++)
                        {
                            String place_name = "Place_"+Integer.toString(i);
                            places.add(new Place(place_name));
                        }
                    }
                    lineCount++;
                }
                done = true;
            } catch (FileNotFoundException e) {
                System.out.println();
                System.out.println(e);
                System.out.println("New file name =");
                Scanner key_scan = new Scanner(System.in);
                file_name = key_scan.nextLine();
            }
            catch(Exception e)
            {
                System.out.println(e.toString());
            }

        }while(!done);
    }
    //method to print parameters
    private void printParameters()
    {
        System.out.printf("%17s"+" >> "+"=".repeat(25)+" Parameters "+"=".repeat(25)+"\n",Thread.currentThread().getName());
        System.out.printf("%17s"+" >> "+"%-19s = "+days_simulation+"\n",Thread.currentThread().getName(),"Days of Simulation");
        System.out.printf("%17s"+" >> "+"%-19s = "+AgencyThread.max_arrival+"\n",Thread.currentThread().getName(),"Max Arrival");
        System.out.printf("%17s"+" >> "+"%-19s = "+AgencyThreads+"\n",Thread.currentThread().getName(),"AgencyThreads");
        System.out.printf("%17s"+" >> "+"%-19s = "+Tour.capacity+"\n",Thread.currentThread().getName(),"Tour capacity");
        System.out.printf("%17s"+" >> "+"%-19s = "+OperatorThreads+"\n",Thread.currentThread().getName(),"Operator Threads");
        System.out.printf("%17s"+" >> "+"%-19s = "+places+"\n",Thread.currentThread().getName(),"Places");
        //for tour, operatorThreads, Places. dont forget to create the objects in readSimulation() and then print here
    }
    //method to run simulation
    private void runSimulation() throws Exception {
        setBarrier();
        for(AgencyThread A : AgencyThreads) {A.start();} //start agency threads
        for(OperatorThread O : OperatorThreads) {O.start();} //start operator threads
        printParameters();
        for(int i=1;i<= days_simulation;i++)//start simulation from day 1
        {
            System.out.printf("%17s"+ " >> "+"=".repeat(60)+"\n",Thread.currentThread().getName());
            System.out.printf("%17s"+ " >> "+"Day%2d\n",Thread.currentThread().getName(),i);

            sharedBarrier.await(); //check point for other threads to start (other threads wait for main to print day before printing more stuff)
            sharedBarrier.await(); //main thread wait until agency threads finished printing new arrival and remaining cus
            //threads printing more stuff...

        }
        for(AgencyThread A: AgencyThreads){
            A.join();
        }
    }
}
    