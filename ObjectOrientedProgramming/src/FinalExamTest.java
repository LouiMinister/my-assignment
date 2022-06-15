import java.util.ArrayList;
import java.util.Objects;

interface Inter {
     static void method(){

     }
}

abstract class Outer {
    static class InnerStatic {

    }

    class Inner{

    }

    abstract void method();
}

class AB {

    public static void method() throws Exception{
        try{
            throw new Exception();
        } finally {
            System.out.println("HI1");
        }
    }
}

class Some implements Cloneable{
    int a;
    ArrayList b = new ArrayList();

    @Override
    public Some clone() throws CloneNotSupportedException {
        Some some = (Some) super.clone();
        some.b = (ArrayList)b.clone();
        return (Some)super.clone();
    }
}

public class FinalExamTest {

    public static void main(String args[]){
        try {
            AB.method();
        }catch (Exception e) {
            System.out.println("HI3");
        } finally {
            System.out.println("HI2");
        }
    }

}