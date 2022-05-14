import java.util.Scanner;

public class tmp {

    public static void main(String[] as){
        int ary[][] = {{1,2},{1,2,3}};
        for(int[] i:ary){
            System.out.println("길이:"+i.length);
            for (int j:i){
                System.out.println(j);
            }
        }

    }
}
