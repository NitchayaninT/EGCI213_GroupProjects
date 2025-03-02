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
    CyclicBarrier barrier; //barrier for agency threads + main (for operation thread, pls create yourself)

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
    //method to read from file
    public void readSimulation()
    {
        boolean done = false;
        String path = "src/main/java/Project2/";
        String file_name = "config.txt";
        //initialized
        AgencyThreads = new ArrayList<>();

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
                        barrier = new CyclicBarrier( agency_num+1 ); //barrier for all agency threads + main
                        for(int i=0;i<agency_num;i++)
                        {
                            String agency_name = "AgencyThread_"+Integer.toString(i);
                            AgencyThreads.add(new AgencyThread(agency_name,max_daily_arrival));//create thread object
                        }
                        for (AgencyThread A: AgencyThreads) {A.setBarrier( barrier );}//set barrier to all threads
                    }
                    else if(lineCount==2){//line 2 = for tours
                        int tour_num = Integer.parseInt(cols[1].trim());
                        int tour_capacity = Integer.parseInt(cols[2].trim());
                    }
                    else if(lineCount==3){//line 3 = for places
                        int place_num = Integer.parseInt(cols[1].trim());
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
    //method to run simulation
    public void runSimulation() throws Exception {
        for(AgencyThread A : AgencyThreads) {A.start();} //start agency threads

        for(int i=1;i<= days_simulation;i++)//start simulation from day 1
        {
            System.out.printf("%17s"+ " >> "+"=".repeat(60)+"\n",Thread.currentThread().getName());
            System.out.printf("%17s"+ " >> "+"Day%2d\n",Thread.currentThread().getName(),i);

            barrier.await(); //check point for other threads to start (other threads wait for main to print day before printing more stuff)
            barrier.await(); //main thread wait until agency threads finished printing new arrival and remaining cus
            //threads printing more stuff...

        }
        for(AgencyThread A: AgencyThreads){
            A.join();
        }
    }
}
    