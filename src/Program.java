import java.util.Date;

public class Program {

	public static void main(String[] args) {

		int sudokuDimension = 9;
		System.out.println("Sudoku Solver started.");

		// user input sudoku
		String inputFile = "input.txt";

		// read the user sudoku input and check its validity
		Integer[][] inputSudoku = ReadFmFile.readFromFile(inputFile);
		if (inputSudoku == null) {
			System.out
					.println("\nYour input was invalid !!! \n\nSudoku Solver stopped.");
			System.exit(0);
		}

		Date start1 = new Date();

		SolveSudoku sudokuDLX = new SolveSudoku(sudokuDimension);
		sudokuDLX.fillConstraintMatrix();

		System.out.println("\nOriginal SUDOKU :\n");

		for (int i = 0; i < 9; i++) {
			if (i == 3 || i == 6)
				System.out.println("-------------------------------");
			for (int j = 0; j < 9; j++) {

				if (j == 3 || j == 6)
					System.out.print("|  ");
				System.out.print(inputSudoku[i][j] + "  ");
			}
			System.out.println();
		}

		System.out.println("\nSolved SUDOKU : \n");
		sudokuDLX.initializeConstraintMatrix(inputSudoku);
		sudokuDLX.solveSudoku();

		Date start2 = new Date();

		// time taken to solve the sudoku
		System.out.println("\nTime taken = "
				+ (start2.getTime() - start1.getTime()) + " ms");

		System.out.println("\nSudoku Solver stopped.");
	}

}
