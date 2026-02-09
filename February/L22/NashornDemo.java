//Nashorn package
import java.io.FileReader;
import javax.script.*;

public class NashornDemo {
    public static void main(String[] args) {
        try {
            ScriptEngineManager mgr = new ScriptEngineManager();
            //mgr.getEngineByName("Nashorn");
            ScriptEngine engine = mgr.getEngineByName("nashorn");
            //engine.eval(new FileReader("abc.js"));

            engine.eval(new FileReader("xyz.js"));
            Invocable inv = (Invocable) engine;
            inv.invokeFunction("abc");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
