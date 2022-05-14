public class Person {
    String name;

    public Person(){
        this.name ="사람";
        System.out.println("Person 생성자 호출");
    }

    public void talk(){
        System.out.println(name +" Person talk");
    }
}
