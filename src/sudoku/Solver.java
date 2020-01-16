package sudoku;

import java.io.FileNotFoundException;

public class Solver {
	
	static Grid grid = new Grid();

	static boolean solved() { //check if the puzzle is solved
	    
		for (int y = 0; y < 9; y++) 
			for (int x = 0; x < 9; x++) 
				if (grid.getBoxes()[x][y].getData() == 0)
					return false;
		return true;
		
	} //end solved

	static void display() { //display the grid
	    
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				System.out.print(grid.getBoxes()[x][y].getData() + " ");
			}
			System.out.println();
		}
		
	} //end display
	
	static void solve() { //tries to solve the puzzle

		int numTrue;
		int numAtTrue = 0;
		boolean needToGuess = false;
		boolean same = true;
		boolean restart = false;

		while (!solved()) { //while the puzzle is not solved

			same = true;
			restart = false;
			
			for (int y = 0; y < 9; y++) //eliminate possibilities
				for (int x = 0; x < 9; x++)
				    	grid.eliminate(x, y);
				
			for (int y = 0; y < 9; y++) { //cycle the grid
				for (int x = 0; x < 9; x++) {
				    
					if (grid.getBoxes()[x][y].getData() == 0) { //if the spot is not solved
					    	
					    	numTrue = 0;
						numAtTrue = 0;
						
						for (int counter = 0; counter < 10; counter++) // find number of true and the number at true
							if (grid.getBoxes()[x][y].possible[counter]) {
								numAtTrue = counter;
								numTrue++;
							}

						if (numTrue == 1) { //when only one possible solution
						    	grid.getBoxes()[x][y].possible[numAtTrue] = false;
						    	grid.getBoxes()[x][y].setData(numAtTrue);
							same = false;
							restart = true;
							break;
						} 
						
						//guess portion starts: 
						//contradiction comes before guess because it shouldn't guess if there's a contradiction
						
						else if (numTrue == 0) { //when there's a contradiction
						    	grid.retrieve();
							restart = true;
							break;
						}
						
						else if (needToGuess) { //when we need to guess
						    	grid.getBoxes()[x][y].possible[numAtTrue] = false;
						    	grid.save();
						    	grid.getBoxes()[x][y].setData(numAtTrue);
							needToGuess = false;
							same = false;
							restart = true;
							break;
						}
						
					} //end if the spot is not solved
				
				} //end cycle x
				
				if(restart)
					break;	
				
			} //end cycle y
			
			if (same) // if the grid remains unchanged, we need to make a guess
				needToGuess = true;
		} //end while the puzzle is not solved
	} //end solve

	public static void main(String[] args) throws FileNotFoundException{
		
		grid.read();
		solve();
		display();
		
	}
}
