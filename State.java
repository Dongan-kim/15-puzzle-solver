package fifteenpuzzle;

import java.awt.*;
import java.util.Stack;


public class State {
    private static int rows;
    private static int col;

    private State parent;
    private int[][] board;
    private Point blank;
    private int gval;
    private int goalDistance;
    private String direction;   //

    public State(int[][] board, State parent, String direction) {
        this.blank = new Point(0, 0);
        this.board = new int[rows][col];
        this.parent = parent;
        this.direction = direction;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < col; j++) {
                if (board[i][j] == 0) {
                	blank.x = j;
                	blank.y = i;
                } else
                    this.board[i][j] = board[i][j];
            }
    }
    public int gethValue(State firstState, State goalState) {
        int rows = State.getRows();
        int col = State.getColumns();
        int[][] firstBoard = firstState.getBoard();
        int[][] goalBoard = goalState.getBoard();
        Manhattan manhatten = new Manhattan();

        Point[] goalPoint = new Point[100];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < col; j++) {
                if (firstBoard[i][j] >= 0)
                	goalPoint[firstBoard[i][j]] = findGoalState(firstBoard[i][j], goalBoard, rows, col);
            }

        int diff = 0;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < col; j++) {
                if (firstBoard[i][j] == 0 || firstBoard[i][j] < 0)
                    continue;
                else {
                    Point tile1Goal = goalPoint[firstBoard[i][j]];

                    for (int k = 0; k < col; k++) {											                    //Check for rows
                        if (firstBoard[i][k] == 0 || firstBoard[i][k] < 0)
                            continue;
                        Point tile2Goal = goalPoint[firstBoard[i][k]];
                        if (i == tile1Goal.getX() && tile1Goal.getX() == tile2Goal.getX() && j < k && tile1Goal.getY() > tile2Goal.getY())
                            diff++;
                    }																							//Check for columns
                    for (int k = 0; k < rows; k++) {
                        if (firstBoard[k][j] == 0 || firstBoard[k][j] < 0)
                            continue;
                        Point tile2Goal = goalPoint[firstBoard[k][j]];
                        if (j == tile1Goal.getY() && tile1Goal.getY() == tile2Goal.getY() && i < k && tile1Goal.getX() > tile2Goal.getX())
                            diff++;
                    }
                }
            }

        return manhatten.gethValue(firstState, goalState) + 2 * diff;
    }

    private Point findGoalState(int value, int[][] goalBoard, int rows, int col) {
        Point p = null;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < col; j++)
                if (value == goalBoard[i][j])
                    p = new Point(i, j);
        return p;
    }

    public static int getRows() {
        return rows;
    }

    public static void setRows(int rows) {
        State.rows = rows;
    }

    public static int getColumns() {
        return col;
    }

    public static void setColumns(int col) {
        State.col = col;
    }

    public void setGval() {
        if (parent == null)
            this.gval = 0;
        else
            this.gval = parent.getGval() + 1;
    }

    public int[][] getBoard() {
        return board;
    }
    
	public String getdirection() {
		return direction;
	}

    public int getGoalDistance() {
        return goalDistance;
    }

    public void setGoalDistance(State goalState) {
        this.goalDistance = gethValue(this, goalState);
    }

    public int getGval() {
        return gval;
    }

    public int gethValue() {
        return goalDistance + gval;
    }

    public void sethValue(State goalState) {
        setGoalDistance(goalState);
        setGval();
    }

    public State getParent() {
        return parent;
    }

    public Point getBlank() {
        return blank;
    }

    public Stack<State> getPath() {
        State current = this;
        Stack<State> path = new Stack<State>();
        while (current != null) {
            path.push(current);
            current = current.getParent();
        }
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof State)) return false;
        State s = (State) o;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < col; j++)
                if (this.board[i][j] != s.getBoard()[i][j] && (this.board[i][j] >= 0 && s.getBoard()[i][j] >= 0))
                    return false;
        return true;
    }

    @Override
    public int hashCode() {
        int num = 31;
        int result = 1;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < col; j++) {
                if (board[i][j] >= 0)
                    result = num * result + board[i][j];
                else
                    result = num * result - 1;
            }
        return result;
    }
}
