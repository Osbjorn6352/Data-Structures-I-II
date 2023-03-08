package algs11;
import stdlib.StdOut;


public class Crossword {
	/** Each element is a Square object with a color (black or white) and a number
	 *  puzzle[r][c] represents the square in row r, column c
	 *  There is at least one row in the puzzle 
	 */
	
	private Square[][] puzzle;
	
	/** Constructs a crossword puzzle grid
	 *  PRECONDITION: There is at least one row in blackSquares
	 *  POSTCONDITION:
	 *  - The crossword puzzle grid has the same dimensions as blackSquares
	 *  - The Square object at row r, column c in the crossword puzzle grid is black
	 *    if and only if blackSquares[r][c] is true
	 *  - The squares in the puzzle are labeled according to the crossword labeling rule
	 */
	
	public Crossword(boolean[][] blackSquares) {
		//Contructs the puzzle
		puzzle = new Square[blackSquares.length][blackSquares[0].length];
	    int numLabel = 1; // set a positive label for our first square to be labeled
		//next, set a nested for loop to traverse through the puzzle, by each column within each row
		for (int r = 0; r < blackSquares.length; r++){
			for (int c = 0; c < blackSquares[0].length; c++){
				if (toBeLabeled(r, c, blackSquares)){
					//CONSTRUCT the squares within each row/column
					puzzle[r][c] = new Square(false, numLabel);
					numLabel++; //if number needs to be labeled, label it, set it as white, and increment label
				}
				else {
					puzzle[r][c] = new Square(blackSquares[r][c], 0);
				}
			}
		}
	}

	/** Returns true if the square at row r, colum c should be labeled with a positive number;
	 *          false otherwise
	 *  The square at row r, column c is black if and only if blackSquares[r][c] is true
	 *  PRECONDITION: r and c are valid indexes in blackSquares
	 */

	private boolean toBeLabeled(int r, int c, boolean[][] blackSquares) {
		// 1) Only white squares should be labeled, so, set black square to false 
		if (blackSquares[r][c] == true) return false;
		// 2) When r is white and 0, we return true (All white squares on top row should be labeled)
		else if (r == 0) return true;
		// 3) When c is white and zero, we return true (All white squares in first column should be labeled)
		else if (c == 0) return true;
		// 4) Last case only reached if r and c are greater than zero
		//    check if square above or left is black (if so we should label)
		else return (blackSquares[r-1][c] || blackSquares[r][c-1]);

	}
	
	public void print() {
		for (int i=0; i<puzzle.length; i++) {
			for (int j=0; j<puzzle[0].length; j++)
					StdOut.format("%3d", puzzle[i][j].getLabel()); //made it a format string to print crossword more cleanly
			StdOut.println();
		}
				
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean[][] test = new boolean[7][9];
		test[0][0] = true; test[0][3] = true;test[0][4] = true;test[0][5] = true;
        test[1][4]= true;
        for (int j=6; j<9; j++) test[2][j] = true;
        test[3][2]=true;  test[3][6]=true;
        for (int j=0; j<3; j++) test[4][j] = true;
        test[5][4]=true;
        test[6][3] = true;test[6][4] = true;test[6][5] = true; test[6][8] = true;
        
        Crossword c = new Crossword(test);
        c.print(); 
	}

}
