import java.util.Scanner;

public class Quiz0210 {

    public static void main(String args[]){
        int x1,y1,r1,x2,y2,r2;
        Scanner scanner = new Scanner(System.in);
        System.out.print("첫번째 원의 중심과 반지름 입력>>");
        x1 = scanner.nextInt();
        y1 = scanner.nextInt();
        r1 = scanner.nextInt();
        System.out.print("두번째 원의 중심과 반지름 입력>>");
        x2 = scanner.nextInt();
        y2 = scanner.nextInt();
        r2 = scanner.nextInt();

        double distanceOfTwoDots = Math.sqrt((x1-x2) * (x1-x2) + (y1-y2) * (y1-y2));
        if(distanceOfTwoDots > r1+r2) {
            System.out.println("두 원은 만나지 않는다.");
        } else if (distanceOfTwoDots == r1+r2){
            System.out.println("두 원은 한 점에서 외접한다");
        } else {
            System.out.println("두 원은 서로 겹친다.");
        }
        scanner.close();
    }
}
