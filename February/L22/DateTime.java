import java.time.*; //made by Joda libraries

public class DateTime{
    public static void main(String[] args)
    {
        LocalDate d1 = LocalDate.of(1983,9,17);
        LocalDate d2 = LocalDate.of(1984,8,19);
        System.out.println(d1.plusMonths(6));
        System.out.println(d2.minusMonths(6));

        LocalTime t1 = LocalTime.now();
        System.out.println(t1);

        //Set s =ZoneId.getAvailableZoneIds();
        //s.forEach(System.out :: println);

        LocalDateTime dt = LocalDateTime.now();
        System.out.println(dt);
    }
}