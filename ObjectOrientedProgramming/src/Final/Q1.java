package Final;

public class Q1 {
    public static void main(String[ ] args) {
        InheritanceTest.main(args);
    }
}

class InheritanceTest {
    public static void main(String[ ] args) {
        FirstChild fc = new FirstChild( ); System.out.println(fc.read( ));
        SecondChild sc = new SecondChild( ); System.out.println(sc.read( ));
        ThirdChild tc1 = new ThirdChild(fc); System.out.println(tc1.read( ));
        ThirdChild tc2 = new ThirdChild(sc);
        System.out.println(tc2.read( )); }
}
class Parent2 {
    public String read( ) {
        return "P"; }
}
class FirstChild extends Parent2 { public String read( ) {
    return super.read( ) + '1'; }
}
class SecondChild extends Parent2 { public String read( ) {
    return super.read( ) + '2'; }
}
class ThirdChild extends Parent2 { Parent2 p;
    public ThirdChild(Parent2 p) { this.p = p;
    }
    public String read( ) { return p.read( ) + "3";
    } }