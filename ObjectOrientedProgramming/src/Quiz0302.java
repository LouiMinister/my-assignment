public class Quiz0302 {

    public static void main(String args[]){
        int n [][] = {{1}, {1,2,3}, {1}, {1,2,3,4}, {1,2}};
        for(int[] ary: n){
            for (int v:ary){
                System.out.print(v + " ");
            }
            System.out.print('\n');
        }
    }
}
