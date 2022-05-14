import java.util.Scanner;

public class Quiz021202 {

    public static String dFormat(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        double numA, numB;
        String operator;

        System.out.print("연산>>");
        numA = scanner.nextDouble();
        operator = scanner.next();
        numB = scanner.nextDouble();

        String message = dFormat(numA)+operator+dFormat(numB)+"의 계산 결과는 ";
        switch(operator) {
            case "+":
                System.out.println(message + dFormat(numA + numB));
                break;
            case "-":
                System.out.println(message + dFormat(numA - numB));
                break;
            case "*":
                System.out.println(message + dFormat(numA * numB));
                break;
            case "/":
                if (numB != 0) {
                    System.out.println(message + dFormat(numA / numB));
                } else {
                    System.out.println("0으로 나눌 수 없습니다.");
                }
                break;
            default:
                System.out.println("연산자의 입력이 잘못되었습니다.");
        }
        scanner.close();
    }
}
