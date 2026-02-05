import java.util.Scanner;

public class UpperTriangle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter input rows");
        int n = sc.nextInt();
        for(int i=1;i<=n;i++)
        {
            for(int j = 1;j<=2*n;j++)
            {
               if(j>=n-i+1 && j<=n+i-1)
               {
                System.out.print("*");
               }
               else{
                System.out.print(" ");
               }
            }
            System.out.println();
        }
    }
}
