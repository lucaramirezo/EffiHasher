import java.io.Serializable;

public class ListaDinamica implements Serializable{
		private int size;
		private Node begin;
		private Node last;
				
		public ListaDinamica() {
			size = 0;
			begin = null;
		}
		
		public boolean add(int indice, Object o) {
			if ((indice > 0) && (indice < size)) {
				Node aux;
				if (indice < (size/2)) {
					aux = begin;
					int i= 0;
					while (i < indice) {
						aux = aux.getNext();
						i++;
					}

				}
				else {
					aux = last;
					int i = size  - 1;
					while (i > indice ) {
						aux = aux.getBack();
						i--;
					}
				}
					
				Node newNode = new Node (o,aux, aux.getBack());
				aux.getBack().setNext(newNode);
				aux.setBack(newNode);
				size++;
				return true;
			}
				else if(indice == 0) {
					return addPrimero(o);
				}
				else if(indice == size) {
					return addUltimo(o);
			}
			return false;
			
		}
		
		
		public boolean addPrimero(Object o) {
			Node newNode = new Node(o,begin, null);
			if (isEmpty()) {
				begin = last = newNode;			}
			else {
				begin.setBack(newNode);
				begin = newNode;
			}
			size++;
			return true;
			
		}
		
		public boolean addUltimo(Object o) {
			Node newNode = new Node(o,null, last);
			if (isEmpty()) {
				begin = last = newNode;
			}
			else {
				last.setNext(newNode);
				last = newNode;
			}
			size++;
			return true;
		
		}
		
		public boolean isEmpty() {
			return (size ==0)? true:false;
		}
		
		public Object get (int index) {
			if (index >= 0 && index < size) {
				Node aux;
				if(index < size/2) {
					aux = begin;
					int i = 0;
					while (i < index) {
						aux = aux.getNext();
						i++;
					}
				}
				else {
					aux = last;
					int i = size-1;
					while (i > index) {
						aux = aux.getBack();
						i--;
					}
				}
				return aux.getData();
			}
			return null;
		}
		
		public boolean set (Object o) {
			Node aux = begin;
			int i = 0;
			while (i <size-1) {
				if (o.equals(aux.getData())) {
					aux.setData(o);
				return true;
				}
				aux = aux.getNext();
				i++;
			}
			
			return false;
		}
		
			
				

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}
		
		public Object remove (int indice ) {
			if ((indice > 0) && (indice < size-1)) {
				Node aux;
				if (indice < (size/2)) {
					aux = begin;
					int i= 0;
					while (i < indice) {
						aux = aux.getNext();
						i++;
					}

				}
				else {
					aux = last;
					int i = size  - 1;
					while (i > indice ) {
						aux = aux.getBack();
						i--;
					}
				}
					
				Object data = aux.getData();
				aux.getBack().setNext(aux.getNext());
				aux.getNext().setBack(aux.getBack());
				
				size--;
				return data;
			}
				else if(indice == 0) {
					return removeFirst();
				}
				else if(indice == size-1) {
					return removeLast();
			}
			return null;
			
		}
		

		public Object removeFirst() {
			if(!isEmpty()) {
				Object oRet = begin.getData();
				if(size > 1) {
					begin = begin.getNext();
					begin.setBack(null);
				}
				else {
					begin = last = null;
				}
				size--;
				return oRet;
			}
			
			return null;
		}
		public Object removeLast() {
			if(!isEmpty()) {
				Object oRet = last.getData();
				if(size > 1) {
					last = last.getBack();
					last.setNext(null);
				}
				else {
					begin = last = null;
				}
				size--;
				return oRet;
			}
			
			return null;
		}
		
		public int indexOf(Object o) {
			Node aux = begin;
			int i = 0;
			while (i<size-1) {
				if (o.equals(aux.getData())) {
					return i;
				}
				aux = aux.getNext();
				i++;
			}
			return -1;
		}
		
		public int remove(Object o) {
			Node aux = begin;
			int i = 0;
			while (i<size) {
				if (o.equals(aux.getData())) {
					if (i > 0 && i < size - 1) {
						aux.getBack().setNext(aux.getNext());
						aux.getNext().setBack(aux.getBack());
						size--;
					}
					else if (i == 0) {
						removeFirst();

					}
					else {
						removeLast();
					}
					return i;
				}
				aux = aux.getNext();
				i++;
				
			}
			return -1;
		}
		public boolean serializarFichero(String rutaFichero) {
			FileUtil f= new FileUtil(rutaFichero);
			return f.serializeToFile(this);
			
		}
		public void deserializarFichero(String rutaFichero) {
			FileUtil f = new FileUtil(rutaFichero);
			ListaDinamica l = (ListaDinamica) f.deserializeFromFile();
			if(l!=null) {
				this.size = l.size;
				this.begin = l.begin;
				this.last = l.last;
			}
		}
	
		@Override
		public String toString() {
			String lista = "";
			Node aux = begin;
			while(aux != null) {
				lista += aux.getData() +" - ";
				aux = aux.getNext();
			}
			return lista;
		}

		public boolean escribirFicheroTexto(String pathFile) {
			FileUtil f = new FileUtil(pathFile);
			Node aux=begin;
			String text = "";
			while(aux!=null) {
				text+=aux.getData()+"\n";
				aux=aux.getNext();
			}
			return f.writeToFile(text);
		}
		public String leerFicheroTexto(String rutaFichero) {
			FileUtil f = new FileUtil(rutaFichero);
			return  f.readFromFile();
			
		}
		
		
		
		
			

	}

		


