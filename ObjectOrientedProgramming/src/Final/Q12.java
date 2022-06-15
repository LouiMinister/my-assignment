package Final;

public class Q12 {
}

abstract class A {
    public A a;
    public A() { System.out.println("A()"); }
    abstract public void f();
}
class B extends A {
    B() {
        super();
        System.out.println("B()");
    }

    public void f() {
        System.out.println("f()");
    }
}
class AbstractTest {
    public static void main(String[] args) {
        new B().f();
    }
}
