// definition of a point on the triangular grid
public class Point {
    private int x, y;
    final private static Point[] adjacentCord = {new Point(0, 1), new Point(1, 0), new Point(1, -1),
            new Point(0, -1), new Point(-1, 0), new Point(-1, 1)};

    // constructor of Point object
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // return x coordinate
    public int getX() {
        return this.x;
    }

    // return y coordinate
    public int getY() {
        return this.y;
    }

    // set x coordinate
    public void setX(int x) {
        this.x = x;
    }

    // set y coordinate
    public void setY(int y) {
        this.y = y;
    }

    // set both x and y coordinates
    public void setAll(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    // determine if the given coordinate is the same
    public boolean isSame(Point p) {
        if(this.x == p.getX() && this.y == p.getY()) {
            return true;
        }
        return false;
    }

    // determine if the given coordinate is adjacent
    public boolean isAdjacent(Point a) {
        int difX = this.x - a.getX();
        int difY = this.y - a.getY();
        Point difPoint = new Point(difX, difY);

        return difPoint.isIn(adjacentCord);
    }

    // determine if the given coordinate is in an array of coordinates
    public boolean isIn(Point[] pointArray) {
        for(Point p : pointArray) {
            if(this.isSame(p)) {
                return true;
            }
        }
        return false;
    }
}