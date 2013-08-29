public class Header extends Node {
	private int headerSize;
	private int headerName;

	public int getHeaderSize() {
		return headerSize;
	}

	public void setHeaderSize(int headerSize) {
		this.headerSize = headerSize;
	}

	public int getHeaderName() {
		return headerName;
	}

	public void setHeaderName(int headerName) {
		this.headerName = headerName;
	}

	public Header() {
		this(0);
	}

	public Header(int name) {
		this.headerName = name;
		this.headerSize = 0;
		this.head = this;
	}

}