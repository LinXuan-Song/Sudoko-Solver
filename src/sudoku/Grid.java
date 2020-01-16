package sudoku;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Grid { //class used to keep objects un-static
	
	public Box[][] Boxes;
	LinkedList<Box[][]> stackSaved = new LinkedList<Box[][]>();
	
	public Grid() { //populates the grid

		Boxes = new Box[9][9];
		
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				Boxes[x][y] = new Box(y, x);
			}
		}

		//populate the 3x3 boxes
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				Boxes[x][y].setThreeXthree(1);
			}
			for (int x = 3; x < 6; x++) {
				Boxes[x][y].setThreeXthree(2);
			}
			for (int x = 6; x < 9; x++) {
				Boxes[x][y].setThreeXthree(3);
			}
		}

		for (int y = 3; y < 6; y++) {
			for (int x = 0; x < 3; x++) {
				Boxes[x][y].setThreeXthree(4);
			}
			for (int x = 3; x < 6; x++) {
				Boxes[x][y].setThreeXthree(5);
			}
			for (int x = 6; x < 9; x++) {
				Boxes[x][y].setThreeXthree(6);
			}
		}

		for (int y = 6; y < 9; y++) {
			for (int x = 0; x < 3; x++) {
				Boxes[x][y].setThreeXthree(7);
			}
			for (int x = 3; x < 6; x++) {
				Boxes[x][y].setThreeXthree(8);
			}
			for (int x = 6; x < 9; x++) {
				Boxes[x][y].setThreeXthree(9);
			}
		}
	} //end constructor
	
	public void read() throws FileNotFoundException {//read puzzle in from a file
	    
	    	Scanner input = new Scanner(new FileInputStream("Puzzle.txt")); //get puzzle from file
	    		for (int y = 0; y < 9; y++) 
	    		    	for (int x = 0; x < 9; x++) 
	    		    	    	Boxes [x][y].setData(input.nextInt());
	    		    	
		input.close();
		
	} //end read
	
	public void save(){//Save the board before a guess is made
	    
		Box[][] copy = new Box[9][9];
		
		for(int y = 0; y < 9; y++){ //populate copy
			for(int x = 0; x < 9; x++){
				Box temp = new Box(y, x);
				temp.setColumn(Boxes[x][y].getColumn());
				temp.setRow(Boxes[x][y].getRow());
				temp.setThreeXthree(Boxes[x][y].getThreeXthree());
				temp.setData(Boxes[x][y].getData());
				temp.setPossible(Boxes[x][y].getPossible());
				copy[x][y] = temp;
			}
		}
		stackSaved.add(copy); //add copy to stack
		
	} //end save
	
	public void retrieve(){//Load last save
	    
		for(int y = 0; y < 9; y++){ //copy information
			for(int x = 0; x < 9; x++) {
				Boxes[x][y].setColumn(stackSaved.getLast()[x][y].getColumn());
				Boxes[x][y].setRow(stackSaved.getLast()[x][y].getRow());
				Boxes[x][y].setThreeXthree(stackSaved.getLast()[x][y].getThreeXthree());
				Boxes[x][y].setData(stackSaved.getLast()[x][y].getData());
				Boxes[x][y].setPossible(stackSaved.getLast()[x][y].getPossible());
			}
		}
		stackSaved.removeLast(); //remove from stack
		
	} //end retrieve
	
	public void eliminate(int row, int column){//eliminate candidates that are the same as the cell's data in cells of the same row and column
		
	    	Box box = Boxes[row][column];
	    
	    	for (int counterRow = 0; counterRow < 9; counterRow++) { //eliminate from rows
		    box.possible[Boxes[counterRow][column].getData()] = false;
		}
			    	
		for (int counterColumn = 0; counterColumn < 9; counterColumn++) { //eliminate from columns
		    box.possible[Boxes[row][counterColumn].getData()] = false;
		}
		
		int temp3X3 = box.getThreeXthree() - 1; //prepare for 3x3 box elimination
		int tempRow = (temp3X3 % 3) * 3;
		int tempColumn = (temp3X3 / 3) * 3;
		
		for (int y = tempColumn; y < tempColumn + 3; y++) { //eliminate 3x3 box
			for (int x = tempRow; x < tempRow + 3; x++) {
				Boxes[x][y].possible[box.getData()] = false;
			}
		}

	} //end eliminate

	public Box[][] getBoxes() {
		return Boxes;
	}

	public void setBoxes(Box[][] boxes) {
	    Boxes = boxes;
	}

	public LinkedList<Box[][]> getStackSaved() {
	    return stackSaved;
	}

	public void setStackSaved(LinkedList<Box[][]> stackSaved) {
	    this.stackSaved = stackSaved;
	}

}
