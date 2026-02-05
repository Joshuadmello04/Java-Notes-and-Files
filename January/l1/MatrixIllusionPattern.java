import java.util.Scanner;

public class MatrixIllusionPattern {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter input rows");
        int n = sc.nextInt();
        int width =2*n-1;
        for(int i=1;i<=n;i++)
        {
            for(int j=1;j<=n;j++)
            {
                if(i==1 || i == n || j==1|| j== 2*n-1){
                    System.out.print();
                }

            }
        }
    }
}
