import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class BranchManager {
    private StudyAreaBranch studyAreaBranch[];
    private String currentCustomerId;
    private ArrayList<Reservation> reservations;

    public String studyAreaBranchToString() {
        return Arrays.toString(studyAreaBranch);
    }

    public BranchManager() {
        this.studyAreaBranch = new StudyAreaBranch[7];
        this.reservations = new ArrayList<Reservation>();
    }

    public StudyAreaBranch[] getStudyAreaBranch() {
        return studyAreaBranch;
    }

    private void checkBranchInRange(int id) throws StudyException {
        if (id < 0 || id > 6) {
            throw new StudyException("유효하지 않은 지점 번호입니다.");
        }
    }

    private void checkStudyAreaInRange(int id) throws StudyException {
        if(id <0 || id > 5){
            throw new StudyException("유효하지 않은 스터디공간 번호입니다");
        }
    }

    private void checkStudyAreaCapacityInRange(int capacity) throws StudyException {
        if(capacity <0 || capacity > 10){
            throw new StudyException("유효하지 않은 스터디공간 허용인원 입니다.");
        }
    }

    private void checkStudyAreaBranchExist(int id) throws StudyException {
        if(studyAreaBranch[id] == null){
            throw new StudyException("해당 지점이 존재하지 않습니다.");
        }
    }

    private void checkStudyAreaBranchAlreadyExist(int id) throws StudyException {
        if(studyAreaBranch[id] != null){
            throw new StudyException("이미 해당 지점이 존재합니다.");
        }
    }

    private void checkReservationAlreadyExist(int branchId, int areaId) throws ReservationException{
        for(Reservation rsv: reservations){
            if(rsv.getStudyAreaBranchId() == branchId &&
                rsv.getStudyAreaId() == areaId &&
                rsv.getStartAt().isAfter(LocalDateTime.now())
            ){
                throw new ReservationException("해당 스터디 지점에 유효한 예약이 존재합니다.");
            }
        }
    }

    private void checkReservationAlreadyExistInBranch(int branchId) throws ReservationException{
        for(Reservation rsv: reservations){
            if(rsv.getStudyAreaBranchId() == branchId &&
                    rsv.getStartAt().isAfter(LocalDateTime.now())
            )
            {
                throw new ReservationException("해당 스터디 지점에 유효한 예약이 존재합니다.");
            }
        }
    }

    private void checkValidatedIdFormat(String id) throws CustomerException{
        if(id.length() < 5 ||
                id.length() >10 ||
                !Pattern.matches("^[a-zA-Z0-9]*$",id))
            throw new CustomerException("사용자 ID의 형식이 올바르지 않습니다");
    }

    private void checkNewReservationDateInRange(LocalDateTime startAt, int hours) throws ReservationException{
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1).with(LocalTime.of(0,0));
        if(!startAt.isAfter(tomorrow)){
            throw new ReservationException("오늘 이후 날짜만 예약할 수 있습니다");
        }
        LocalTime startTime =  startAt.toLocalTime();
        LocalTime endTime = startAt.toLocalTime().plusHours(hours);
        if (startTime.isBefore(LocalTime.of(8,0)) || //8시 이전이거나
            endTime.isAfter(LocalTime.of(22, 0))){ // 22시 이후인 경우
            throw new ReservationException("오전 8시부터 밤 10시까지 예약 가능합니다");
        }
    }

    private void checkNewReservationOverlap(LocalDateTime startAt, int hours, int branchId, int areaId) throws ReservationException{

        LocalDateTime endTime = startAt.plusHours(hours);
        for(Reservation rsv :reservations){
            if(rsv.getStudyAreaId() == areaId && rsv.getStudyAreaBranchId() == branchId){
                if(!(endTime.isBefore(rsv.getStartAt()) ||
                        startAt.isAfter(rsv.getStartAt().plusHours(rsv.getHours())))
                ){
                    throw new ReservationException("시간이 겹치는 예약이 존재합니다");
                }
            }
        }
    }

    public void create(int id) throws StudyException{
        checkBranchInRange(id);
        checkStudyAreaBranchAlreadyExist(id);
        studyAreaBranch[id] = new StudyAreaBranch(id);
    }

    public void delete(int id) throws CustomException{
        checkBranchInRange(id);
        checkStudyAreaBranchExist(id);
        checkReservationAlreadyExistInBranch(id);
        studyAreaBranch[id] = null;
    }

    public void modify(int id) throws CustomException{
        checkBranchInRange(id);
        checkStudyAreaBranchExist(id);
        checkReservationAlreadyExistInBranch(id);
    }

    public void addStudyArea(int branchId, int studyAreaId, int capacity) throws StudyException{
        checkBranchInRange(branchId);
        checkStudyAreaInRange(studyAreaId);
        checkStudyAreaCapacityInRange(capacity);
        checkStudyAreaBranchExist(branchId);

        StudyAreaBranch branchSelected = studyAreaBranch[branchId];
        if(branchSelected.getStudyAreas()[studyAreaId] != null)
            throw new StudyException("이미 스터디 공간이 존재합니다.");
        branchSelected.getStudyAreas()[studyAreaId] = new StudyArea(studyAreaId, capacity);
    }

    public void deleteStudyArea(int branchId, int studyAreaId) throws CustomException{
        checkBranchInRange(branchId);
        checkStudyAreaInRange(studyAreaId);
        checkStudyAreaBranchExist(branchId);
        checkReservationAlreadyExist(branchId, studyAreaId);

        StudyAreaBranch branchSelected = studyAreaBranch[branchId];
        if(branchSelected.getStudyAreas()[studyAreaId] == null)
            throw new StudyException("해당 스터디 공간이 존재하지 않습니다.");
        branchSelected.getStudyAreas()[studyAreaId] = null;
    }

    public void updateStudyAreaCapacity(int branchId, int studyAreaId, int capacity) throws StudyException{
        checkBranchInRange(branchId);
        checkStudyAreaInRange(studyAreaId);
        checkStudyAreaBranchExist(branchId);
        checkStudyAreaInRange(capacity);

        StudyAreaBranch branchSelected = studyAreaBranch[branchId];
        if(branchSelected.getStudyAreas()[studyAreaId] == null)
            throw new StudyException("해당 스터디 공간이 존재하지 않습니다.");
        StudyArea studyArea = branchSelected.getStudyAreas()[studyAreaId];
        studyArea.setCapacity(capacity);
    }

    public void setCurrentCustomer(String id) throws CustomerException {
        checkValidatedIdFormat(id);
        this.currentCustomerId = id;
    }

    public void createReservation(LocalDateTime startAt, int hours, int customerCnt, int studyAreaBranchId, int studyAreaId) throws ReservationException, StudyException{
        checkNewReservationDateInRange(startAt, hours);
        checkStudyAreaBranchExist(studyAreaBranchId);
        checkNewReservationOverlap(startAt, hours, studyAreaBranchId, studyAreaId);

        StudyAreaBranch branchSelected = studyAreaBranch[studyAreaBranchId];
        if(branchSelected.getStudyAreas()[studyAreaId] == null)
            throw new ReservationException("해당 스터디 공간이 존재하지 않습니다.");
        if(customerCnt > studyAreaBranch[studyAreaBranchId].getStudyAreas()[studyAreaId].getCapacity())
            throw new ReservationException("해당 스터디 공간의 가용인원을 초과하였습니다");
        reservations.add(new Reservation(currentCustomerId,studyAreaBranchId, studyAreaId, customerCnt, startAt, hours));

    }

    public ArrayList<String> getMyReservationsStr(){
        ArrayList<String> myReservations = new ArrayList<String>();
        for(Reservation rsv: reservations){
            if(rsv.getCustomerId().equals(currentCustomerId) &&
                rsv.getStartAt().isAfter(LocalDateTime.now()))
                myReservations.add(rsv.toString());
        }
        return myReservations;
    }

    private Reservation getReservation(int hashCode){
        for(Reservation rsv: reservations){
            if(rsv.hashCode() == hashCode){
                return rsv;

            }
        }
        return null;
    }

    public void modifyMyReservation(int hashCode, int customerCnt) throws ReservationException{
        Reservation rsv =  getReservation(hashCode);
        if(rsv == null)
            throw new ReservationException("코드에 해당하는 예약이 존재하지 않습니다");
        if(!rsv.getCustomerId().equals(currentCustomerId))
            throw new ReservationException("현재 로그인한 회원의 예약이 아닙니다.");
        if(customerCnt > studyAreaBranch[rsv.getStudyAreaBranchId()].getStudyAreas()[rsv.getStudyAreaId()].getCapacity())
            throw new ReservationException("해당 스터디 공간의 가용인원을 초과하였습니다");

        rsv.setCustomerCnt(customerCnt);
    }

    public void deleteMyReservation(int hashCode) throws ReservationException {
        Reservation rsv =  getReservation(hashCode);
        if(rsv == null)
            throw new ReservationException("코드에 해당하는 예약이 존재하지 않습니다");
        if(!rsv.getCustomerId().equals(currentCustomerId))
            throw new ReservationException("현재 로그인한 회원의 예약이 아닙니다.");
        reservations.remove(rsv);
    }
}

class CustomException extends Exception {
    CustomException(String msg) { super(msg);}
}

class StudyException extends CustomException{
    StudyException(String msg){
        super(msg);
    }
}

class CustomerException extends CustomException {
    public CustomerException(String message) {
        super(message);
    }
}

class ReservationException extends CustomException {
    public ReservationException(String message){
        super(message);
    }
}