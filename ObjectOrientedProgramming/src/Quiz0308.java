import java.util.Scanner;

public class Quiz0308 {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("정수 몇개?");
        int n = scanner.nextInt();

        int ary[] = new int[100];
        boolean overlapFlag;
        int i = 0;
        while(i < n){
            overlapFlag = false;
            int rand = (int)(Math.random()*100+1);
//            System.out.println(i+ " "+ n + " "+ rand);
            for(int v:ary){
                if(v==rand){
                    overlapFlag = true;
                    break;
                }
            }
            if(overlapFlag == true){
                continue;
            } else {
                ary[i] = rand;
                i++;
            }
        }
        for(int j = 0 ; j<n; j++){
            System.out.printf("%d ",ary[j]);
            if(j%10 == 9){
                System.out.print('\n');
            }
        }
    }
}
