import java.util.InputMismatchException;
import java.util.Scanner;
public class Quiz0412 {
    public static void main(String[] args){
        ReserveSystem reserveSystem = new ReserveSystem();
        reserveSystem.start();
    }
}

// 좌석 - 타입, 예약자이름, 좌석 번호, 예약 여부
class Seat{
    private int type; // S: 1, A: 2, B: 3
    private int id;
    private boolean isReserved;
    private String reservingClientName;

    public Seat(int type, int id) {
        this.type = type;
        this.id = id;
        this.isReserved = false;
        this.reservingClientName = "";
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean isReserved() {
        return isReserved;
    }
    public String getReservingClientName() {
        return reservingClientName;
    }
    public void reserve(String reservingClientName){
        this.isReserved = true;
        this.reservingClientName = reservingClientName;
    }
    public void cancelReservation(){
        this.isReserved = false;
        this.reservingClientName = null;
    }
}

// 예약 시스템 - static +start +입력 -예약, -조회, -취소, -끝내기
// 없는 이름, 없는 번호, 없는 메뉴, 잘못된 취소 예외처리 (사용자 재시도)
class ReserveSystem {
    private static Scanner scanner = new Scanner(System.in);
    private Seat[][] seats;

    public ReserveSystem() {
        seats = new Seat[4][11];
        for (int typeI = 1; typeI <= 3; typeI++) {
            for (int idI = 1; idI <= 10; idI++) {
                seats[typeI][idI] = new Seat(typeI, idI);
            }
        }
    }

    private Seat getSeat(int seatType, int seatId) throws Exception {
        if(1<=seatType && seatType<=3 && 1<=seatId && seatId <=10){
            return this.seats[seatType][seatId];
        }
        throw new Exception("존재하지 않는 좌석에 대한 접근입니다.");
    }
    public void start(){
        System.out.println("명품콘서트홀 예약 시스템입니다.");
        while(true){
            System.out.print("예약:1, 조회:2, 취소:3, 끝내기:4>>");
            try {
                int input = scanner.nextInt();
                if(input == 4){
                    break;
                }
                command(input);
            } catch (InputMismatchException e){
                scanner.nextLine();
                System.out.println("잘못된 입력입니다.");
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void command(int command) throws Exception{
        switch(command){
            case 1:
                reserve();
                break;
            case 2:
                lookUpAll();
                break;
            case 3:
                cancel();
                break;
            default:
                throw new Exception("잘못된 명령 코드입니다.");
        }
    }
    private void reserve() throws Exception{
        System.out.print("좌석구분 S(1), A(2), B(3)>>");
        int seatType = scanner.nextInt();
        lookUp(seatType);
        System.out.print("이름>>");
        String reservingClientName = scanner.next();
        System.out.print("번호>>");
        int seatId = scanner.nextInt();
        getSeat(seatType, seatId).reserve(reservingClientName);
    }
    private String seatTypeToString(int seatType) throws Exception {
        String seatTypeStr = "";
        switch(seatType){
            case 1:
                seatTypeStr = "S";
                break;
            case 2:
                seatTypeStr = "A";
                break;
            case 3:
                seatTypeStr = "B";
                break;
            default:
                throw new Exception("존재하지 않는 좌석등급입니다.");
        }
        return seatTypeStr;
    }

    private void lookUp(int seatType) throws Exception{
        String seatTypeStr = seatTypeToString(seatType);
        String msg = seatTypeStr + ">>";
        for(int i = 1; i<= 10; i++){
            Seat seat = getSeat(seatType, i);
            if(seat.isReserved()){
                msg += " " + seat.getReservingClientName();
            } else {
                msg += " " + "---";
            }
        }
        System.out.println(msg);
    }
    private void lookUpAll(){
        try {
            for (int typeI = 1; typeI <= 3; typeI++) {
                lookUp(typeI);
            }
        } catch (Exception e) {}
        System.out.println("<<<조회를 완료하였습니다.>>>");
    }
    private void cancel() throws Exception{
        System.out.print("좌석 S:1, A:2, B:3>>");
        int seatType = scanner.nextInt();
        lookUp(seatType);
        System.out.print("이름>>");
        String reservingClientName = scanner.next();
        for(int i = 1; i<=10; i++) {
            Seat seatI = getSeat(seatType, i);
            if(seatI.getReservingClientName().equals(reservingClientName)) {
                seatI.cancelReservation();
                return;
            }
        }
        throw new Exception("해당하는 예약자가 없습니다.");
    }
}

