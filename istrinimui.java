//==== Simple (shalow) public cloning
class CloneablePoint implements Cloneable {
    int x,y;

    public CloneablePoint(int x, int y){
        this.x = x; this.y = y;
    }

    // Overriding Object.clone
    public  Object clone()
            throws CloneNotSupportedException {
        return super.clone();
    }

    public String toString() {return "(" + x + "," + y + ")";}
}

class TestClone
{
    public static void main(String[] args)
    {
        try
        {
            CloneablePoint p1 = new CloneablePoint(1,2);
            CloneablePoint p2 = (CloneablePoint)p1.clone();
            System.out.println("p1=" + p1 + " p2=" + p2 +
                    " p1 == p2: " + (p1==p2));
            // p1=(1,2) p2=(1,2) p1 == p2: false
        }
        catch (CloneNotSupportedException exc)
        {
            System.out.println("Cloning exception:" + exc);
        }
        (new Line(0,1,2,3)).clone();
    }
}


//=== Deep cloning
class Line implements Cloneable {
    CloneablePoint p1 , p2;
    Line(int x1, int y1, int x2, int y2){
        p1 = new CloneablePoint(x1,y1);
        p2 = new CloneablePoint(x2,y2);
    }

    public  Object clone() {
        try {
            Line l = (Line) super.clone();
            // Clone points
            l.p1 = (CloneablePoint) p1.clone();
            l.p2 = (CloneablePoint) p2.clone();
            return l;
        }
        catch (CloneNotSupportedException exc) {
            // Should never happen
            throw new Error ("Oi" +exc.getMessage());
        }
    }
}
