import java.util.Scanner;

public class MidTerm17 {

    public static void p2(){
        char a = '\377';
        System.out.println(a);
    }

    public static void p3(){
        boolean b = !((!true || true) && false);
        System.out.println(b);
        System.out.println((double)1/4);

    }

    public static void p4(){
        String[] s = {"A", "B", "C"};
        int a = 3/2;
        System.out.print(s[5/2]);
    }

    public static void p7(){
        short[] a = new short[2];
        int[] b = new int[2];
        double[] c = new double[2];
        Object[] d = new Object[2];
        System.out.println(a[0] + ", "+ b[0] + ", " + c[0] + ", " + d[0]);
    }

    public static void p9(){
        int[][] test;
        int[] t1 = { 2, 3, 4 };
        int[] t2 = { 5, 7 };
        test = new int[3][5];
        test[0] = t1;
        test[1] = t2;
        for(int[] ary:test){
            for(int v: ary){
                System.out.println(v);
            }
        }


    }

//    public void my1(){
//        long a = 3;
//        int c = a;
//    }

    public static void main(String[] args){
//        Scanner scanner = new Scanner(System.in);
//        long a = 3;
//        double b = 4;
//        double c = a;
////        int d = c;
//
//        System.out.println(0.1/0);

        long a = 1;
        float b = a;

        System.out.println(b);
    }
}
