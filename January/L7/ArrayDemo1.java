public class ArrayDemo1 {
    public static void main(String[] args) {
        int a[][] = new int[6][]; //rows mandatory
        a[0] = new int[1];//first floor 1 stays
        a[1] = new int[3];
        a[2] = new int[2];
        a[3] = new int[20];
        a[4] = new int[11];
        a[5] = new int[5];

        //traversing this array
        for(int rows=0; rows<a.length; rows++) //traverse all rows
        {
            for(int cols=0; cols<a[rows].length; cols++)
            {
                System.out.print(a[rows][cols]);
            }
            System.out.println();
        }
    }
}
