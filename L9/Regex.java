import java.util.regex.*;

class PanCard{
    String panNum;
    public PanCard(String num)
    {
        panNum=num;
    }   
    public void validatePan()
    {
        String regex="^[A-Z]{5}[0-9]{4}[A-Z]{1}$";
        if(panNum.matches(regex)){
            System.out.println(panNum+" is a valid PAN NUM");
        }
        else
            System.out.println(panNum+" is invalid");
    }
    public void validatePan2()
    {
        Pattern p1 =Pattern.compile("\\b[A-Z]{5}[0-9]{4}[A-Z]\\b"); //solving using java regex pattern package
        Matcher m1 = p1.matcher(panNum);
        
        if(m1.find())
        {
            System.out.println("Valid Pan " +panNum);
        }
    }
}
public class Regex{
    public static void main(String[] args) {
        new PanCard("ABCDE12345").validatePan();
        new PanCard("ABCDE1234F").validatePan();
        new PanCard("ABCD012345F").validatePan();
        new PanCard("ABCDE12345FG").validatePan();
        new PanCard("ABCD0123FG").validatePan();
        System.out.println("<=============>");
        new PanCard("ABCDE12345").validatePan2();
        new PanCard("My pan no is : ABCDE1234F").validatePan2();
        new PanCard("ABCD012345F").validatePan2();
    }
}