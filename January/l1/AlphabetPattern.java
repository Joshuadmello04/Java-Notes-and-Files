import java.util.Scanner;

public class AlphabetPattern {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter input rows");
        int n = sc.nextInt();
        for(int i=0;i<n;i++)
        {
            for(char ch='A';ch<='A'+i;ch++)
            {
                System.out.print(ch);
            }
            System.out.println();
        }
        sc.close();  
    }
}
