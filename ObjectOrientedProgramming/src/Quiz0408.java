import java.util.Scanner;

public class Quiz0408 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("인원수>>");
        int phonesCnt = scanner.nextInt();
        PhoneBook phoneBook = new PhoneBook(phonesCnt);
        for (int i = 0 ; i < phonesCnt; i++){
            System.out.print("이름과 전화번호(이름과 번호는 빈 칸없이 입력)>>");
            String name = scanner.next();
            String tel = scanner.next();
            phoneBook.addPhone(name, tel);
        }
        System.out.println("저장되었습니다...");
        while(true){
            System.out.print("검색할 이름>>");
            String nameForFind = scanner.next();
            if(nameForFind.equals("그만")){
                break;
            }
            phoneBook.find(nameForFind);
        }

    }
}

class Phone {
    private String name;
    private String tel;

    public String getName(){
        return this.name;
    }

    public String getTel(){
        return this.tel;
    }

    public Phone(String name, String tel){
        this.name = name;
        this.tel = tel;
    }

    public void show() {
        System.out.println(this.name + "의 번호는 " + tel + " 입니다.");
    }
}

class PhoneBook {
    private Phone[] phones;
    private int lastIdx = 0;
    private int cnt;

    public PhoneBook(int cnt){
        this.cnt = cnt;
        this.phones = new Phone[cnt];
    }

    public void addPhone(String name, String tel){
        this.phones[lastIdx++] = new Phone(name, tel);
    }

    public void find(String name){
        for(int i = 0; i< cnt; i++){
            if(name.equals(phones[i].getName())){
                phones[i].show();
                return;
            }
        }
        System.out.println(name+" 이 없습니다.");
    }
}