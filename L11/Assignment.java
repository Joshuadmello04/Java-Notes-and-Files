/* Bike racing game
1. ask you to enter 10 bikers name
2. Ask you to enter distance coverage in kms (track length)
3. Put a countdown 5,4,3,2,1 goooo!!!!!
4. Now, all 10 bikers will go travel that track length...but display()every 100ms coverage : the biker name-> ex : Rahul covered 100meters
John covered 200meterss...
Sam covered 100meterss .....
5. The speed should vary for every biker dynamically..to prevent priorirty based speed coz thats not fun or fair..so every 100 ms it should be like realtime anyone can be 1st 2nd 3rd .... every 100 meters
6. The final thing is a simple dashboard with Ranking
1. name, start time,end time,time taken
2. name, start time,end time,time taken
3. name, start time,end time,time taken
.......
*/

import java.util.Random;
import java.util.Scanner;

class Biker extends Thread {
    private final String name;
    private final int trackLengthMeters;
    private int distanceCovered = 0;
    private long startTime;
    private long endTime;
    private final Random rand = new Random();

    public Biker(String name, int trackLengthKm) {
        this.name = name;
        this.trackLengthMeters = trackLengthKm * 1000;
    }

    public String getBikerName() 
    {
         return name; 
    }
    public long getStartTime() 
    { 
        return startTime/1000;
    }
    public long getEndTime() 
    { 
        return endTime/1000;
    }
    public long getTimeTaken() 
    { 
        return (endTime - startTime)/1000;
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        while (distanceCovered < trackLengthMeters) {
            int change = 50 + rand.nextInt(100); //randomly change time
            distanceCovered += change;
            if (distanceCovered > trackLengthMeters) {
                distanceCovered = trackLengthMeters;
            }
            if(distanceCovered%100 == 0){
                System.out.println("Biker "+ name + " covered "+ distanceCovered+" ms");
            }
             try {
                Thread.sleep(100); // 100 ms tick
             } catch (InterruptedException e) {
            }
        }
        endTime = System.currentTimeMillis();
    }
}

public class Assignment {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        String[] names = new String[10];
        System.out.println("Enter names of 10 bikers:");
        for (int i = 0; i < names.length; i++) {
            System.out.print("Biker " + (i + 1) + " Name: ");
            String name = sc.nextLine();
            names[i] = name;
        }
        System.out.print("Enter track length in kms: ");
        int trackLengthKm = sc.nextInt();
        //biker threads
        Biker[] bikers = new Biker[10];
        for (int i = 0; i < bikers.length; i++) {
            //new biker obj coz thread..
            bikers[i] = new Biker(names[i], trackLengthKm);
        }
        // Countdown
        System.out.println("\nRace starting in...");
        for (int i = 5; i >= 1; i--) {
            System.out.println(i);
            Thread.sleep(1000);
        }
        System.out.println("GOOOO!!!!!\n");
        // Start the race
        for (Biker b : bikers) {
            b.start();
        }
        // Wait for the bikers to finish
        for (Biker b : bikers) {
            b.join();
        }

        for (int i = 0; i < bikers.length - 1; i++) {
            for (int j = 0; j < bikers.length - i - 1; j++) {
                if (bikers[j].getTimeTaken() > bikers[j + 1].getTimeTaken()) {
                    Biker tmp = bikers[j];
                    bikers[j] = bikers[j + 1];
                    bikers[j + 1] = tmp;
                }
            }
        }

        // Dashboard
        System.out.println("\n--- Race Results ---");
        System.out.println( "Rank, Name, Start(ms), End(ms), Time(ms)");
        for(int i = 0; i < bikers.length; i++) {
            Biker b = bikers[i];
            System.out.println(i+1 + " "+ b.getBikerName()+" "+b.getStartTime() + " "+b.getEndTime());
        }
    }
}
