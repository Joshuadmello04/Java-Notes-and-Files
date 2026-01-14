class Room
{
	int length;
	int width;
	Room()
	{
		 length = 10;
		 width = 20;
		//System.out.println("--------------");
	}
	Room(String s)
	{
		this();
		System.out.print(" Area: ");
	}
	Room(int x, int y)
	{
		//this("aahsxs");
		this.length = x;
		this.width = y;
		this.area();
	}
	public void area()
	{
		System.out.println("Area : "+(length * width));
	}
}
public class RoomMain
{
	public static void main(String args[])
	{
		long a = 1234567891011L;
		long l = 123444;
		//int b = 12345678910; //error
		//int b = (long) 12345678910L error;
		System.out.println(a);
		// Room r1 = new Room();
		// Room r2 = new Room(30, 40);
		// Room r3 = new Room(60, 40);
		// Room r4 = new Room(70, 40);
		// Room r5 = new Room(90, 40);		
		//r1.area();
		//r2.area();
		//System.out.println("---------------");

		//r1.length = 50;
		//r2.width = 60;
		//r1.area();
		//r2.area();
		//System.out.println("---------------");

		//r1 = r2;
		//r1.length = 70;
		//r2.width = 80;
		//r1.area();
		//r2.area();
	}
}
