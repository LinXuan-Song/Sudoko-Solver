package sudoku;

public class Box {
	
	private int row;
	private int column;
	private int threeXthree;
	private int data;
	public boolean[] possible;
	
	public Box(int row, int column){
		
	    	this.row = row;
		this.column = column;
		
		possible = new boolean[10];
		
		for(int counter = 1; counter < 10; counter++){
			possible[counter] = true;
		}
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getThreeXthree() {
		return threeXthree;
	}

	public void setThreeXthree(int box) {
		this.threeXthree = box;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}
	
	public void setPossible(boolean[] temp){
		for(int x = 0; x < 10; x++){
			possible[x] = temp[x];
		}
	}
	
	public boolean[] getPossible(){
		return possible;
	}

}
