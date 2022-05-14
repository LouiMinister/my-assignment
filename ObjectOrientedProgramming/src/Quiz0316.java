import java.util.Scanner;

public class Quiz0316 {
    static String str[] = {"가위", "바위", "보"};

    public static int handStrToIdx(String hand){
        for(int i = 0; i<=2; i++){
            if(hand.equals(str[i])){
                return i;
            }
        }
        return -1;
    }

    public static String generateResultStr(int playerHandIdx, int cpuHandIdx){
        int res = (((playerHandIdx+3) - cpuHandIdx))%3;
        switch (res){
            case 0:
                return "비겼습니다.";
            case 1:
                return "사용자가 이겼습니다.";
            case 2:
                return "컴퓨터가 이겼습니다.";
        }
        return "에러";
    }

    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);

        System.out.println("컴퓨터와 가위 바위 보 게임을 합니다.");
        while(true){
            System.out.print("가위 바위 보!>>");
            String input = scanner.nextLine();
            if(input.equals("그만")){
                System.out.print("게임을 종료합니다...");
                break;
            }
            boolean wrongInputFlag = true;
            for(var s:str){
                if(input.equals(s)){
                    wrongInputFlag = false;
                }
            }
            if(wrongInputFlag){
                System.out.println("입력값이 올바르지 않습니다. 다시 입력해주세요.");
                continue;
            }
            String cpu = str[(int)(Math.random()*3)];
            System.out.print("사용자 = "+input+" , 컴퓨터 = "+cpu+ ", ");
            String resultStr = generateResultStr(handStrToIdx(input), handStrToIdx(cpu));
            System.out.println(resultStr);
        }
    }
}
