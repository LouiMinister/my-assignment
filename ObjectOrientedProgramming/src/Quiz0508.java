public class Quiz0508 {
    public static void main(String[] args){
        PositivePosition p = new PositivePosition();
        p.move(10, 10);
        System.out.println(p.toString() + "입니다.");

        p.move(-5, 5); // 객체 p는 음수 공간으로 이동되지 않음
        System.out.println(p.toString() + "입니다.");

        PositivePosition p2 = new PositivePosition(-10, -10);
        System.out.println(p2.toString() + "입니다.");
    }
}

class Point {
    private int x, y;
    public Point(int x, int y) {this.x = x; this.y = y;}
    public int getX() {return x;}
    public int getY() {return y;}
    protected void move(int x, int y) {this.x= x; this.y= y;}
}

class PositivePosition extends Point {
    public PositivePosition(){
        super(0, 0);
    }
    public PositivePosition(int x, int y) {
        super(Math.max(x, 0), Math.max(y, 0));
    }
    @Override
    protected void move(int x, int y) {
        if(x >= 0  && y >= 0) {
            super.move(x, y);
        }
    }
    @Override
    public String toString() {
        return String.format("(%d,%d)의 점", getX(), getY());
    }
}