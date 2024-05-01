package fifteenpuzzle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class Solver {	
	public static void main(String[] args) throws NumberFormatException, IOException {
		File fileName = new File(args[0]);
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
		int size = Integer.parseInt(br.readLine());
		int[][] board = new int[size][size];
		
		int c1, c2, s;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				c1 = br.read();
				c2 = br.read();
				s = br.read(); 
				if (s != ' ' && s != '\n') {
					br.close();
					throw new IllegalArgumentException();
				}
				if (c1 == ' ')
					c1 = '0';
				if (c2 == ' ')
					c2 = '0';
				board[i][j] = 10 * (c1 - '0') + (c2 - '0');
			}
	      }
		 br.close();
		 
		 FileWriter fw = new FileWriter(args[1]);
		 int num = 1;													    //making goal State
		 int[][] goal = new int[size][size];
		 for(int i=0; i<size; i++) {
			 for(int j=0; j<size; j++) {
				 goal[i][j] = num;
				 num++;
			 }
		 }
		 goal[size-1][size-1] = 0;
		 
		 Stack<State> solution = new Stack<State>();
		 State.setRows(size);
		 State.setColumns(size);
		 State initial = new State(board, null, null);
		 State goalstate = new State(goal, null, null);
		 Greedy nodeC = new Greedy(initial,goalstate);
		 solution = nodeC.solve();
		 solution.pop();                                       				//to remove first null
		 while(!solution.isEmpty()) {
			 fw.write(solution.pop().getdirection());
			 fw.write("\n");
		 }
		 
		 fw.close();

	}
	
	
	
}
