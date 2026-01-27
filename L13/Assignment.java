import java.util.Random;
import java.util.Scanner;

class Race {
    private static final int BIKER_COUNT = 10;
    private final Biker[] bikers = new Biker[BIKER_COUNT];
    private final Thread[] threads = new Thread[BIKER_COUNT];

    private long globalStartTime; // ✅ same for everyone

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

        // ✅ create and start threads now (all ready, but suspended)
        for (int i = 0; i < BIKER_COUNT; i++) {
            threads[i] = new Thread(bikers[i]);
            threads[i].start();
        }

        countdown();

        sc.close();
    }

    public void countdown() {
        try {
            for (int i = 5; i >= 1; i--) {
                System.out.println(i);
                Thread.sleep(1000);
            }
            System.out.println("GOOOOO!!!!!");

            // ✅ Set common start time for all
            globalStartTime = System.currentTimeMillis();
            for (int i = 0; i < BIKER_COUNT; i++) {
                bikers[i].setStartTime(globalStartTime);
            }

            // ✅ Resume all threads together
            for (int i = 0; i < BIKER_COUNT; i++) {
                threads[i].resume(); // ⚠️ deprecated but required
            }

            // ✅ Wait for all to finish
            for (int i = 0; i < BIKER_COUNT; i++) {
                threads[i].join();
            }

            sortByTimeTaken();
            display(bikers);

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

    void display(Biker[] bikers) {
        System.out.println();
        System.out.println("========== FINAL DASHBOARD ==========");
        System.out.println("Rank | Name | Start Time | End Time | Time Taken (ms)");
        System.out.println("------------------------------------------------------");

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
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public void run() {
        // ✅ suspend immediately so everyone waits for countdown
        Thread.currentThread().suspend(); // ⚠️ deprecated but required
        while (coveredDistance < trackLengthMeters) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int speed = random.nextInt(41) + 10;
            coveredDistance += speed;
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

public class Assignment {
    public static void main(String[] args) {
        Race race = new Race();
        race.setupRace(); // ✅ only call this once
    }
}

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

7. all bikes must be ready but wait for countdown
8. all bikes must run at a time after the countdown to reaches 0
9. dashboard...same but ensure start time of all is same
.......
*/