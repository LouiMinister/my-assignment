import java.util.Arrays;

public class StudyAreaBranch {
    private int id; // 1~6
    private StudyArea[] studyAreas; // 1~5

    public StudyAreaBranch(int id){
        this.id = id;
        this.studyAreas =  new StudyArea[6];
    }

    public StudyArea[] getStudyAreas() {
        return studyAreas;
    }

    public void setStudyAreas(StudyArea[] studyAreas) {
        this.studyAreas = studyAreas;
    }

    @Override
    public String toString() {
        return "StudyAreaBranch{" +
                "id=" + id +
                ", studyAreas=" + Arrays.toString(studyAreas) +
                '}';
    }
}
