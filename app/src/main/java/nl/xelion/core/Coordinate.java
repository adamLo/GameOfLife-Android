package nl.xelion.core;

public class Coordinate {

    private int column;
    private int row;

    public Coordinate(int column, int row) {

        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
