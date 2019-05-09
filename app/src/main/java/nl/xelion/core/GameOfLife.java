package nl.xelion.core;

import java.util.ArrayList;

public class GameOfLife {

    private ArrayList<CellBoard> iterations;

    public ArrayList<CellBoard> getIterations() {
        return iterations;
    }

    public GameOfLife(int numberOfColumns, int numberOfRows, Coordinate[] liveCellsSeed) {

        CellBoard board = new CellBoard(numberOfColumns, numberOfRows, liveCellsSeed);
        iterations = new ArrayList<CellBoard>();
        iterations.add(board);
    }

    public CellBoard getCurrentBoard() {

        if  (iterations != null && iterations.size() > 0) {

            CellBoard currentBoard = iterations.get(iterations.size() - 1);
            return  currentBoard;
        }

        return null;
    }

    public boolean iterate() {

        CellBoard currentBoard = getCurrentBoard();

        if (currentBoard != null) {

            CellBoard newBoard = currentBoard.iterate();

            if (newBoard != null) {

                iterations.add(newBoard);
                return true;
            }
        }

        return false;
    }

    public boolean isFinished() {

        CellBoard currentBoard = getCurrentBoard();

        if (currentBoard != null) {

            int aliveCount = currentBoard.getAliveCellCount();
            return  aliveCount == 0 || aliveCount == currentBoard.getNumberOfColumns() * currentBoard.getNumberOfRows();
        }

        return true;
    }

}
