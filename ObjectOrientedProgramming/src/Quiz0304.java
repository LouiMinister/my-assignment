import java.util.Scanner;

public class Quiz0304 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        char c = scanner.nextLine().charAt(0);
        for (; c >= 'a'; c--){
            for (char i = 'a'; i <= c; i++){
                System.out.print(i);
            }
            System.out.print('\n');
        }
    }
}
