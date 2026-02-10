import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo {
    
    static class SortTask extends RecursiveTask<Void> {
        private int[] array;
        private int left;
        private int right;
        private static final int THRESHOLD = 100;
        
        SortTask(int[] array, int left, int right) {
            this.array = array;
            this.left = left;
            this.right = right;
        }
        
        @Override
        protected Void compute() {
            if (right - left <= THRESHOLD) {
                // Base case: use Arrays.sort for small arrays
                Arrays.sort(array, left, right + 1);
            } else {
                // Recursive case: split the array
                int mid = (left + right) / 2;
                
                SortTask leftTask = new SortTask(array, left, mid);
                SortTask rightTask = new SortTask(array, mid + 1, right);
                
                // Fork both tasks
                leftTask.fork();
                rightTask.fork();
                
                // Wait for both to complete
                leftTask.join();
                rightTask.join();
                
                // Merge the sorted halves
                merge(left, mid, right);
            }
            return null;
        }
        
        private void merge(int left, int mid, int right) {
            int[] temp = new int[right - left + 1];
            int i = left, j = mid + 1, k = 0;
            
            while (i <= mid && j <= right) {
                if (array[i] <= array[j]) {
                    temp[k++] = array[i++];
                } else {
                    temp[k++] = array[j++];
                }
            }
            
            while (i <= mid) {
                temp[k++] = array[i++];
            }
            
            while (j <= right) {
                temp[k++] = array[j++];
            }
            
            System.arraycopy(temp, 0, array, left, temp.length);
        }
    }
    
    public static void main(String[] args) {
        // Create an array of 1000 ints with random values
        int[] array = new int[1000];
        Random random = new Random();
        
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(10000);
        }
        
        System.out.println("Original array (first 20 elements): ");
        printArray(array, 0, 19);
        
        // Create ForkJoinPool and sort using Fork/Join
        ForkJoinPool pool = new ForkJoinPool();
        SortTask task = new SortTask(array, 0, array.length - 1);
        
        long startTime = System.currentTimeMillis();
        pool.invoke(task);
        long endTime = System.currentTimeMillis();
        
        System.out.println("\nSorted array (first 20 elements): ");
        printArray(array, 0, 19);
        
        System.out.println("\nTime taken to sort: " + (endTime - startTime) + " ms");
        System.out.println("Is array sorted? " + isSorted(array));
        
        pool.shutdown();
    }
    
    private static void printArray(int[] array, int start, int end) {
        for (int i = start; i <= end && i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
    
    private static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
