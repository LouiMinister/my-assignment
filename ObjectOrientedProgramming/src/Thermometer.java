public class Thermometer{
    int i = 0;
    Thermometer increase(){
        i++;
        return this;
    }
    void show(){
        System.out.println(i);
    }
    public static void main(String[] args){
        Thermometer tt = new Thermometer();
        tt.increase().increase().increase().show();
    }
}