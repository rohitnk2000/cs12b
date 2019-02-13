import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/*
 * Rohit Kulkarni, 1654070, 12B, Queens.java, 2/6/19
 * Recursively places Queens in nxn chessboard and finds solutions
 * where no queens attack each other
 */
public class Queens {

	/**
	 * determines whether or not the user wants each solutions
	 * to be printed or just the number of solutions
	 */
	static String mode = "";
	/**
	 * Places one queen onto one row of chessboard array and decrements every spot
	 * that queen could attack by one
	 * 
	 * @param B - chessboard represented by 2-d array
	 * @param i - row of chessboard
	 * @param j - column of chessboard
	 */
	static void placeQueen(int[][] B, int i, int j) {
		B[i][j] += 1;
		B[i][0] = j;
		//decrements vertical spots for rows below
		for (int k = i + 1; k < B.length; k++) {
			if (k != i) {
				B[k][j] -= 1;
			}
		}

		//decrements diagonals
		for (int k = 1; k < B.length; k++) {
			if (k != 0 && i + k < B.length && j + k < B.length) {
				B[i + k][j + k] -= 1;
			}
			if (k != 0 && i + k < B.length && j - k > 0) {
				B[i + k][j - k] -= 1;
			}
		}

	}

	/**
	 * Removes Queen from chessboard and undoes placeQueen
	 * 
	 * @param B - chessboard represented by 2-d array
	 * @param i - row of chessboard
	 * @param j - column of chessboard
	 */
	static void removeQueen(int[][] B, int i, int j) {
		B[i][j] -= 1;
		B[i][0] = 0;
		
		//increments vertical spots for rows below
		for (int k = i + 1; k < B.length; k++) {
			if (k != i) {
				B[k][j] += 1;
			}
		}
		//decrements diagonals
		for (int k = 1; k < B.length; k++) {
			if (k != 0 && i + k < B.length && j + k < B.length) {
				B[i + k][j + k] += 1;
			}
			if (k != 0 && i + k < B.length && j - k > 0) {
				B[i + k][j - k] += 1;
			}
		}

	}

	/**
	 * Places one queen onto the first row and recurs on all open spots of the next
	 * row. If a solution for the board is found, it is stored in the open 0th i and
	 * j index of array
	 * 
	 * @param B    - chessboard represented as 2-d array
	 * @param i    - row of the chessboard that queen will be placed on
	 * @param mode - determines whether solution is printed or just added to total
	 * @return number of solutions for an nxn board
	 */
	static int findSolutions(int[][] B, int i, String mode) {
		//if a queen is placed on the last row, 
		//which means a solution has been found
		if (i > B.length - 1) {
			if (mode.equals("verbose")) {
				System.out.print("(");
				for (int k = 1; k < B.length; k++) {
					if (k == B.length - 1) {
						System.out.print(B[k][0]);
					} else {
						System.out.print(B[k][0] + ", ");
					}
				}
				System.out.println(")");
			}
			B[0][0] += 1;
			return 1;
		} 
		//places queen on next row, finds the possible solutions, then removes the queen
		//so it can be placed on another row
		else {
			for (int j = 1; j < B.length; j++) {
				if (B[i][j] == 0) {
					placeQueen(B, i, j);
					findSolutions(B, i + 1, mode);
					removeQueen(B, i, j);
				}

			}
		}
		return B[0][0];
	}

	/**
	 * Prints the number of solutions to an nxn chessboard findSolutions method
	 * 
	 * @param B    - chessboard represented as 2-d array
	 * @param mode - "verbose" prints out solutions, otherwise just prints number of
	 *             solutions
	 */
	static void printBoard(int[][] B) {
		System.out.println(B.length - 1 + "-Queens has " + findSolutions(B, 1, mode) + " solutions");
	}

	/**
	 * initializes chessboard to n+1 by n+1 array all values are 0
	 * 
	 * @param n - number of places on the chessboard
	 * @return the chessboard array
	 */
	static int[][] initArray(int n) {
		int[][] start = new int[n + 1][n + 1];
		for (int i = 0; i < start.length; i++) {
			for (int j = 0; j < start[0].length; j++) {
				start[i][j] = 0;
			}
		}
		return start;
	}

	public static void main(String[] args) {
		int n = 0; // initializes chessboard size first
		//checks if there are arguments, and less than two arguments
		if (args.length <= 2 && args.length > 0) { 
			//if argument contains "-v", sets mode to "verbose
			//and checks if next argument is an integer
			if (args[0].contains("-v") && args[1].matches("\\d")) {
				mode = "verbose"; 									
				n = Integer.parseInt(args[1]);
				printBoard(initArray(n)); //prints solutions and number of solutions for nxn board
			}
			//If "-v" is not present, then just parses the next integer and prints the number of solutions
			else if (args[0].matches("\\d+") && Integer.parseInt(args[0]) > 0 && Integer.parseInt(args[0]) < 16) {
				n = Integer.parseInt(args[0]); // parses int from input
				printBoard(initArray(n));
			} 
			//prints default error message
			else {
				System.out.println("Usage: Queens [-v] number");
				System.out.println("Option: -v verbose output, print all solutions");
			}

		} 
		//if there are no arguments or more than two arguments, prints default error message
		else {
			System.out.println("Usage: Queens [-v] number");
			System.out.println("Option: -v verbose output, print all solutions");
		}

	}

}
