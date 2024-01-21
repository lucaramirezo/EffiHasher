public class Node {
	//pila
	private Object data;
	private Node next;
	private Node back;
	
	public Node(Object o, Node n, Node b) {
		this.data = o;
		this.next = n;
		this.back = b;
	}

	public Node(Object o) {
		this.data = o;
		this.next = null;
		this.back = null;
	}
	
	public Node(){
		this.data = null;
		this.next = null;
		this.back = null;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Node getBack() {
		return back;
	}

	public void setBack(Node back) {
		this.back = back;
	}
	
	
}