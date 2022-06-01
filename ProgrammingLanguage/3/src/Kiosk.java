import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Kiosk {
    private Scanner scanner;
    private BranchManager branchManager;



    public Kiosk(){
        this.scanner = new Scanner(System.in);
        this.branchManager = new BranchManager();
    }
    public void start(){
        while(true){
            System.out.println("모드 선택: 1.관리자모드 2.사용자모드 3.프로그램종료");
            try {
                int action = scanner.nextInt();
                switch (action) {
                    case 1:
                        doAdminAction();
                        break;
                    case 2:
                        doUserAction();
                        break;
                    case 3:
                        throw new EndException();
                    default:
                        throw new CommandMismatchException();
                }
            } catch (CommandMismatchException e){
                System.out.println("존재하지 않는 명령입니다");
            } catch (InputMismatchException e){
                System.out.println("존재하지 않는 명령입니다");
                scanner.nextLine();
            } catch (EndException e){
                System.out.println("프로그램 종료");
                return;
            } catch (CustomException e){
                System.out.println(e.getMessage());
            } finally {

            }
        }
    }

    private void doAdminAction() throws StudyException{
        manageBranch();
    }

    private void manageBranch() throws StudyException, InputMismatchException{
        int action;
        System.out.println("작업 선택: 1.지점 추가 2.지점 삭제 3.지점 수정");
        action = scanner.nextInt();
        switch (action){
            case 1: {
                System.out.println("추가할 지점의 번호를 입력하세요 (1~6)");
                int branchId = scanner.nextInt();
                branchManager.create(branchId);
                break;
            } case 2: {
                System.out.println("삭제할 지점의 번호를 입력하세요 (1~6)");
                int branchId = scanner.nextInt();
                branchManager.delete(branchId);
                break;
            } case 3: {
                System.out.println("수정할 지점의 번호를 입력하세요 (1~6)");
                int branchId = scanner.nextInt();
                branchManager.modify(branchId);
                manageStudyArea(branchId);
                break;
            } default: {
                throw new CommandMismatchException();
            }
        }
    }

    private void manageStudyArea(int branchId) throws StudyException, InputMismatchException {
        int action;
        System.out.println("작업 선택: 1.스터디 공간 추가 2.스터디 공간 삭제 3.스터디 공간 수정");

        action = scanner.nextInt();
        switch (action) {
            case 1: {
                System.out.println("추가할 스터디 공간의 번호를 입력하세요 (1~5)");
                int studyAreaId = scanner.nextInt();
                System.out.println("스터디 공간의 허용 인원을 입력해 주세요 (1~10)");
                int capacity = scanner.nextInt();
                branchManager.addStudyArea(branchId, studyAreaId, capacity);
                break;
            } case 2: {
                System.out.println("삭제할 스터디 공간의 번호를 입력하세요 (1~5)");
                int studyAreaId = scanner.nextInt();
                branchManager.deleteStudyArea(branchId, studyAreaId);
                break;
            } case 3: {
                System.out.println("수정할 스터디 공간의 번호를 입력하세요 (1~5)");
                int studyAreaId = scanner.nextInt();
                System.out.println("수정할 스터디 공간의 허용 인원을 입력해주세요 (1~10)");
                int capacity = scanner.nextInt();
                branchManager.updateStudyAreaCapacity(branchId, studyAreaId, capacity);
                break;
            } default: {
                throw new CommandMismatchException();
            }
        }
    }

    private void printBranchesTable() throws CustomException{
        System.out.println("============지점목록============");
        boolean isAllEmpty = true;
        StudyAreaBranch[] studyAreaBranch = branchManager.getStudyAreaBranch();
        for(int i =1; i<=6; i++){
            if(studyAreaBranch[i] != null){
                System.out.println((i)+"번 지점");
                isAllEmpty = false;
            }
        }
        if(isAllEmpty) throw new CustomerException("지점이 하나도 존재하지 않습니다");
        System.out.println("============================");
    }

    private void printStudyAreaTable(int branchId) throws CustomException{
        System.out.println("============스터디 공간 목록============");
        boolean isAllEmpty = true;
        StudyAreaBranch studyAreaBranch = branchManager.getStudyAreaBranch()[branchId];
        if(studyAreaBranch == null){ throw new CustomException("해당 지점은 존재하지 않습니다.");}
        StudyArea[] studyAreas = studyAreaBranch.getStudyAreas();
        for(int i =1; i<=5; i++){
            if(studyAreas[i] != null){
                System.out.println((i)+"번 스터디 공간 " + "- 사용 가능 인원: "+ studyAreas[i].getCapacity());
                isAllEmpty = false;
            }
        }
        if(isAllEmpty) throw new CustomerException("스터디 공간이 존재하지 않습니다.");
        System.out.println("===================================");
    }

    private void lookUpBranches() throws CustomException, InputMismatchException{
        printBranchesTable();
        System.out.println("조회할 지점 번호를 입력하세요 (1~6)");
        int branchId = scanner.nextInt();
        printStudyAreaTable(branchId);
    }

    private void createReservation() throws CustomException, InputMismatchException{
        System.out.println("예약할 지점의 번호를 입력하세요 (1~6)");
        int branchId = scanner.nextInt();
        System.out.println("예약할 스터디 공간을 입력하세요 (1~5)");
        int studyAreaId = scanner.nextInt();
        System.out.println("예약 일자를 입력하세요");
        scanner.nextLine();
        String startAtStr = scanner.nextLine();
        System.out.println("시작 시간을 입력하세요 (8 ~ 22)");
        int startAtTime = scanner.nextInt();
        System.out.println("사용 시간을 입력하세요(이용할 시간 숫자로)");
        int duringHours = scanner.nextInt();
        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("YYMMdd");
        LocalDate ld = LocalDate.parse(startAtStr, DATEFORMATTER);
        LocalDateTime startAt = LocalDateTime.of(ld, LocalTime.of(startAtTime, 0));
        System.out.println(startAt);
        System.out.println("사용 인원을 입력해주세요");
        int customerCnt = scanner.nextInt();
        branchManager.createReservation(startAt, duringHours, customerCnt, branchId, studyAreaId);
    }

    private void lookUpMyReservations() throws  InputMismatchException{
        ArrayList<String> myReservations = branchManager.getMyReservationsStr();
        System.out.println("============지점목록============");
        if(myReservations.size() == 0){
            System.out.println("예약 내역이 존재하지 않습니다");
            return;
        }
        for(String str: myReservations){
            System.out.println(str);
        }
        System.out.println("=============================");
    }

    private void doUserAction() throws CustomException,InputMismatchException {
        System.out.println("사용자 ID를 입력하세요 (영문자, 숫자 조합으로 5~10 글자)");
        String id = scanner.nextLine();
        if(id.equals(""))
            id = scanner.nextLine();
        branchManager.setCurrentCustomer(id);

        System.out.println("작업 선택: 1.스터디공간 조회 2.스터디공간 예약 3.예약 조회 4.예약 수정");
        int action = scanner.nextInt();
        switch (action){
            case 1: {
                System.out.println("스터디 지점을 조회합니다.");
                lookUpBranches();
                break;
            } case 2: {
                System.out.println("스터디 공간 예약을 진행합니다.");
                createReservation();
                break;
            } case 3: {
                System.out.println("나의 예약을 조회합니다.(현재 시간 이후)");
                lookUpMyReservations();
                break;
            } case 4: {
                System.out.println("나의 예약을 수정합니다. (현재 시간 이후)");
            } default: {
                throw new CommandMismatchException();
            }
        }


//        System.out.println("스터디 공간 예약을 진행합니다...");
//        System.out.println("예약할 지점의 번호를 입력하세요 (숫자)");
//        printBranchesTable();
//        int branchId = scanner.nextInt();
//        printStudyAreaTable(branchId);
//        System.out.println("예약할 스터디 공간을 입력하세요 (숫자) System.out.println("스터디 공간 예약을 진행합니다...");
////        System.out.println("예약할 지점의 번호를 입력하세요 (숫자)");
////        printBranchesTable();
////        int branchId = scanner.nextInt();
////        printStudyAreaTable(branchId);
////        System.out.println("예약할 스터디 공간을 입력하세요 (숫자)");
////        int studyAreaId = scanner.nextInt();
////
////        System.out.println("예약 일자를 입력하세요");
////        scanner.nextLine();
////        String startAtStr = scanner.nextLine();");
//        int studyAreaId = scanner.nextInt();
//
//        System.out.println("예약 일자를 입력하세요");
//        scanner.nextLine();
//        String startAtStr = scanner.nextLine();
    }

    public static void main(String[] args){
        new Kiosk().start();
    }

}

class EndException extends Exception {}
class CommandMismatchException extends InputMismatchException {}