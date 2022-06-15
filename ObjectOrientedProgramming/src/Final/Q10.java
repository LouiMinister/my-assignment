
package Final;
import java.util.ArrayList;
import java.util.List;

public class Q10 {
    public static <E> void m0(List<E> L, E e){

    }
    public static <E> void m1(List<?> L, E e){

    }
    public static <E> void m2(List<? extends E> L, E e){

    }
    public static <E> void m3(List<? super E> L, E e){

    }
    public static <E> void m4(List<E> L, Object e){

    }
    public static void main(String[] args){
//        m0(new ArrayList<String>(), new Object());
//        m1(new ArrayList<String>(), new Object());
//        m2(new ArrayList<String>(), new Object());
//        m3(new ArrayList<String>(), new Object());
        m4(new ArrayList<String>(), new Object());

    }
}
