import java.util.Scanner;

public class CreateRhombus {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int temp=1;
        for(int i=1;i<=10;i++)
        {

             System.out.println(temp);
             temp=temp<<1;
        }
        // System.out.println("Enter lines of rhombus");
        // int n = sc.nextInt();
        // pattern(n);
    }
    static void pattern(int n)
    {
        for(int i=1;i<=n;i++)
        {
            for(int j=1;j<2*n;j++)
            {
                if(j>=(n-i+1) && j<=(n+i-1))
                {
                    System.out.print("*");
                }
                else
                {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        
        for(int i=n-1;i>=1;i--)
        {
            for(int j=1;j<2*n;j++)
            {
                if(j>=(n-i+1) && j<=(n+i-1))
                {
                    System.out.print("*");
                }
                else 
                 System.out.print(" ");
            }
            System.out.println();
        }
    }
}
