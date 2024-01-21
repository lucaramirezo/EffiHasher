import java.io.Serializable;

/**
 * Representa un nodo en una lista enlazada doble.
 * Cada nodo contiene una referencia a un objeto de datos,
 * así como referencias al nodo siguiente y al nodo anterior en la lista.
 */
public class Node implements Serializable {
	// Atributos de la clase Node
	private Object data; // El objeto de datos almacenado en este nodo
	private Node next;   // Referencia al siguiente nodo en la lista
	private Node back;   // Referencia al nodo anterior en la lista

	/**
	 * Constructor para crear un nuevo nodo con referencias al nodo siguiente y al nodo anterior.
	 *
	 * @param o El objeto de datos para este nodo.
	 * @param n La referencia al siguiente nodo en la lista.
	 * @param b La referencia al nodo anterior en la lista.
	 */
	public Node(Object o, Node n, Node b) {
		this.data = o;
		this.next = n;
		this.back = b;
	}

	/**
	 * Constructor para crear un nuevo nodo sin nodos siguiente o anterior.
	 * Esto es útil para el inicio o fin de una lista enlazada.
	 *
	 * @param o El objeto de datos para este nodo.
	 */
	public Node(Object o) {
		this.data = o;
		this.next = null;
		this.back = null;
	}

	/**
	 * Constructor predeterminado para crear un nodo vacío.
	 * El nodo no contiene datos y no apunta a ningún otro nodo.
	 */
	public Node() {
		this.data = null;
		this.next = null;
		this.back = null;
	}

	/**
	 * Obtiene el objeto de datos almacenado en este nodo.
	 *
	 * @return El objeto de datos.
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Establece o actualiza el objeto de datos para este nodo.
	 *
	 * @param data El nuevo objeto de datos.
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * Obtiene la referencia al siguiente nodo en la lista.
	 *
	 * @return El siguiente nodo.
	 */
	public Node getNext() {
		return next;
	}

	/**
	 * Establece o actualiza la referencia al siguiente nodo en la lista.
	 *
	 * @param next El nuevo siguiente nodo.
	 */
	public void setNext(Node next) {
		this.next = next;
	}

	/**
	 * Obtiene la referencia al nodo anterior en la lista.
	 *
	 * @return El nodo anterior.
	 */
	public Node getBack() {
		return back;
	}

	/**
	 * Establece o actualiza la referencia al nodo anterior en la lista.
	 *
	 * @param back El nuevo nodo anterior.
	 */
	public void setBack(Node back) {
		this.back = back;
	}
}
