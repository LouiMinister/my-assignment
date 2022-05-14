import java.util.Scanner;

public class Quiz0314 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String course[] = { "Java", "C++", "HTML5", "컴퓨터구조", "안드로이드"};
        int score[] = {95,88,76,62,55};

        while(true){
            System.out.print("과목 이름>>");
            String inputSub = scanner.nextLine();
            if(inputSub.equals("그만")){
                break;
            }
            boolean foundFlag = false;
            for(int i = 0; i < course.length; i++){
                if(inputSub.equals(course[i])) {
                    System.out.println(inputSub + "의 점수는 " + score[i]);
                    foundFlag = true;
                    break;
                }
            }
            if(!foundFlag){
                System.out.println("없는 과목입니다");
            }
        }
    }
}
