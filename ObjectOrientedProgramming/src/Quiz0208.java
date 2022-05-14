import java.util.Scanner;

public class Quiz0208 {

    public static boolean inRect(int x, int y, int rectx1, int recty1, int rectx2, int recty2){
        if ((x>= rectx1 && x <= rectx2) && (y>=recty1 && y<=recty2)){
            return true;
        } else return false;
    }

    public static boolean isRectCollide(int rect1x1, int rect1y1, int rect1x2, int rect1y2,
                                         int rect2x1, int rect2y1, int rect2x2, int rect2y2){
        return inRect(rect1x1, rect1y1, rect2x1, rect2y1, rect2x2, rect2y2) || // 4개의 점 중 하나라도 겹치면 충
                inRect(rect1x1, rect1y2, rect2x1, rect2y1, rect2x2, rect2y2) ||
                inRect(rect1x2, rect1y1, rect2x1, rect2y1, rect2x2, rect2y2) ||
                inRect(rect1x2, rect1y2, rect2x1, rect2y1, rect2x2, rect2y2);
    }

    public static void main(String[] args){
        int x1,y1,x2,y2; // 전제조건: (x1,y1)은 직사각형의 왼쪽 상단 모서리, (x2,y2)는 직사각형의 오른쪽 하단 모서리임.
        Scanner scanner = new Scanner(System.in);

        System.out.print("직사각형을 구성하는 첫번째 점을 x y 형식으로 입력하세요.");
        x1 = scanner.nextInt();
        y1 = scanner.nextInt();

        System.out.print("직사각형을 구성하는 두번째 점을 x y 형식으로 입력하세요.");
        x2 = scanner.nextInt();
        y2 = scanner.nextInt();

        if(isRectCollide(x1,y1,x2,y2,100,100,200,200)){
            System.out.println("두 직사각형은 충돌합니다.");
        } else {
            System.out.println("두 직사각형은 충돌하지 않습니다.");
        }
        scanner.close();
    }
}
