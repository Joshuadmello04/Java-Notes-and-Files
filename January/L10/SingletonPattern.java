package L10;
 
//principal shows early instantiation
class Principal{
    private static final Principal p1 = new Principal();
    private Principal(){
        System.out.println("PRincipal obj created");
    }
    public static Principal getPrincipal(){
        return p1;
    }
}

//vice principal uses lazy instantiation
class VicePrincipal{
    private static VicePrincipal vp1;
    private VicePrincipal(){
        System.out.println("Vice Principal obj created");
    }
    public static VicePrincipal getVicePrincipal(){
        if(vp1 == null){
            vp1 = new VicePrincipal();
        }
        return vp1;
    }
}
public class SingletonPattern {
    public static void main(String[] args) {
        System.out.println("**Singleton Demo**");
        VicePrincipal vp1 = VicePrincipal.getVicePrincipal();
        VicePrincipal vp2 = VicePrincipal.getVicePrincipal();
        
        Principal p1 = Principal.getPrincipal();
        Principal p2 = Principal.getPrincipal();
        
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(vp1);
        System.out.println(vp2);
    }

    static{
        try {
            Class.forName("Principal");
            Class.forName("Vice Principal");
        } catch (Exception e) {
        }
    }
}
