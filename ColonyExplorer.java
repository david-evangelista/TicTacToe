import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class ColonyExplorer {


	public static int recurExploreAndLabelColony(char[][] grid, int row, int col, char label) {
		int colonySize = 0;	// declaring variable
		grid[row][col]=label; // replace with label
		colonySize++; // increase colony size by 1

		// LEFT
		if (col != 0 && grid[row][col-1] == '1')
			colonySize += recurExploreAndLabelColony(grid, row, col-1, label);	

		// LEFT UP
		if (col !=0 && row !=0 && grid[row-1][col-1] == '1')
			colonySize += recurExploreAndLabelColony(grid, row-1, col-1, label);

		// UP
		if (row != 0 && grid[row-1][col] == '1')
			colonySize += recurExploreAndLabelColony(grid, row-1, col, label);

		// UP RIGHT
		if (row != 0 && col != grid[0].length-1 && grid[row-1][col+1] == '1')
			colonySize += recurExploreAndLabelColony(grid, row-1, col+1, label);

		// RIGHT
		if (col != grid[0].length-1 && grid[row][col+1] == '1')
			colonySize += recurExploreAndLabelColony(grid, row, col+1, label);	

		// DOWN RIGHT
		if (row != grid.length-1 && col != grid[0].length-1 && grid[row+1][col+1] == '1')
			colonySize += recurExploreAndLabelColony(grid, row+1, col+1, label);	

		// DOWN
		if (row != grid.length-1 && grid[row+1][col] == '1')
			colonySize += recurExploreAndLabelColony(grid, row+1, col, label);

		// LEFT DOWN
		if (col !=0 && row != grid.length-1 && grid[row+1][col-1] == '1')
			colonySize += recurExploreAndLabelColony(grid, row+1, col-1, label);

		// RETURN
		return colonySize;

	} // recurExploreAndLabelColony


	public static int iterExploreAndLabelColony(char[][] grid, int row, int col, char label) {
		int colonySize = 0;	// declaring variable
		grid[row][col]=label; // replace with label
		colonySize++; // increase colony size by 1

		// PARSING THROUGH GRID
		for (int i = 0; i < grid.length; i++) {	// will iterate over each row and skip a line at the end

			for (int k = 0; k < grid[0].length; k++){ // will iterate over columns, and stops while a Colony has been found	
					
				if (grid[i][k] == label) { // if find a cell of the colony already labeled

					// *** NEIGHBORS ***
			
					// LEFT
					if (k != 0 && grid[i][k-1] == '1') {
						grid [i] [k-1] = label;	
						colonySize ++;
					}
					// UP LEFT
					if (k != 0 && i!=0 && grid[i-1][k-1] == '1') {
						grid [i-1] [k-1] = label;	
						colonySize ++;
					}
					// UP
					if (i!=0 && grid[i-1][k] == '1') {
						grid [i-1] [k] = label;	
						colonySize ++;
					}
					// UP RIGHT
					if (k != grid[0].length-1 && i!=0 && grid[i-1][k+1] == '1') {
						grid [i-1] [k+1] = label;	
						colonySize ++;
					}
					// RIGHT
					if (k != grid[0].length-1 && grid[i][k+1] == '1') {
						grid [i] [k+1] = label;	
						colonySize ++;
					}
					// DOWN RIGHT
					if (i != grid.length-1 && k != grid[0].length-1 && grid[i+1][k+1] == '1') {
						grid[i+1][k+1] = label;	
						colonySize++;
					}
					// DOWN
					if (i != grid.length-1 && grid[i+1][k] == '1') {
						grid[i+1][k] =label;
						colonySize++;
					}
					// LEFT DOWN
					if (k !=0 && i != grid.length-1 && grid[i+1][k-1] == '1') {
						grid[i+1][k-1] = label;
						colonySize++;
					}
					
				} // if
				
				
			}// for				
		} //for

		// RETURN
		return colonySize;

	} // iterExploreAndLabelColony

	
	
	// ****** MAIN METHOD *******
	public static void main(String[] args) {

		// declaring streams
		Scanner key = new Scanner(System.in); // for user keyboard input
		int intMode = 0; // 1 = recursion, 2 = iteration
		boolean done = false; // boolean if T, jump to end of program, while False, loop
		String m1 = "Recursion", m2 = "Iteration", m3 = "Quit Program"; // strings for modes

		// ** WELCOME **
		System.out.println("Welcome to the Colony Explorer program!\n\n");// greeting

		while (!done) { // outer while

			// USER MODE SELECTION
			System.out.println("**********************\n\n");
			System.out.println("Please select the mode.\n1 - "+m1+"\n2 - "+m2+"\n3 - "+m3+"\n");// user selection
			try {				
				intMode = key.nextInt(); // getting user input		

				// VALIDATING INPUT
				if (intMode <1 || intMode > 3) { // if invalid input
					key.close(); // closing stream for compiler
					throw new IOException();	
				} // if

			} // try	

			catch (IOException e) {
				System.out.println("Error. Invalid Input.");
			} // catch

			// SELECTION FEEDBACK
			switch (intMode) { // user feedback
			case 1:
				System.out.println("\nYou selected "+intMode+" - "+m1+". "); // user input feedback
				break;
			case 2:
				System.out.println("\nYou selected "+intMode+" - "+m2+". "); // user input feedback
				break;				
			case 3:
				System.out.println("\nYou selected "+intMode+" - "+m3+". "); // user input feedback
				break;						
			}	//switch

			// IF USER QUITS
			if (intMode == 3) //if user selected quit
				done = true; // jumps to end of program

			// ELSE - PROGRAM CONTINUES
			if (!done) { //only continues if user didn't select quit

				// USER INPUT	
				System.out.println("\nGenerating grid...\n\n");// feedback

				// GENERATING RANDOM GRID	
				Random rand = new Random(); // will help generate random binary value			
				char grid [][] = new char [rand.nextInt(15)+5] [rand.nextInt(15)+5]; // creating "matrix" 2-d array of random size
				// first dim = height/row *** 2nd dim = length/column	

				for (int i = 0; i < grid.length; i++, System.out.println()) {	// will iterate over each row and skip a line at the end				

					for (int k = 0; k < grid[0].length; k++){ // will iterate over each column (each L-R entry in each row)

						grid[i][k] = (char)(rand.nextInt(2)+48); // will generate either 0 or 1	 (adding 48 because casting)		
						System.out.print(grid[i][k] + "  ");		

					}// for				
				} //for

				System.out.println("\n"); // skipping line



				// FEEDBACK
				if (intMode == 1)
					System.out.println("\nExploring colonies using Recursion...\n\n");// feedback
				if (intMode == 2)
					System.out.println("\nExploring colonies using Iteration...\n\n");// feedback

				String label = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; // will hold char for label
				int currLabel = 0;

				// PARSING THROUGH GRID
				for (int i = 0; i < grid.length; i++) {	// will iterate over each row			
					for (int k = 0; k < grid[0].length; k++){ // will iterate over each column
						if (grid[i][k] == '1') {	// call ExploreAndLabelColony	
						
							
							if (intMode == 1) // recursion
							System.out.println("Colony "+label.charAt(currLabel)+": "+recurExploreAndLabelColony(grid, i, k, label.charAt(currLabel)));
							if (intMode == 2) // iteration
								System.out.println("Colony "+label.charAt(currLabel)+": "+iterExploreAndLabelColony(grid, i, k, label.charAt(currLabel)));							
							
							if (currLabel == 51) // reset label from last one to 0
								currLabel = 0;
							else	currLabel++; // otherwise label goes to following letter
						} //if
					}// for				
				} //for

				System.out.println("\n"); // skipping line



				//	if (intMode == 1) // recursive
				// call method
				//	if (intMode ==2) // iterative
				// call method		


				// ** END OF OPERATION **


				
				
				
				// RE WRITING GRID
				for (int i = 0; i < grid.length; i++, System.out.println()) {	// will iterate over each row and skip a line at the end				
					for (int k = 0; k < grid[0].length; k++){ // will iterate over each column (each L-R entry in each row)		
						if (grid[i][k]=='0') // replace with '-'
							grid[i][k]='-';
						System.out.print(grid[i][k] + "  ");		
					}// for				
				} //for

				System.out.println("\n"); // skipping line








				// PROMPT FOR CONTINUE Y/N	
				String yn = "";						
				System.out.println("Would you like to continue? Y/N\n"); // prompting user whether to close program

				try {				
					yn = key.next(); // getting user input		

					// VALIDATING INPUT
					if (!yn.equalsIgnoreCase("y") && !yn.equalsIgnoreCase("n")) { // if invalid input
						key.close(); // closing stream for compiler
						throw new IOException();	
					} // if

				} // try	

				catch (IOException e) {
					System.out.println("Error. Invalid Input.");
				} // catch

				if (yn.equalsIgnoreCase("n"))
					done = true;

			}// if !done
		}// outer !done while

		// CLOSING PROGRAM
		System.out.println("Closing Program. Have a nice day! Goodbye.");
		// closing streams
		key.close(); // compiler
		System.exit(0);// closing program



	}// main






} // Driver
