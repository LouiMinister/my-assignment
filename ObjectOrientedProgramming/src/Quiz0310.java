public class Quiz0310 {

    public static void main(String args[]){

        int ary[] = new int[10];
        boolean overlapFlag;
        int i = 0;
        while(i < 10){
            ary[i]=-1;
            overlapFlag = false;
            int rand = (int)(Math.random()*15);
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
        int printAry[] = new int[16];
        for(var j:ary){
            printAry[j] = (int)(Math.random()*10+1);
        }
        for(int j = 0; j<16; j++){
            System.out.printf("%4d", printAry[j]);
            if(j%4 == 3){
                System.out.print('\n');
            }
        }
    }
}
