
import java.io.*;
import java.util.*;
public class Driver2 {


	// UNHIDE RECURSIVE METHOD
	public static void unHideRec(String instr, PrintWriter writer) {		
		int next = instr.indexOf('#');// stores index of next '#'

		// BASE CASE
		if (next == -1) { // if no '#'
			writer.println(instr); // -> print string to file
		}		
		// RECURSION
		if (next >=0) { // if contains '#'
			// O
			instr = instr.substring(0, next).concat("O").concat(instr.substring(next+1)); // replaces next '#' with 'O'
			unHideRec(instr, writer); // recursion call		
			// X
			instr = instr.substring(0, next).concat("X").concat(instr.substring(next+1)); // replaces next '#' with 'X'
			unHideRec(instr, writer); // recursion call		
		}		
	} // end of unHide Recursive method


	// UNHIDE ITERATIVE METHOD
	public static void unHideIter(String instr, PrintWriter writer, int nbrHidden) {		

		// FINDING INDICES OF '#' & STORING IN ARRAY
		int[] arrayIndices = new int[nbrHidden];// array to hold indices of #	
		for (int i = 0; i<nbrHidden;i++) { // find+store indices of #
			if (i==0)
				arrayIndices[i] = instr.indexOf('#');// stores index of next #
			else 
				arrayIndices[i] = instr.indexOf('#', arrayIndices[i-1]+1);// stores index of next '#' following the previous
		} // for: now we have 'arrayIndices' ∀ # in string 'instr'

		// -- PROCESSING --

		String binary = ""; // this will hold the <=> of the #'s but in 0-1's

		// CONSTRUCTING BINARY BASE MODEL (all 0's i.e. 000...)
		for (int i = 0; i < arrayIndices.length; i++) {
			binary = binary.concat("0"); // constructing string of 0's
		} // we now have string '000...' holding a 0 ∀ # <=> 'OOO'

		// WRITING ALL PERMUTATIONS TO FILE
		for (int i = 0; i < Math.pow(2, nbrHidden); i++) { // iterates through all possible permutations

			for (int j =0; j < arrayIndices.length; j++) { // converts each character back from binary to XO
				instr = instr.substring(0, arrayIndices[j]) + bintoXO(binary.charAt(j)) + instr.substring(arrayIndices[j]+1); // converts to XO				
			} // for: string now converted back to XO

			writer.println(instr); // printing the permutation to file

			// ADDING +1 to binary
			binary = binAdd(binary); // adding 1 i.e. 0001 becomes 0010

		} // for: permutation iteration

	} // end of unHide Iterative method


	public static char bintoXO (char inChar) {
		if (inChar == '0')
			return 'O';
		if (inChar == '1')
			return 'X';
		else return '!'; // error
	} // end of binaryAdd


	public static String binAdd (String binary) { // this method takes a string input of binary 01's

		int totalLength = binary.length(); // stores what total length should be in the end for padding

		long value = Long.parseLong(binary, 2); // integer holds initial value
		value++; // long holds value +1

		//want a string in binary
		binary = Long.toBinaryString(value); // string holds value in base 2

		// want to pad
		binary = String.format("%0"+totalLength+"d", Long.parseLong(binary));

		return binary;
	}


	// ****** MAIN METHOD *******
	public static void main(String[] args) {

		// declaring streams
		Scanner key = new Scanner(System.in); // for user keyboard input
		PrintWriter writer = null; // to write to file, set to null to open in try-catch
		int userNbr = 0; // for compiler
		int intMode = 0; // 1 = recursion, 2 = iteration
		String strMode = ""; // declaring string that will hold current mode
		boolean done = false; // boolean if T, jump to end of program, while False, loop
		String m1 = "1 - Recursion", m2 = "2 - Iteration", m3 = "3 - Quit Program"; // strings for modes
		long startTimer, endTimer; // declaring long vars for timing
				

		// WELCOME
		System.out.println("Welcome to the Tic-Tac-Toe program!\n\n");// greeting


		while (!done) { // outer while

			// USER MODE SELECTION

			System.out.println("Please select the mode.\n"+m1+"\n"+m2+"\n"+m3+"\n");// user selection
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
				System.out.println("\nYou selected "+m1+". "); // user input feedback
				break;
			case 2:
				System.out.println("\nYou selected "+m2+". "); // user input feedback
				break;				
			case 3:
				System.out.println("\nYou selected "+m3+". "); // user input feedback
				break;						
			}	//switch

			// IF USER QUITS
			if (intMode == 3) //if user selected quit
				done = true; // jumps to end of program

			// ELSE - PROGRAM CONTINUES
			if (!done) { //only continues if user didn't select quit

				if (intMode == 1)
					strMode = "Recursion"; // sets string for printing
				if (intMode == 2)
					strMode = "Iteration"; // sets string for printing


				// USER INPUT	
				System.out.println("Please select the maximum number of "+strMode+" UnHiding hidden characters:");// prompting user
				try {
					userNbr = key.nextInt(); // getting user input
					System.out.println("You entered "+userNbr+"."); // user input feedback

					if (userNbr < 1) { // if invalid input
						key.close(); // closing stream for compiler
						throw new IOException();	
					} // if
				} // try	
				catch (IOException e) {
					System.out.println("Error. Invalid Input.");
				} // catch
				
				startTimer = 0;// resetting
				endTimer = 0;
				
				startTimer = System.currentTimeMillis(); // Start timer
				
				// GENERATING RANDOM SEQUENCES
				int rndNbr; // will hold a random integer
				Random rand = new Random(); // will help generate random integer
				String seq; // will hold random-generated sequence


				try {	
					writer = new PrintWriter(new FileOutputStream ("Output_"+strMode+".txt"), true); // will append for looping
					writer.println("\n*********** OUTPUT FILE ("+strMode+") ***********\n\n\n"); // few lines at the beginning
				} //try

				catch (FileNotFoundException e) {
					System.out.println("Error. File Not Found.");
				} // catch


				for (int i = 2; i <= userNbr; i+=2) {	// will iterate until user-defined upper bound

					rndNbr = rand.nextInt(11)+5; // will generate a number between 5-15
					seq = ""; // resetting to empty
					int rndChar = 0; // will generate either 'O' or 'X'
					int o = 79;
					int x = 88;


					for (int k = 0; k<rndNbr;k++) { // this loop creates a sequence of XO's
						rndChar = rand.nextBoolean() ? o : x; // chooses between 79 and 88			
						seq += (char)rndChar; // adds either 'O' or 'X'
						//79 and 88
					}// for 3

					for (int j = 0; j < i; j++) { // this loop adds the # tail		

						rndNbr = rand.nextInt(seq.length()+1); // generates a random index in the string
						seq = seq.substring(0, rndNbr).concat("#").concat(seq.substring(rndNbr)); // will insert '#' randomly			
					} // for 2

					writer.println("--------------\n\nn = "+i+"\n\n"+"Randomized Sequence:\n"+ seq+"\n\n");

					if (intMode == 1) // recursive
						unHideRec(seq, writer); // calling recursive method
					if (intMode ==2) // iterative
						unHideIter(seq, writer, i); // calling iterative method				


					writer.println("\n"); // lines for separation
					writer.flush(); //flushing writer

				} //for 1

				// ** END OF OPERATION **
				endTimer = System.currentTimeMillis(); // stop timer			
				System.out.println("\nOperation Completed. The operation took "+(endTimer - startTimer)+" ms."); 
						
				// PROMPT FOR CONTINUE Y/N	
				String yn = "";						
				System.out.println("Would you like to continue? Y/N"); // prompting user whether to close program
				
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
		if (writer != null)
			writer.close(); // compiler
		System.exit(0);// closing program



	}// main






} // Driver2
