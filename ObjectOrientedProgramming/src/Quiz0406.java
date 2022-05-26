import java.util.Scanner;

public class Quiz0406 {
    public static void main(String[] args){
        CircleManager.main(new String[0]);
    }
}

class Circle1{
    private double x, y;
    private int radius;
    public Circle1(double x, double y, int radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
    public int getRadius(){
        return this.radius;
    }
    public void show(){
        System.out.printf("가장 면적이 큰 원은 (%.1f,%.1f)%d\n", x, y, radius);
    }
}

class CircleManager {
    public static void main(String[] args){ // 같은 넓이인 경우 앞에있는 원 출력
        Scanner scanner = new Scanner(System.in);
        Circle1 c[] = new Circle1[3];
        for (int i = 0; i<3; i++){
            System.out.print("x, y, radius >>");
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();
            int radius = scanner.nextInt();
            c[i] = new Circle1(x, y, radius);
        }
        int max = 0;
        int maxIdx = 0;
        for(int i=0; i<c.length; i++){
            if(max < c[i].getRadius()){
                max = c[i].getRadius();
                maxIdx = i;
            }
        }
        c[maxIdx].show();
        scanner.close();
    }
}