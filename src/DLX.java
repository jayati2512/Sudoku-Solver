import java.util.Stack;

public class DLX {

	protected Header header;
	protected Header[] colHeader;
	protected NodePtr[] rowHeader;
	public int solution[][];
	protected int totalSolutions;
	protected Stack<Node> sudokuSolution;
	public boolean foundSolution;

	public DLX() {
		this.header = new Header(0);
		this.sudokuSolution = new Stack<Node>();
		this.totalSolutions = 0;
	}

	/**
	 * prints solution of sudoku
	 */
	public void printSolution() {
		if (sudokuSolution.size() == 0) {
			System.out.println("No solution present.");
			return;
		}
		System.out.println("Writing a solution");
		while (!sudokuSolution.empty())
			System.out.println(sudokuSolution.pop());
	}

	/**
	 * initialize row and column headers
	 * 
	 * @param row
	 * @param column
	 */
	public void initializeHeaders(int row, int column) {
		colHeader = new Header[column];
		rowHeader = new NodePtr[row];

		// initializing row header
		for (int i = 0; i < row; i++) {
			rowHeader[i] = new NodePtr();
		}

		// initializing column header
		for (int i = 0; i < column; i++) {
			colHeader[i] = new Header(i);
			colHeader[i].setLeft(header.getLeft());
			colHeader[i].setRight(header);
			header.getLeft().setRight(colHeader[i]);
			header.setLeft(colHeader[i]);
		}
	}

	/**
	 * add a row in contraint matrix
	 * 
	 * @param row
	 *            index of row
	 * @param position
	 *            array of positions where node is to be inserted
	 * @throws Exception
	 */
	public void addRowToMatrix(int row, int[] position) throws Exception {

		if (position.length == 0) {
			throw new Exception("Array position cannot be empty");
		}

		if (row >= rowHeader.length
				|| position[position.length - 1] >= colHeader.length) {
			throw new ArrayIndexOutOfBoundsException(
					"Position of node exceeds columns header");
		}

		Node firstNode = new Node(row);
		firstNode.setHead(colHeader[position[0]]);
		firstNode.getHead().setHeaderSize(
				firstNode.getHead().getHeaderSize() + 1);
		firstNode.setDown(colHeader[position[0]]);
		firstNode.setUp(colHeader[position[0]].getUp());
		colHeader[position[0]].getUp().setDown(firstNode);
		colHeader[position[0]].setUp(firstNode);
		rowHeader[row].setNode(firstNode);

		for (int i = 1; i < position.length; i++) {
			Node tempNode = new Node(row);

			// head
			tempNode.setHead(colHeader[position[i]]);
			tempNode.getHead().setHeaderSize(
					tempNode.getHead().getHeaderSize() + 1);

			// left to right
			tempNode.setLeft(firstNode.getLeft());
			tempNode.setRight(firstNode);
			firstNode.getLeft().setRight(tempNode);
			firstNode.setLeft(tempNode);

			// up to down
			tempNode.setDown(colHeader[position[i]]);
			tempNode.setUp(colHeader[position[i]].getUp());
			colHeader[position[i]].getUp().setDown(tempNode);
			colHeader[position[i]].setUp(tempNode);

		}
	}

	/**
	 * finds the column header having the minimum size
	 * 
	 * @return
	 */
	protected Header findMinimumHeader() {
		int minimum = Integer.MAX_VALUE;
		Header minimumHeader = null;
		Header tempHeader = (Header) header.getRight();

		while (tempHeader != header) {
			if (tempHeader.getHeaderSize() < minimum) {
				minimum = tempHeader.getHeaderSize();
				minimumHeader = tempHeader;
			}
			tempHeader = (Header) tempHeader.getRight();
		}
		return minimumHeader;
	}

	/**
	 * performs the Cover operation for one column as per Knuth DLX algorithm
	 * 
	 * @param colHeader
	 *            the header of the column to cover
	 */
	protected void coverCol(Header colHeader) {
		// header unlinking
		colHeader.getLeft().setRight(colHeader.getRight());
		colHeader.getRight().setLeft(colHeader.getLeft());

		// nodes unlinking
		Node k = colHeader.getDown();
		Node m = null;
		while (k != colHeader) {
			m = k.getRight();
			while (m != k) {
				m.getUp().setDown(m.getDown());
				m.getDown().setUp(m.getUp());
				m.getHead().setHeaderSize(m.getHead().getHeaderSize() - 1);
				m = m.getRight();
			}
			k = k.getDown();
		}
	}

	/**
	 * performs the Uncover operation for one column as per Knuth DLX algorithm
	 * 
	 * @param colHeader
	 *            the header of the column to uncover
	 */
	protected void uncoverCol(Header colHeader) {

		// linking the nodes
		Node i = colHeader.getUp();
		Node j = null;
		while (i != colHeader) {
			j = i.getLeft();
			while (j != i) {
				j.getUp().setDown(j);
				j.getDown().setUp(j);
				j.getHead().setHeaderSize(j.getHead().getHeaderSize() + 1);
				j = j.getLeft();
			}
			i = i.getUp();
		}

		// linking the header
		colHeader.getLeft().setRight(colHeader);
		colHeader.getRight().setLeft(colHeader);
	}

	/**
	 * selects a matrix row to check the solution
	 * 
	 * @param index
	 *            index in rowHeader[] of the row to select
	 */
	public void chooseRow(int index) {
		Node node = rowHeader[index].getNode();
		sudokuSolution.push(node);
		do {
			coverCol(node.getHead());
			node = node.getRight();
		} while (node != rowHeader[index].getNode());
	}

	/**
	 * deselects the last row which was pushed on the stack
	 */
	public void deSelectRow() {
		Node n = ((Node) sudokuSolution.pop()).getLeft();
		do {
			uncoverCol(n.getHead());
			n = n.getLeft();
		} while (n != rowHeader[n.getRow()].getNode());
		uncoverCol(n.getHead());
	}

	/**
	 * deselects all rows which were pushed on the stack
	 */
	public void deselectAllRows() {
		while (sudokuSolution.size() > 0) {
			deSelectRow();
		}
	}

	/**
	 * Knuth's DLX algorithm with printing the solution
	 */
	protected void solveSudokuRecursively() {

		if (header.getRight() == header) {
			totalSolutions++;
			System.out.print("\nSolution #" + totalSolutions + "\n\n");
			printSolution();
			return;
		}

		Header minHeader = findMinimumHeader();

		// return if its not a good solution
		if (minHeader.getHeaderSize() == 0)
			return;

		coverCol(minHeader);

		Node r = minHeader.getDown();
		Node j = null;
		while (r != minHeader) {
			sudokuSolution.push(r);
			j = r.getRight();
			while (j != r) {
				coverCol(j.getHead());
				j = j.getRight();
			}

			// recursive call
			solveSudokuRecursively();

			// if previous cover was wrong then uncover it and continue
			r = (Node) sudokuSolution.pop();
			minHeader = r.getHead();
			j = r.getLeft();
			while (j != r) {
				uncoverCol(j.getHead());
				j = j.getLeft();
			}
			r = r.getDown();
		}
		uncoverCol(minHeader);
	}
}
