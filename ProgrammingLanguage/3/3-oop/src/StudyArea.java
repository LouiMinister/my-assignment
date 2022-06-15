public class StudyArea {
    private int id; // 1~5
    private int capacity; // 1~10

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public StudyArea(int id, int capacity){
        this.id = id;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "StudyArea{" +
                "id=" + id +
                ", capacity=" + capacity +
                '}';
    }
}
