import java.util.Scanner;

public class Quiz0204 {

    public static void main(String[] args){
        int min,mid,max;
        Scanner scanner = new Scanner(System.in);

        System.out.print("정수 3개 입력>>");
        min = scanner.nextInt();
        mid = scanner.nextInt();
        max = scanner.nextInt();

        if(min > mid) { // min mid sort
            int tmp = min;
            min = mid;
            mid = tmp;
        }
        if(mid > max){ // mid max sort
            int tmp = mid;
            mid = max;
            max = tmp;
        }
        if(min > mid){ // min mid sort
            int tmp = min;
            min = mid;
            mid = tmp;
        }
        System.out.print("중간 값은 " + mid);
        scanner.close();
    }
}
