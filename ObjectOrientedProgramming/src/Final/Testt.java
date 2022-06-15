package Final;

public class Testt {
    public static void main(String args[]){
        Sub s = new Sub();
        s.getA();
    }
}

abstract class Base {
    int a;
    int b;
    public Base(){
        this.a=1;
        this.b=2;
    }

    public void getA(){
        System.out.println(a +" "+ b);
    }
}

class Sub extends Base {
    int a;
    public Sub(){
        this.a=3;
    }

//    public void getA(){
//        System.out.println(a +" "+ b);
//    }
}

