import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
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
        loadBranches();
        loadReservations();
    }

    public StudyAreaBranch[] getStudyAreaBranch() {
        return studyAreaBranch;
    }

    public void saveBranches(){
        String filePath = "./branches.txt";
        File file = new File(filePath);
        try {
            if(!file.exists())
                file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(int i = 1; i<=6; i++){
                if(studyAreaBranch[i] == null){
                    bw.write("null");
                    bw.newLine();
                } else {
                    bw.write("StudyAreaBranch");
                    bw.newLine();
                    StudyArea[] studyAreas = studyAreaBranch[i].getStudyAreas();
                    for(int j = 1; j<=5; j++){
                        if(studyAreas[j] == null){
                            bw.write("null");
                            bw.newLine();
                        } else {
                            bw.write(studyAreas[j].getId() + " " +
                                    studyAreas[j].getCapacity());
                            bw.newLine();
                        }
                    }
                }
            }
            bw.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void loadBranches(){
        String filePath = "./branches.txt";
        File file = new File(filePath);

        try {
            if(!file.exists())
                return;
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            for(int i = 1; i<=6; i++){
                line = br.readLine();
                if(line.equals("StudyAreaBranch")){
                    studyAreaBranch[i] = new StudyAreaBranch(i);
                    StudyArea[] studyAreas = studyAreaBranch[i].getStudyAreas();
                    for(int j = 1; j<=5; j++){
                        line = br.readLine();
                        if(!line.equals("null")){
                            StringTokenizer st = new StringTokenizer(line);
                            studyAreas[j] =  new StudyArea(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
                        }
                    }
                }
            }
            br.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void saveReservations(){
        String filePath = "./reservations.txt";
        File file = new File(filePath);
        try {
            if(!file.exists())
                file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(Reservation rsv: reservations){
                bw.write(rsv.getCustomerId() +" "+
                        rsv.getStudyAreaBranchId() + " "+
                        rsv.getStudyAreaId() + " "+
                        rsv.getCustomerCnt() + " "+
                        rsv.getStartAt().toString() + " "+
                        rsv.getHours());
                bw.newLine();
            }
            bw.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void loadReservations(){
        String filePath = "./reservations.txt";
        File file = new File(filePath);
        try {
            if(!file.exists())
                return;
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while(true){
                line = br.readLine();
                if(line == null) break;
                StringTokenizer st = new StringTokenizer(line);
                Reservation rsv = new Reservation(
                    st.nextToken(),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    LocalDateTime.parse(st.nextToken()),
                    Integer.parseInt(st.nextToken())
                );
                reservations.add(rsv);
            }
            br.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void checkBranchInRange(int id) throws StudyException {
        if (id < 0 || id > 6) {
            throw new StudyException("???????????? ?????? ?????? ???????????????.");
        }
    }

    private void checkStudyAreaInRange(int id) throws StudyException {
        if(id <0 || id > 5){
            throw new StudyException("???????????? ?????? ??????????????? ???????????????");
        }
    }

    private void checkStudyAreaCapacityInRange(int capacity) throws StudyException {
        if(capacity <0 || capacity > 10){
            throw new StudyException("???????????? ?????? ??????????????? ???????????? ?????????.");
        }
    }

    private void checkStudyAreaBranchExist(int id) throws StudyException {
        if(studyAreaBranch[id] == null){
            throw new StudyException("?????? ????????? ???????????? ????????????.");
        }
    }

    private void checkStudyAreaBranchAlreadyExist(int id) throws StudyException {
        if(studyAreaBranch[id] != null){
            throw new StudyException("?????? ?????? ????????? ???????????????.");
        }
    }

    private void checkReservationAlreadyExist(int branchId, int areaId) throws ReservationException{
        for(Reservation rsv: reservations){
            if(rsv.getStudyAreaBranchId() == branchId &&
                rsv.getStudyAreaId() == areaId &&
                rsv.getStartAt().isAfter(LocalDateTime.now())
            ){
                throw new ReservationException("?????? ????????? ????????? ????????? ????????? ???????????????.");
            }
        }
    }

    private void checkReservationAlreadyExistInBranch(int branchId) throws ReservationException{
        for(Reservation rsv: reservations){
            if(rsv.getStudyAreaBranchId() == branchId &&
                    rsv.getStartAt().isAfter(LocalDateTime.now())
            )
            {
                throw new ReservationException("?????? ????????? ????????? ????????? ????????? ???????????????.");
            }
        }
    }

    private void checkValidatedIdFormat(String id) throws CustomerException{
        if(id.length() < 5 ||
                id.length() >10 ||
                !Pattern.matches("^[a-zA-Z0-9]*$",id))
            throw new CustomerException("????????? ID??? ????????? ???????????? ????????????");
    }

    private void checkNewReservationDateInRange(LocalDateTime startAt, int hours) throws ReservationException{
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1).with(LocalTime.of(0,0));
        if(!startAt.isAfter(tomorrow)){
            throw new ReservationException("?????? ?????? ????????? ????????? ??? ????????????");
        }
        LocalTime startTime =  startAt.toLocalTime();
        LocalTime endTime = startAt.toLocalTime().plusHours(hours);
        if (startTime.isBefore(LocalTime.of(8,0)) || //8??? ???????????????
            endTime.isAfter(LocalTime.of(22, 0))){ // 22??? ????????? ??????
            throw new ReservationException("?????? 8????????? ??? 10????????? ?????? ???????????????");
        }
    }

    private void checkNewReservationOverlap(LocalDateTime startAt, int hours, int branchId, int areaId) throws ReservationException{

        LocalDateTime endTime = startAt.plusHours(hours);
        for(Reservation rsv :reservations){
            if(rsv.getStudyAreaId() == areaId && rsv.getStudyAreaBranchId() == branchId){
                if(!(endTime.isBefore(rsv.getStartAt()) ||
                        startAt.isAfter(rsv.getStartAt().plusHours(rsv.getHours())))
                ){
                    throw new ReservationException("????????? ????????? ????????? ???????????????");
                }
            }
        }
    }

    public void create(int id) throws StudyException{
        checkBranchInRange(id);
        checkStudyAreaBranchAlreadyExist(id);
        studyAreaBranch[id] = new StudyAreaBranch(id);
        saveBranches();
    }

    public void delete(int id) throws CustomException{
        checkBranchInRange(id);
        checkStudyAreaBranchExist(id);
        checkReservationAlreadyExistInBranch(id);
        studyAreaBranch[id] = null;
        saveBranches();
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
            throw new StudyException("?????? ????????? ????????? ???????????????.");
        branchSelected.getStudyAreas()[studyAreaId] = new StudyArea(studyAreaId, capacity);
        saveBranches();
    }

    public void deleteStudyArea(int branchId, int studyAreaId) throws CustomException{
        checkBranchInRange(branchId);
        checkStudyAreaInRange(studyAreaId);
        checkStudyAreaBranchExist(branchId);
        checkReservationAlreadyExist(branchId, studyAreaId);

        StudyAreaBranch branchSelected = studyAreaBranch[branchId];
        if(branchSelected.getStudyAreas()[studyAreaId] == null)
            throw new StudyException("?????? ????????? ????????? ???????????? ????????????.");
        branchSelected.getStudyAreas()[studyAreaId] = null;
        saveBranches();
    }

    public void updateStudyAreaCapacity(int branchId, int studyAreaId, int capacity) throws StudyException{
        checkBranchInRange(branchId);
        checkStudyAreaInRange(studyAreaId);
        checkStudyAreaBranchExist(branchId);
        checkStudyAreaInRange(capacity);

        StudyAreaBranch branchSelected = studyAreaBranch[branchId];
        if(branchSelected.getStudyAreas()[studyAreaId] == null)
            throw new StudyException("?????? ????????? ????????? ???????????? ????????????.");
        StudyArea studyArea = branchSelected.getStudyAreas()[studyAreaId];
        studyArea.setCapacity(capacity);
        saveBranches();
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
            throw new ReservationException("?????? ????????? ????????? ???????????? ????????????.");
        if(customerCnt > studyAreaBranch[studyAreaBranchId].getStudyAreas()[studyAreaId].getCapacity())
            throw new ReservationException("?????? ????????? ????????? ??????????????? ?????????????????????");
        reservations.add(new Reservation(currentCustomerId,studyAreaBranchId, studyAreaId, customerCnt, startAt, hours));
        saveBranches();
        saveReservations();
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
            throw new ReservationException("????????? ???????????? ????????? ???????????? ????????????");
        if(!rsv.getCustomerId().equals(currentCustomerId))
            throw new ReservationException("?????? ???????????? ????????? ????????? ????????????.");
        if(customerCnt > studyAreaBranch[rsv.getStudyAreaBranchId()].getStudyAreas()[rsv.getStudyAreaId()].getCapacity())
            throw new ReservationException("?????? ????????? ????????? ??????????????? ?????????????????????");

        rsv.setCustomerCnt(customerCnt);
        saveBranches();
        saveReservations();
    }

    public void deleteMyReservation(int hashCode) throws ReservationException {
        Reservation rsv =  getReservation(hashCode);
        if(rsv == null)
            throw new ReservationException("????????? ???????????? ????????? ???????????? ????????????");
        if(!rsv.getCustomerId().equals(currentCustomerId))
            throw new ReservationException("?????? ???????????? ????????? ????????? ????????????.");
        reservations.remove(rsv);
        saveBranches();
        saveReservations();
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