import java.util.Arrays;
import java.util.Scanner;

public class MidTerm19 {

    int a = 3;
    {a=4;}
    {a=4;}
    public static void p1(){
        byte a=01_77; // 56 + 7 + 64 = 127
        int b = 0xff_ff_ff_ff; // 16비트, int => 11111111_11111111_11111111_11111111
        byte c = 0x7f; // 15 + 16*8 =128
        int d = 0x00000000000000000000000000000000000000000000000000000_0000_0000_0000_0000_0000_0001;
    }

    static float l;
    public static void p4(){
        boolean b1  = true;
        byte b = 1;
        int i = 3;
        long l = b+i;
        double i2 = 0.1d;
        long a = 9999999999L;
        double d3 = 0.3;
        int cc = 2000_0000_00;
        byte c = (byte)a;

        System.out.println(cc);
    }

    public static void p5(){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("나뉨수를 입력하시오:");
            double dividend = scanner.nextDouble(); // 나뉨수 입력
            System.out.print("나눗수를 입력하시오:");
            double divisor = scanner.nextDouble(); // 나눗수 입력
            try {
                System.out.println(dividend+"를 "+ divisor + "로 나누면 몫은 " + dividend/divisor + "입니다."); scanner.close();
                break;
            }
            catch(ArithmeticException e) { // ArithmeticException 예외  
                System.out.println("0으로 나눌 수 없습니다! 다시 입력하세요");
            }
        }
    }

    public static void p61(){
        String ss[] = {"1", "2", "3"};
        String sss = null;
        try {
            try {
                try {
                    int b = 4;
                    System.out.println(ss[ss.length]);
            System.out.println("Bye Bye");
                } catch (Exception e){
                    System.out.println("CHECK");
                }
                finally {
                    System.out.println("HIHI");
                }
            } finally {
                System.out.println("HIHIHI");
            }
        } finally {
            System.out.println("HIHIHIHI");
        }
        System.out.println("HIHIHIHIHIHIHIHIHI");
    }

    public static void p7(){
        Scanner scanner = new Scanner(System.in);
        String a = scanner.nextLine();
        int b= scanner.nextInt();
        int d = scanner.nextInt();
        String c = scanner.nextLine();
        System.out.println(a+b+c);
        System.out.println(d);
        System.out.println("!!");
    }

    static void f(int x, int y) {System.out.println("111");}
    static void f(long x, int y) {System.out.println("222");}

    public static void p9(){
        for (int i = 0; i<3; i++){
            int a= 10;
            System.out.println(a);
        }
    }

    public static void p10(){
        char[][] chars = {{'1','2','3'},{'4','5','6'}};
        for (var cell: chars){
            for (char val: cell){
                System.out.println(val);
            }
        }
    }

    public static void p11(){
        int t1[] = {1,2,3};
        int t2[] = {4,5};
        int test[][] = {t1,t2};

        int[] v1 = {1,2,3};
        int[] v2 = {4,5};
        int[][] test2 = {v1,v2};
        System.out.println(Arrays.deepToString(test));
        System.out.println(Arrays.deepToString(test2));
    }

    public static void p12(){
//        String s;
//        System.out.println("s=" + s );
    }
    public static void main(String args[]){
        String a = new String("abc");
        String b = new String("abc");
        String c = a.replace("a", "f");
        String d = a.substring(1,2);
        String e = a.concat(b);
        System.out.println(a == b);
        System.out.println(a.equals(b));

        Person p1 = new Person();
        Person p2 = new Student();
        Student s = new Student();

        System.out.println(p1.name); // 사람
        p1.talk(); // 사람
        System.out.println(p2.name); // 사람
        p2.talk(); // 학생
        System.out.println(s.name); // 학생
        s.talk(); // 학생

    }
}
