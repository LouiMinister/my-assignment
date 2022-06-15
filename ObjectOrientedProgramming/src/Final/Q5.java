package Final;

public class Q5 {
    public static void main(String args[]){
        ExceptionHandlingTest2.main(args);
    }
}

class ChildException extends Exception { }
class GrandChildException extends ChildException { }
class ExceptionHandlingTest2 { public static void main(String args[ ]) {
    try {
        throw new GrandChildException( );
    } catch(GrandChildException g) { System.out.println("GrandChildException");
    } catch(ChildException c) { System.out.println("ChildException");
    }
    try {
        throw new GrandChildException( );
    } catch(ChildException c) { System.out.println("ChildException");
    } }
}