import java.util.Scanner;

public class OneZeroTriangle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter input rows");
        int n = sc.nextInt();
        int oddCheck;
        for (int i = 1; i <= n; i++) {
            oddCheck = (i%2==0)?0:1;
            for(int j=1;j<=i;j++)
            {
                if(oddCheck ==1){
                    System.out.print(oddCheck);
                    oddCheck=0;
                }
                else{
                    System.out.print(oddCheck);
                    oddCheck =1;
                }
            }
            System.out.println();
        }
    }
}
