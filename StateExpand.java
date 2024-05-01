package fifteenpuzzle;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class StateExpand {
    public List<State> expandStates(State curState) {
        Point blank = curState.getBlank();
        State parent = curState;
        int rows = State.getRows();
        int col = State.getColumns();

        List<State> next = new ArrayList<>();
        if (this.checkRight(blank, col))
            next.add(this.moveRight(rows, col, blank, parent));
        if (this.checkDown(blank, rows))
            next.add(this.moveDown(rows, col, blank, parent));
        if (this.checkUp(blank))
            next.add(this.moveUp(rows, col, blank, parent));
        if (this.checkLeft(blank, col))
            next.add(this.moveLeft(rows, col, blank, parent));

        return next;
    }

    private boolean checkUp(Point blank) {								      //checking where the blank is at
        int position = blank.y - 1;
        if (position < 0)
            return false;
        return true;
    }

    private boolean checkDown(Point blank, int rows) {
        int position = blank.y + 1;
        if (position >= rows)
            return false;
        return true;
    }

    private boolean checkLeft(Point blank, int col) {
        if (blank.x % col == 0)
            return false;
        return true;
    }

    private boolean checkRight(Point blank, int col) {
        if (blank.x % col == col - 1)
            return false;
        return true;
    }

    private State moveUp(int rows, int col, Point blank, State parent) {	//make move a tile
        int[][] newBoard = new int[rows][col];
        copy(newBoard, rows, col, parent);

        int tmp = newBoard[blank.y][blank.x];
        newBoard[blank.y][blank.x] = newBoard[blank.y - 1][blank.x];
        newBoard[blank.y - 1][blank.x] = tmp;

        return new State(newBoard, parent, Integer.toString(Math.abs(newBoard[blank.y][blank.x])) + " D");
    }

    private State moveDown(int rows, int col, Point blank, State parent) {
        int[][] newBoard = new int[rows][col];
        copy(newBoard, rows, col, parent);

        int tmp = newBoard[blank.y][blank.x];
        newBoard[blank.y][blank.x] = newBoard[blank.y + 1][blank.x];
        newBoard[blank.y + 1][blank.x] = tmp;

        return new State(newBoard, parent, Integer.toString(Math.abs(newBoard[blank.y][blank.x])) + " U");
    }

    private State moveLeft(int rows, int col, Point blank, State parent) {
        int[][] newBoard = new int[rows][col];
        copy(newBoard, rows, col, parent);

        int tmp = newBoard[blank.y][blank.x];
        newBoard[blank.y][blank.x] = newBoard[blank.y][blank.x - 1];
        newBoard[blank.y][blank.x - 1] = tmp;

        return new State(newBoard, parent, Integer.toString(Math.abs(newBoard[blank.y][blank.x])) + " R");
    }

    private State moveRight(int rows, int col, Point blank, State parent) {
        int[][] newBoard = new int[rows][col];
        copy(newBoard, rows, col, parent);

        int tmp = newBoard[blank.y][blank.x];
        newBoard[blank.y][blank.x] = newBoard[blank.y][blank.x + 1];
        newBoard[blank.y][blank.x + 1] = tmp;

        return new State(newBoard, parent, Integer.toString(Math.abs(newBoard[blank.y][blank.x])) + " L");
    }

    private void copy(int[][] newBoard, int rows, int col, State parent) {
        int[][] board = parent.getBoard();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < col; j++)
                newBoard[i][j] = board[i][j];
    }
}
