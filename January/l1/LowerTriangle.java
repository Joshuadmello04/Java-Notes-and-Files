import java.util.Scanner;

public class LowerTriangle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter input rows");
        int n = sc.nextInt();
        int cnt =0;  
        for(int i = n;i>=1;i--)
        {
            for(int j =1;j<=2*n-1;j++)
            {
                if(j <= cnt || j> 2*n -  cnt -1)
                {
                    System.out.print(" ");
                }
                else
                {
                    System.out.print("*");
                }
            }
            cnt++;
            System.out.println();
        }
    }
}
