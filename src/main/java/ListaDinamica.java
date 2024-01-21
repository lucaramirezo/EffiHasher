import java.io.Serializable;

/**
 * Una implementación de lista dinámica doblemente enlazada.
 * Esta estructura de datos permite almacenar objetos de manera secuencial y dinámica.
 */
public class ListaDinamica implements Serializable {
	private int size; // Número de elementos en la lista
	private Node begin; // Referencia al primer nodo de la lista
	private Node last; // Referencia al último nodo de la lista

	/**
	 * Constructor para la lista dinámica. Inicializa la lista vacía.
	 */
	public ListaDinamica() {
		size = 0;
		begin = null;
	}

	/**
	 * Añade un objeto a la lista en la posición especificada.
	 *
	 * @param indice Índice en el que se insertará el objeto.
	 * @param o      El objeto a insertar.
	 * @return true si la inserción fue exitosa, false en caso contrario.
	 */
	public boolean add(int indice, Object o) {
		if ((indice > 0) && (indice < size)) {
			Node aux;
			if (indice < (size / 2)) {
				aux = begin;
				int i = 0;
				while (i < indice) {
					aux = aux.getNext();
					i++;
				}

			} else {
				aux = last;
				int i = size - 1;
				while (i > indice) {
					aux = aux.getBack();
					i--;
				}
			}

			Node newNode = new Node(o, aux, aux.getBack());
			aux.getBack().setNext(newNode);
			aux.setBack(newNode);
			size++;
			return true;
		} else if (indice == 0) {
			return addPrimero(o);
		} else if (indice == size) {
			return addUltimo(o);
		}
		return false;

	}

	/**
	 * Añade un objeto al principio de la lista.
	 *
	 * @param o El objeto a añadir.
	 * @return true siempre que la inserción es exitosa.
	 */
	public boolean addPrimero(Object o) {
		Node newNode = new Node(o, begin, null);
		if (isEmpty()) {
			begin = last = newNode;
		} else {
			begin.setBack(newNode);
			begin = newNode;
		}
		size++;
		return true;

	}

	/**
	 * Añade un objeto al final de la lista.
	 *
	 * @param o El objeto a añadir.
	 * @return true siempre que la inserción es exitosa.
	 */
	public boolean addUltimo(Object o) {
		Node newNode = new Node(o, null, last);
		if (isEmpty()) {
			begin = last = newNode;
		} else {
			last.setNext(newNode);
			last = newNode;
		}
		size++;
		return true;

	}

	/**
	 * Comprueba si la lista está vacía.
	 *
	 * @return true si la lista está vacía, false en caso contrario.
	 */
	public boolean isEmpty() {
		return (size == 0);
	}

	/**
	 * Obtiene el objeto en la posición especificada de la lista.
	 *
	 * @param index Índice del objeto a obtener.
	 * @return El objeto en la posición especificada, o null si el índice es inválido.
	 */
	public Object get(int index) {
		if (index >= 0 && index < size) {
			Node aux;
			if (index < size / 2) {
				aux = begin;
				int i = 0;
				while (i < index) {
					aux = aux.getNext();
					i++;
				}
			} else {
				aux = last;
				int i = size - 1;
				while (i > index) {
					aux = aux.getBack();
					i--;
				}
			}
			return aux.getData();
		}
		return null;
	}

	/**
	 * Reemplaza el objeto en la lista que es igual al objeto proporcionado.
	 *
	 * @param o El objeto con el que reemplazar el objeto en la lista.
	 * @return true si se encontró y reemplazó el objeto, false en caso contrario.
	 */
	public boolean set(Object o) {
		Node aux = begin;
		int i = 0;
		while (i < size - 1) {
			if (o.equals(aux.getData())) {
				aux.setData(o);
				return true;
			}
			aux = aux.getNext();
			i++;
		}

		return false;
	}

	/**
	 * Obtiene el tamaño actual de la lista.
	 *
	 * @return El tamaño de la lista.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Establece el tamaño de la lista. Útil después de la deserialización.
	 *
	 * @param size El nuevo tamaño de la lista.
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Elimina el objeto en la posición especificada de la lista.
	 *
	 * @param indice Índice del objeto a eliminar.
	 * @return El objeto eliminado, o null si el índice es inválido.
	 */
	public Object remove(int indice) {
		if ((indice > 0) && (indice < size - 1)) {
			Node aux;
			if (indice < (size / 2)) {
				aux = begin;
				int i = 0;
				while (i < indice) {
					aux = aux.getNext();
					i++;
				}

			} else {
				aux = last;
				int i = size - 1;
				while (i > indice) {
					aux = aux.getBack();
					i--;
				}
			}

			Object data = aux.getData();
			aux.getBack().setNext(aux.getNext());
			aux.getNext().setBack(aux.getBack());

			size--;
			return data;
		} else if (indice == 0) {
			return removeFirst();
		} else if (indice == size - 1) {
			return removeLast();
		}
		return null;

	}

	/**
	 * Elimina el primer objeto de la lista.
	 *
	 * @return El objeto eliminado, o null si la lista está vacía.
	 */
	public Object removeFirst() {
		if (!isEmpty()) {
			Object oRet = begin.getData();
			if (size > 1) {
				begin = begin.getNext();
				begin.setBack(null);
			} else {
				begin = last = null;
			}
			size--;
			return oRet;
		}

		return null;
	}

	/**
	 * Elimina el último objeto de la lista.
	 *
	 * @return El objeto eliminado, o null si la lista está vacía.
	 */
	public Object removeLast() {
		if (!isEmpty()) {
			Object oRet = last.getData();
			if (size > 1) {
				last = last.getBack();
				last.setNext(null);
			} else {
				begin = last = null;
			}
			size--;
			return oRet;
		}

		return null;
	}

	/**
	 * Devuelve el índice del primer objeto en la lista que es igual al objeto proporcionado.
	 *
	 * @param o El objeto a buscar.
	 * @return El índice del objeto, o -1 si no se encuentra en la lista.
	 */
	public int indexOf(Object o) {
		Node aux = begin;
		int i = 0;
		while (i < size - 1) {
			if (o.equals(aux.getData())) {
				return i;
			}
			aux = aux.getNext();
			i++;
		}
		return -1;
	}

	/**
	 * Elimina el primer objeto en la lista que es igual al objeto proporcionado.
	 *
	 * @param o El objeto a eliminar.
	 * @return El índice del objeto eliminado, o -1 si no se encuentra en la lista.
	 */
	public int remove(Object o) {
		Node aux = begin;
		int i = 0;
		while (i < size) {
			if (o.equals(aux.getData())) {
				if (i > 0 && i < size - 1) {
					aux.getBack().setNext(aux.getNext());
					aux.getNext().setBack(aux.getBack());
					size--;
				} else if (i == 0) {
					removeFirst();

				} else {
					removeLast();
				}
				return i;
			}
			aux = aux.getNext();
			i++;

		}
		return -1;
	}

	/**
	 * Serializa la lista y la almacena en un archivo.
	 *
	 * @param rutaFichero Ruta del archivo donde se almacenará la lista.
	 * @return true si la serialización y almacenamiento fue exitosa, false en caso contrario.
	 */
	public boolean serializarFichero(String rutaFichero) {
		FileUtil f = new FileUtil(rutaFichero);
		return f.serializeToFile(this);

	}

	/**
	 * Deserializa la lista desde un archivo.
	 *
	 * @param rutaFichero Ruta del archivo desde donde se leerá y deserializará la lista.
	 */
	public void deserializarFichero(String rutaFichero) {
		FileUtil f = new FileUtil(rutaFichero);
		ListaDinamica l = (ListaDinamica) f.deserializeFromFile();
		if (l != null) {
			this.size = l.size;
			this.begin = l.begin;
			this.last = l.last;
		}
	}

	/**
	 * Devuelve una representación en forma de String de la lista.
	 *
	 * @return String que representa el contenido de la lista.
	 */
	@Override
	public String toString() {
		String lista = "";
		Node aux = begin;
		while (aux != null) {
			lista += aux.getData() + " - ";
			aux = aux.getNext();
		}
		return lista;
	}

	/**
	 * Escribe el contenido de la lista en un archivo de texto.
	 *
	 * @param pathFile Ruta del archivo donde se escribirá el contenido de la lista.
	 * @return true si la escritura fue exitosa, false en caso contrario.
	 */
	public boolean escribirFicheroTexto(String pathFile) {
		FileUtil f = new FileUtil(pathFile);
		Node aux = begin;
		String text = "";
		while (aux != null) {
			text += aux.getData() + "\n";
			aux = aux.getNext();
		}
		return f.writeToFile(text);
	}

	/**
	 * Lee el contenido de un archivo de texto y lo devuelve como un String.
	 *
	 * @param rutaFichero Ruta del archivo de texto a leer.
	 * @return String que representa el contenido del archivo.
	 */
	public String leerFicheroTexto(String rutaFichero) {
		FileUtil f = new FileUtil(rutaFichero);
		return f.readFromFile();

	}

}
