import java.util.Scanner;

public class Quiz0306 {
    public static void main(String[] args){ // 문제랑 결과가 다르긴함. 환산할 돈의 종류가 높은것이 우선적으로 많은 개수를 구한다고 정하고 품
        Scanner scanner = new Scanner(System.in);
        int[] unit = {50000,10000,1000,500,100,50,10,1};

        System.out.print("금액을 입력하시오>>");
        int n = scanner.nextInt();
        for(int u:unit){
            int cnt = n/u;
            n -= u * cnt;
            System.out.println(u+"원 짜리 : "+cnt+"개");
        }
        scanner.close();
    }
}
