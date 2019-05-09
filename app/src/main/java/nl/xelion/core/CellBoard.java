package nl.xelion.core;

import java.util.ArrayList;
import java.util.HashMap;

public class CellBoard {

    private boolean[][] cells;

    public boolean[][] getCells() {
        return cells;
    }

    private int numberOfColumns;
    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    private int numberOfRows;
    public int getNumberOfRows() {
        return numberOfRows;
    }

    // RULES
    // Anyliving cell with less than survivalMinCount live neighbors will die of underpopulation
    private final int SURVIVALMINCOUNT = 2; // Any living cell with at least this amount of live neighbors wil survive
    private final int SURVIVALMAXCOUNT = 3; // Any living cell with maximum this amount of live neighbors will survive
    // Anyliving cell with more than survivalMaxCount live neighbors will die of overpopulation
    private final int REPRODUCTIONCOUNT = 3; // Any dead cell with exactly this amount of live neighbors will become live as reporoduction

    public CellBoard(int numberOfColumns, int numberOfRows, Coordinate[] liveCellsSeed) {

        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;

        cells = new boolean[numberOfRows][numberOfColumns];

        reset();

        if (liveCellsSeed != null) {

            for (Coordinate coordinate : liveCellsSeed) {

                cells[coordinate.getRow()][coordinate.getColumn()] = true;
            }
        }
    }

    private void reset() {

        for (int row = 0; row < numberOfRows; row++) {

            for (int column = 0; column < numberOfColumns; column++) {

                cells[row][column] = false;
            }
        }
    }

    public boolean[] neighborsAt(int row, int column) {

        int startColumn = column - 1;
        if (startColumn < 0) {
            startColumn = 0;
        }

        int endColumn = column + 1;
        if (endColumn >= numberOfColumns) {
            endColumn = numberOfColumns - 1;
        }
        if (endColumn < 0) {
            endColumn = 0;
        }

        int startRow = row - 1;
        if (startRow < 0) {
            startRow = 0;
        }

        int endRow = row + 1;
        if (endRow >= numberOfRows) {
            endRow = numberOfRows - 1;
        }
        if (endRow < 0) {
            endRow = 0;
        }

        ArrayList<Boolean> _neighbors = new ArrayList<>();

        for (int _row = startRow; _row <= endRow; _row++) {

            for (int _column = startColumn; _column <= endColumn; _column++) {

                if (_row != row || _column != column) {

                    Boolean value = new Boolean(cells[_row][_column]);
                    _neighbors.add(value);
                }
            }
        }

        boolean[] neighbors = new boolean[_neighbors.size()];
        for (int i = 0; i < _neighbors.size(); i++) {
            neighbors[i] = _neighbors.get(i) != null && _neighbors.get(i).booleanValue();
        }

        return neighbors;
    }

    public int aliveNeighborsAt(int row, int column) {

        int aliveCount = 0;
        boolean[] neighbors = neighborsAt(row, column);

        for (int i = 0; i < neighbors.length; i++) {

            if (neighbors[i]) {
                aliveCount++;
            }
        }

        return  aliveCount;
    }

    public boolean toggleCellAt(int row, int column) {

        boolean alive = cells[row][column];

        int aliveNeighborsCount = aliveNeighborsAt(row, column);

        if (alive && aliveNeighborsCount < SURVIVALMINCOUNT) {
            // Dies of underpopulation
            alive = false;
        }
        else if (alive && aliveNeighborsCount >= SURVIVALMINCOUNT && aliveNeighborsCount <= SURVIVALMAXCOUNT) {
            // Survives
            alive = true;
        }
        else if (alive && aliveNeighborsCount > SURVIVALMAXCOUNT) {
            // Dies of overpopulation
            alive = false;
        }
        else if (!alive && aliveNeighborsCount == REPRODUCTIONCOUNT) {
            // Reproduction
            alive = true;
        }

        return alive;
    }

    public CellBoard iterate() {

        CellBoard newBoard = new CellBoard(numberOfColumns, numberOfRows, null);

        for (int row = 0; row < numberOfRows; row++) {

            for (int column = 0; column < numberOfColumns; column++) {

                boolean newValue = toggleCellAt(row, column);
                newBoard.cells[row][column] = newValue;
            }
        }

        return newBoard;
    }

    public String description() {

        String desc = "";

        for (int row = 0; row < numberOfRows; row++) {

            String line = "";

            for (int column = 0; column < numberOfColumns; column++) {

                Coordinate coordinate = new Coordinate(column, row);
                boolean value = cells[row][column];

                line += value ? "X" : "-";
            }

            if (!desc.isEmpty()) {
                desc += "\n";
            }
            desc += line;
        }

        return  desc;
    }

    public int getAliveCellCount() {

        int count = 0;

        for (int row = 0; row < numberOfRows; row++) {

            for (int column = 0; column < numberOfColumns; column++) {

                if (cells[row][column]) {
                    count += 1;
                }
            }
        }

        return count;
    }
}
