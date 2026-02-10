
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


class Race{
    private static final int BIKERS =10;
    
    public void setupRace() {
        Scanner sc = new Scanner(System.in);  
        System.out.print("Enter track length (km): ");
        int km = sc.nextInt();
        List<String> names = new ArrayList<>();

        for(int i=1;i<=BIKERS;i++){
            names.add("Biker"+i);
        }

        try {
            startRace(names, km);
        } catch (InterruptedException | java.util.concurrent.ExecutionException e) {
            e.printStackTrace();
        }
    }        


    public void startRace(List<String> names,int trackLength) throws InterruptedException, java.util.concurrent.ExecutionException 
    { 
        int trackMeters = trackLength*1000;
        CountDownLatch startSignal = new CountDownLatch(1);
        CyclicBarrier milestoneBarrier = new CyclicBarrier(BIKERS, ()->System.out.println("All Bikers covered milestone"));
        ExecutorService executor = Executors.newFixedThreadPool(BIKERS);
        List<Future<RaceResult>> results = new ArrayList<>();
        
        for(String name : names){
            results.add(executor.submit(new Biker(name, trackMeters, startSignal, milestoneBarrier)));
        }

        countdown();
        startSignal.countDown(); // Start

        List<RaceResult> raceResults = new ArrayList<>();

        for(Future<RaceResult> future : results){
            raceResults.add(future.get());
        }
        executor.shutdown();
        display(raceResults);
    }  

 private void countdown() throws InterruptedException{
    for(int i=5;i>=1;i--)
    {
        System.out.println(i+"...");
        Thread.sleep(1000);
    }
    System.out.println("!!!!---BEGIN---!!!\n");
 }

 private void display(List<RaceResult> results){
    results.sort(Comparator.comparingLong(RaceResult::timeTaken));
    System.out.println("========== FINAL DASHBOARD ==========");
        System.out.println("Rank\t|\tName\t|\tStart\t|\tEnd\t|\tTime(ms)");
        System.out.println("-----------------------------------");

        int rank = 1;
        for (RaceResult r : results) {
            System.out.println(rank++ + "\t|\t" +r.name + "\t|\t" +r.startTime + "\t|\t" +r.endTime + "\t|\t" +r.timeTaken());
        }
 }
}

class RaceResult{
    String name;
    long startTime;
    long endTime;

    public RaceResult(String name, long startTime, long endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long timeTaken() {
        return endTime - startTime;
    }
}

class Biker implements Callable<RaceResult>
{
    private final String name;
    private final int trackLength;
    private final CountDownLatch startSignal;
    private final CyclicBarrier milestoneBarrier;

    public Biker(String name, int trackLength, CountDownLatch startSignal, CyclicBarrier milestoneBarrier) {
        this.name = name;
        this.trackLength = trackLength;
        this.startSignal = startSignal;
        this.milestoneBarrier = milestoneBarrier;
    }

    @Override
    public  RaceResult call() throws Exception {
        startSignal.await(); // Wait for the start signal
        
        long startTime = System.currentTimeMillis();
        int covered = 0;
        int nextMilestone = 500;  
        
        while(covered<trackLength)
        {
            int speed = 10 + (int)(Math.random() * 20); // Random speed between 10-30 m/s
            covered += speed;
            Thread.sleep(200); // Simulate time taken for each second
            
            if(covered >= nextMilestone && nextMilestone <= trackLength) {
                System.out.println(name + " reached " + nextMilestone + " meters.");
                milestoneBarrier.await(); // Wait for all bikers to reach the milestone
                nextMilestone += 500; // Set next milestone
            }
        }
        long endTime = System.currentTimeMillis();
        return new RaceResult(name, startTime, endTime);
    }
}

public class BikeAssignment {
    public static void main(String[] args) {
        new Race().setupRace();
    }
}
