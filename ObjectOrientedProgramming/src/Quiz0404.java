class Quiz0404{
    public static void main(String args[]) {
        Rectangle.main(new String[0]);
    }
}

class Rectangle {
    private int x;
    private int y;
    private int width;
    private int height;

    public Rectangle(int x, int y, int width, int height){
        this.x= x;
        this.y= y;
        this.width = width;
        this.height = height;
    }

    public int square(){
        return width*height;
    }

    public void show(){
        System.out.println("(" + x +","+y+")에서 크기가 "+width+"x"+height+"인 사각형");
    }

    public boolean contains(Rectangle rec){ // 외각선이 겹치는경우는 포함으로 치지 않음
        return
                this.x <rec.x &&
                this.y < rec.y &&
                rec.x + rec.width < this.x + this.width &&
                rec.y + rec.height < this.y + this.height;
    }

    public static void main(String args[]){
        Rectangle r = new Rectangle(2, 2, 8, 7);
        Rectangle s = new Rectangle(5, 5, 6, 6);
        Rectangle t = new Rectangle(1, 1, 10, 10);

        r.show();
        System.out.println("s의 면적은 " + s.square());
        if(t.contains(r)) System.out.println("t는 r을 포함합니다.");
        if(t.contains(s)) System.out.println("t는 s을 포함합니다.");
    }
}
