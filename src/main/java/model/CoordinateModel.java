package model;

/**
 * Represents the coordinates of a country.
 */
public class CoordinateModel {
    private int x;
    private int y;

    /**
     * Creates a coordinate with the specified x and y values.
     *
     * @param x x-coordinate value
     * @param y y-coordinate value
     */
    public CoordinateModel(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * get the x-coordinate value
     * @return x-coordinate value
     */
    public int getX() {return x;}

    /**
     * get the y-coordinate value
     * @return y-coordinate value
     */
    public int getY() {return y;}
}