import java.util.Scanner;

public class Quiz0410 {
    public static void main(String args[]){
        DicApp.start();
    }

}

class Dictionary1 {
    private static String [] kor = {"사랑", "아기", "돈", "미래", "희망"};
    private static String [] eng = {"love", "baby", "money", "future", "hope"};
    public static String kor2Eng(String word) {
        for(int i =0; i<kor.length; i++){
            if(kor[i].equals(word)) {
                return eng[i];
            }
        }
        return null;
    }
}

class DicApp {
    public static void start(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("한영 단어 검색 프로그램입니다.");
        while(true){
            System.out.print("한글 단어?");
            String korForFind = scanner.next();
            if(korForFind.equals("그만")){
                break;
            }
            String engFound = Dictionary1.kor2Eng(korForFind);
            if(engFound == null){
                System.out.println(korForFind + "는 저의 사전에 없습니다.");
            } else {
                System.out.println(korForFind+"은 "+engFound);
            }
        }
        scanner.close();
    }
}
