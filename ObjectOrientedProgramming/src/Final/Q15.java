package Final;

class Q15 {
}

class Egg {
    protected class Yolk {
        public Yolk() { System.out.println("Egg.Yolk()"); } public void f() { System.out.println("Egg.Yolk.f()"); }
    }
    private Yolk y = new Yolk();
    public Egg() { System.out.println("New Egg()"); } public void insertYolk(Yolk yy) { y = yy; }
    public void g() { y.f(); }
}
class BigEgg extends Egg {
    public class Yolk extends Egg.Yolk {
        public Yolk() {
            System.out.println("BigEgg.Yolk()");
        }

        public void f() {
            System.out.println("BigEgg.Yolk.f()");
        }
    }

    public BigEgg() {
        insertYolk(new Yolk());
    }

    public static void main(String[] args) {
        Egg e = new BigEgg();
        e.g();
    }
}