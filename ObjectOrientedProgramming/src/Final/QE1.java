package Final;

public class QE1 {

    public static void main(String[] args) {
        String str1 = "Happy ";
        String str2 = str1;
        str2 += "New Year";
        str1 = str2.substring(6);
        System.out.println(str1 + str2);
    }
}
