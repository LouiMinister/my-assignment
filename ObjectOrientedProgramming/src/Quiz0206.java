import java.util.Scanner;

public class Quiz0206 {

    public static void main(String[] args){
        System.out.print("1~99 사이의 정수를 입력하시오>>");
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();

        int cnt = 0; // 3,6,9 가 들어간 횟수
        if((num/10) == 3 || (num/10) == 6 || (num/10) == 9){ // 10의 자리에서 들어간 경우
            cnt +=1;
        }
        if((num%10) == 3 || (num%10) == 6 || (num%10) == 9) { // 1의 자리에서 들어간 경우
            cnt +=1;
        }

        if(cnt == 0){ // 3,6,9 가 없을 때
            System.out.println(num);
        } else if (cnt == 1){ // 3,6,9 가 하나일 때
            System.out.println("박수짝");
        } else if (cnt == 2){ // 3,6,9 가 두개일 때
            System.out.println("박수짝짝");
        }
        scanner.close();
    }
}
