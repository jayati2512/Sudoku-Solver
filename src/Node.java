public class Node {

	// links to neighbours
	protected Node left;
	protected Node right;
	protected Node up;
	protected Node down;

	// column header
	protected Header head;

	protected int row;

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Node getUp() {
		return up;
	}

	public void setUp(Node up) {
		this.up = up;
	}

	public Node getDown() {
		return down;
	}

	public void setDown(Node down) {
		this.down = down;
	}

	public Header getHead() {
		return head;
	}

	public void setHead(Header head) {
		this.head = head;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public Node() {
		this(-1, null);
	}

	public Node(int row) {
		this(row, null);

	}

	public Node(int row, Header head) {
		this.left = this;
		this.right = this;
		this.up = this;
		this.down = this;
		this.row = row;
		this.head = head;
	}
}
