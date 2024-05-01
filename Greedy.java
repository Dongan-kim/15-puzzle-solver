package fifteenpuzzle;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class Greedy extends Gvalue {



	public Greedy(State firstState, State goalState) {
    	super(firstState, goalState);
    }
	
    @Override
    public Stack<State> solve() {
        Stack<State> open = new Stack<State>();
        Deque<State> closed = new LinkedList<State>();

        int[][] firstBoard = firstState.getBoard();
        int[][] goalBoard = goalState.getBoard();
        int rows = State.getRows();
        int col = State.getColumns();
        int numTopRows = rows-2;
        int numSorts = (col+1)/2;
        boolean flag = true;			                					//Is false when topRows are sorted

        for (int curRow = 0; curRow <= numTopRows; curRow++) {  //row by row
            for (int numToSort = 1; numToSort <= numSorts; numToSort++) {   //2tiles by 2tiles
                if (curRow == numTopRows) 
                	flag = false;

                int numBottom = rows - curRow;
                State.setRows(numBottom);

                int[][] newFirstBoard;
                int[][] newGoalBoard;
                int[][] previousBoard;
                String previousDirection = null;

                if (curRow == 0 && numToSort == 1) { 					               						//Start from firstBoard
                    previousBoard = firstBoard;
                }else{
                	previousDirection = closed.peekLast().getdirection();					                //Else continue from previous state
                    previousBoard = closed.pollLast().getBoard();
                }

                newFirstBoard = getRestRows(previousBoard, col, curRow, numBottom);      				//getting rest rows (rows that has not sorted yet) and make a board as new board and new goal board
                newGoalBoard = getRestRows(goalBoard, col, curRow, numBottom);			


                if (flag)
                    newFirstBoard = ignoreTiles(goalBoard[curRow], newFirstBoard, numBottom, col, numToSort);  //make -1 by two tiles

                State newInitialState = new State(newFirstBoard, null, previousDirection);
                State newGoalState = new State(newGoalBoard, null, null);

                AStar aStar = new AStar(newInitialState, newGoalState);
                open = aStar.solve();
                this.numOfmove += aStar.getNumOfMove();


                combine(open, goalBoard, rows, col, curRow);												//add top half 
                addToOpen(closed, open);
            }
    	}
        return solution(closed);
    }

    private int[][] ignoreTiles(int[] pattern, int[][] initialBoard, int rows, int col, int n) {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < col; j++)
                initialBoard[i][j] = Math.abs(initialBoard[i][j]);												

        int[][] ret = new int[rows][col];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < col; j++) {
                boolean contains = false;
                for (int p = 0; p < n * 2; p++) {												//two by two tiles
                    if (p / col == 1)
                        continue;
                    if (pattern[p] == initialBoard[i][j] || initialBoard[i][j] == 0)
                        contains = true;
                }
                if (contains)
                	ret[i][j] = initialBoard[i][j];
                else
                	ret[i][j] = initialBoard[i][j] * (-1);										//make numbers negative except the target numbers
            }
        return ret;
    }

    public void addToOpen(Deque<State> deque, Stack<State> stack) {
        while (!stack.isEmpty()) {
            deque.addLast(stack.pop());
        }
    }

    public Stack<State> solution(Deque<State> sol) {
        Stack<State> ret = new Stack<State>();
        while (!sol.isEmpty()) {
            ret.push(sol.pollLast());
        }
        return ret;
    }

    public int[][] getRestRows(int[][] board, int col, int numTopRows, int numBottom) {
        int[][] ret = new int[numBottom][col];
        for (int i = 0; i < numBottom; i++)
            for (int j = 0; j < col; j++)
                ret[i][j] = Math.abs(board[i + numTopRows][j]);
        return ret;
    }

    public void combine(Stack<State> stack, int[][] goalBoard, int rows, int col, int numTopRows) {
        State.setRows(rows);
        Stack<State> tmp = new Stack<State>();
        while (!stack.isEmpty()) {
            State curState = stack.pop();
            int[][] newBoard = new int[rows][col];
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < col; j++) {
                    if (i < numTopRows)						//rows that already solved
                        newBoard[i][j] = goalBoard[i][j];
                    else
                        newBoard[i][j] = curState.getBoard()[i - numTopRows][j];
                }
            State newState = new State(newBoard, null, curState.getdirection());
            tmp.push(newState);
        }
        while (!tmp.isEmpty())
            stack.push(tmp.pop());
    }
}
