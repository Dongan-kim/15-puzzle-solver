package fifteenpuzzle;
import java.awt.Point;

interface Hvalue {
   int gethValue(State firstState, State goalState);
}
public class Manhattan implements Hvalue{
	
    public int gethValue(State firstState, State goalState) {
        int rows = State.getRows();
        int col = State.getColumns();
        int[][] firstBoard = firstState.getBoard();
        int[][] goalBoard = goalState.getBoard();

        Point[] goalPositions = new Point[100];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < col; j++) {
                if (firstBoard[i][j] >= 0)
                    goalPositions[firstBoard[i][j]] = findGoalState(firstBoard[i][j], goalBoard, rows, col);
            }


        int diff = 0;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < col; j++) {
                if (firstBoard[i][j] == 0 || firstBoard[i][j] < 0) continue;   //if the number is negative or 0 skip it
                Point p = goalPositions[firstBoard[i][j]];
                diff += Math.abs(i - p.getX()) + Math.abs(j - p.getY());
            }
        return diff;
    }

    private Point findGoalState(int value, int[][] goalBoard, int rows, int col) {
        Point p = null;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < col; j++)
                if (value == goalBoard[i][j])
                    p = new Point(i, j);
        return p;
    }
}
