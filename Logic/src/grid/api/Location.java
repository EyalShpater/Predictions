package grid.api;

public class Location implements Comparable<Location> {
    private int x;
    private int y;

    public Location() {
        x = 0;
        y = 0;
    }

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int compareTo(Location other) {
        int difference = Integer.compare(this.x, other.x);

        if (difference == 0) {
            difference = Integer.compare(this.y, other.y);
        }

        return difference;
    }
}
