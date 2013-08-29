import java.util.Arrays;

public class SolveSudoku extends DLX {

	// dimension for the sudoku matrix
	protected int matrixDim;

	// dimension for the block
	private int blockDim;

	public SolveSudoku(int matrixDim) {
		this.matrixDim = matrixDim;
		blockDim = (int) Math.sqrt(matrixDim);
	}

	public int getMatrixDim() {
		return matrixDim;
	}

	public void setMatrixDim(int matrixDim) {
		this.matrixDim = matrixDim;
	}

	public int getBlockDim() {
		return blockDim;
	}

	public void setBlockDim(int blockDim) {
		this.blockDim = blockDim;
	}

	/**
	 * builds the contraint matrix
	 */
	public void fillConstraintMatrix() {

		// initialize the contraint matrix column headers
		initializeHeaders(matrixDim * matrixDim * matrixDim, 4 * matrixDim
				* matrixDim);

		// initialize the contraint matrix rows
		int b = 0;
		for (int r = 0; r < matrixDim; r++) {
			for (int c = 0; c < matrixDim; c++) {

				// initialize the contraint matrix blocks
				b = (r / blockDim) * blockDim + (c / blockDim);
				for (int d = 0; d < matrixDim; d++) {
					try {
						addRowToMatrix(r * matrixDim * matrixDim + c
								* matrixDim + d, new int[] { r * matrixDim + c,
								matrixDim * (matrixDim + r) + d,
								matrixDim * (c + 2 * matrixDim) + d,
								matrixDim * (b + 3 * matrixDim) + d });
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * initialize the constraint matrix
	 * 
	 * @param inputSudoku
	 *            the input sudoku matrix from the user
	 */
	public void initializeConstraintMatrix(Integer[][] inputSudoku) {
		try {
			for (int r = 0; r < matrixDim; r++) {
				for (int c = 0; c < matrixDim; c++) {
					if (inputSudoku[r][c] != 0) {
						chooseRow(r * matrixDim * matrixDim + c * matrixDim
								+ ((inputSudoku[r][c]) - 1));
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("Error in initializeConstraintMatrix "
					+ ex.getMessage());
		}
	}

	/**
	 * solving the sudoku, returning true if an unique solution is found
	 * 
	 * @return
	 */
	public boolean solveSudoku() {
		totalSolutions = 0;
		solveSudokuRecursively();
		System.out.println(totalSolutions + " solution"
				+ (totalSolutions > 1 ? "s found." : " found."));
		deselectAllRows();
		return (totalSolutions == 1);
	}

	/**
	 * printing the generated sudoku solution(s)
	 */
	@Override
	public void printSolution() {

		int[] finalSol = new int[sudokuSolution.size()];
		int index1 = 0, index2 = 0;

		while (index2 < sudokuSolution.size()) {
			finalSol[index1++] = ((Node) sudokuSolution.get(index2)).getRow();
			index2++;
		}
		Arrays.sort(finalSol);
		for (int k = 0; k < finalSol.length; k++) {
			finalSol[k] = finalSol[k] % matrixDim;
		}

		for (int k = 0; k < matrixDim; k++) {
			if (k == 3 || k == 6)
				System.out.println("-------------------------------");
			for (int m = 0; m < matrixDim; m++) {
				if (m == 3 || m == 6)
					System.out.print("|  ");
				System.out.print((finalSol[k * matrixDim + m] + 1) + "  ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
