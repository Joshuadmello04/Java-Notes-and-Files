import java.util.Scanner;

public class Mountain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter input rows");
        int n = sc.nextInt();

        for(int i=1;i<=n;i++)
        {
            for(int j=1;j<=2*n;j++)
            {
                if(j<=i){
                    System.out.print(j);
                }
                else if(j > 2*n-i)
                {
                    System.out.print(j-(2*n-i));
                }
                else{
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
