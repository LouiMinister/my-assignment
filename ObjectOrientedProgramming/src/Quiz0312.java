public class Quiz0312 {

    public static void main(String[] args){
      int sum = 0;
      for(String arg: args){
          try{
              sum += Integer.parseInt(arg);
          } catch (Exception e) {}
      }
      System.out.println(sum);
    }
}
