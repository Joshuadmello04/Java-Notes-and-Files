class Encapsulation{
        private int length;
        private int width;

        public Encapsulation(int l,int w) {
            setLength(l);
            setWidth(w);
        }
        public void setLength(int l)
        {
            if(l>0)
             this.length=l;
        }
        public void setWidth(int w)
        {
            if(w>0)
             this.width=w;
        }
        // public int getLength()
        // {
        //     return length;
        // }
        // public int getWidth()
        // {
        //     return width;
        // }
        public void area()
        {
            System.err.println("Area : "+length*width);
        }
        
    }

class Car{
        //int speed =0; bad can be accessed outside thus private
        private int speed =0; //now no need to setter in this case..coz no direct speed it goes from 0 to ...
        public void accelerate()
        {
            //u can put logic also like dont cross 80 or 120 speed
            if(speed<=120)
             speed++;
        }
        public void brake()
        {
            if(speed>0)
              speed--;
        }
}

public class Encapsulation1{
    public static void main(String[] args) {
        Encapsulation e = new Encapsulation(30, 40);
        Car c1 = new Car();
        //i could put c1.speed =100 or negative value which is bad
        c1.accelerate();
        c1.brake();
    }
    
}
