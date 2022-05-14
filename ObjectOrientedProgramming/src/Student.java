public class Student extends Person{
    String name;
    public Student(){
        name ="학생";
    }

    public void talk(){
        System.out.println(name+": Student talk");
    }
    public void bite(){
        System.out.println("bite!!");
    }
}
