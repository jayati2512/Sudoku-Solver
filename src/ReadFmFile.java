import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ReadFmFile {

	static Integer[][] readFromFile(String path) {

		Integer[][] sudoku = new Integer[9][9];
		String input = "";
		try {
			// read input from file
			FileInputStream fstream = new FileInputStream(path);

			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			while ((strLine = br.readLine()) != null) {
				input += strLine;
			}

			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		if (input.length() != 81) {
			return null;
		}

		int count = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sudoku[i][j] = Integer.parseInt(input.charAt(count++) + "");
			}
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sudoku[i][j] != 0) {
					int temp = sudoku[i][j];
					int check = 0;
					for (int k = 0; k < 9; k++) {
						if (sudoku[i][k] == temp)
							check++;
					}
					if (check > 1) {
						// Invalid input row check
						return null;
					}
				}
			}
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sudoku[j][i] != 0) {
					int temp = sudoku[j][i];
					int check = 0;
					for (int k = 0; k < 9; k++) {
						if (sudoku[k][i] == temp)
							check++;
					}
					if (check > 1) {
						// Invalid input column check
						return null;
					}
				}
			}
		}

		for (int i = 0; i < 9; i = i + 3) {
			for (int j = 0; j < 9; j = j + 3) {
				for (int k = i; k < i + 3; k++) {
					for (int l = j; l < j + 3; l++) {
						if (sudoku[k][l] != 0) {
							int temp = sudoku[k][l];
							int check = 0;
							for (int m = i; m < i + 3; m++) {
								for (int n = j; n < j + 3; n++) {
									if (sudoku[m][n] == temp) {
										check++;
									}
								}
							}
							if (check > 1) {
								// Invalid input box check
								return null;
							}
						}
					}
				}
			}
		}
		return sudoku;
	}
}