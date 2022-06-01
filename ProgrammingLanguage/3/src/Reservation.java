import java.time.LocalDateTime;

public class Reservation {
    private String customerId; // 영문자 및 숫자 조합 5~10글자
    private int studyAreaBranchId;
    private int studyAreaId;
    private int customerCnt; // 사용 인원
    private LocalDateTime startAt; // YYMMDD
    private int hours;

    public Reservation(int studyAreaBranchId, int studyAreaId, int customerCnt, LocalDateTime startAt, int hours){
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

    @Override
    public String toString() {
        return "예약고객 아이디:" + customerId  +
                ", 지점 번호" + studyAreaBranchId +
                ", 스터디공간 번호" + studyAreaId +
                ", 예약 시간" + startAt +
                ", 사용 기간" + hours;
    }
}
