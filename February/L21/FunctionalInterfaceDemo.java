        
        @FunctionalInterface
        interface I{
            public abstract void abc();
            public abstract int hashCode();
            public abstract boolean equals(Object obj);
        }   

        @FunctionalInterface
        interface J extends Runnable{

        }

        @FunctionalInterface
        interface K extends Comparable{

        }

        @FunctionalInterface
        interface L{
            void abc();
            
            static void atoz(){

            }

            default void xyz(){

            }
        }
        class A implements L{

        }

public class FunctionalInterfaceDemo {
      

        public static void main(String[] args) {
            
        }

}
