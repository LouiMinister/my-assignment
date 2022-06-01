import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation {
    private String customerId; // 영문자 및 숫자 조합 5~10글자
    private int studyAreaBranchId;
    private int studyAreaId;
    private int customerCnt; // 사용 인원
    private LocalDateTime startAt; // YYMMDD
    private int hours;

    public Reservation(String customerId, int studyAreaBranchId, int studyAreaId, int customerCnt, LocalDateTime startAt, int hours){
        this.customerId = customerId;
        this.studyAreaBranchId = studyAreaBranchId;
        this.studyAreaId = studyAreaId;
        this.customerCnt = customerCnt;
        this.startAt = startAt;
        this.hours = hours;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public int getHours() {
        return hours;
    }

    public int getCustomerCnt() {
        return customerCnt;
    }

    public int getStudyAreaBranchId() {
        return studyAreaBranchId;
    }

    public int getStudyAreaId() {
        return studyAreaId;
    }

    public void setCustomerCnt(int customerCnt) {
        this.customerCnt = customerCnt;
    }

    @Override
    public String toString() {
        return "예약고객 아이디: " + customerId  +
                ", 지점 번호: " + studyAreaBranchId +
                ", 스터디공간 번호: " + studyAreaId +
                ", 사용 인원: " + customerCnt +
                ", 예약 시간: " + startAt +
                ", 사용 기간: " + hours + "시간" +
                ", 예약 코드: " + hashCode();
    }

    @Override
    public int hashCode() {
        return Math.abs(Objects.hash(customerId, studyAreaBranchId, studyAreaId, startAt.toString())) % 1000000;
    }
}
