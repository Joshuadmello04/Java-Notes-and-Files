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

public class Assignment {

    public static void main(String[] args) {
        Race race = new Race();
        race.setupRace();
        race.startRace();
    }
}

class Race {

    private static final int BIKER_COUNT = 10;

    private Biker[] bikers = new Biker[BIKER_COUNT];
    private Thread[] threads = new Thread[BIKER_COUNT];

    public void setupRace() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter track length (in km): ");
        int trackLengthKm = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < BIKER_COUNT; i++) {
            System.out.println("Enter biker " + (i + 1) + " name: ");
            String name = sc.nextLine();
            bikers[i] = new Biker(name, trackLengthKm);
        }
    }

    public void startRace() {
        countdown();

        for (int i = 0; i < BIKER_COUNT; i++) {
            threads[i] = new Thread(bikers[i]);
            threads[i].start();
        }

        for (int i = 0; i < BIKER_COUNT; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        sortByTimeTaken();
        RaceDashboard.display(bikers);
    }

    private void countdown() {
        try {
            for (int i = 5; i >= 1; i--) {
                System.out.println(i);
                Thread.sleep(1000);
            }
            System.out.println("GOOOOO!!!!!");
            System.out.println();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sortByTimeTaken() {
        for (int i = 0; i < bikers.length - 1; i++) {
            for (int j = 0; j < bikers.length - i - 1; j++) {
                if (bikers[j].getTimeTaken() > bikers[j + 1].getTimeTaken()) {
                    Biker temp = bikers[j];
                    bikers[j] = bikers[j + 1];
                    bikers[j + 1] = temp;
                }
            }
        }
    }
}

class Biker implements Runnable {

    private String name;
    private int trackLengthMeters;
    private int coveredDistance = 0;
    private int nextMilestone = 100;
    private long startTime;
    private long endTime;
    private static final Random random = new Random();

    public Biker(String name, int trackLengthKm) {
        this.name = name;
        this.trackLengthMeters = trackLengthKm * 1000;
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();

        while (coveredDistance < trackLengthMeters) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int speed = random.nextInt(41) + 10;
            coveredDistance += speed;
            // Print 100m milestones
            if (coveredDistance >= nextMilestone) {
                System.out.println(name + " completed " + nextMilestone + " meters");
                nextMilestone += 100;
            }
            if (coveredDistance > trackLengthMeters) {
                coveredDistance = trackLengthMeters;
            }
        }
        endTime = System.currentTimeMillis();
    }
    public long getTimeTaken() {
        return endTime - startTime;
    }
    public String getName() {
        return name;
    }
    public long getStartTime() {
        return startTime;
    }
    public long getEndTime() {
        return endTime;
    }
}

class RaceDashboard {

    public static void display(Biker[] bikers) {
        System.out.println();
        System.out.println("========== FINAL DASHBOARD ==========");
        System.out.println("Rank | Name | Start Time | End Time | Time Taken (ms)");
        System.out.println("-----------------------------------------------");

        for (int i = 0; i < bikers.length; i++) {
            Biker b = bikers[i];
            System.out.println(
                    (i + 1) + " | " +
                    b.getName() + " | " +
                    b.getStartTime() + " | " +
                    b.getEndTime() + " | " +
                    b.getTimeTaken()
            );
        }
    }
}
