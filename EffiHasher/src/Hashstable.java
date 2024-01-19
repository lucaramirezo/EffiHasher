import java.math.*;
public class Hashstable {
	private ListaDinamica[] table;
	private double loadFactor;
	private int capacity;
	private int population;
	private final static String RUTA_TXT = "data\\txt\\";
	private final static String RUTA_BINARY = "data\\txt\\";

	public Hashstable(int c, double max) {
		this.capacity = c;
		this.loadFactor = max;
		this.table = new ListaDinamica[capacity];
		for (int i = 0; i < capacity; i++) {
			table[i] = new ListaDinamica();
		}
	}
	
	private double calculateLoadFactor() {
		return (double)(population / capacity);
	}

	/**
	 * 
	 * @return Devuelve verdadero si (population/capacity)>= loadfactor
	 */
	private boolean checkLoad() {
		if (calculateLoadFactor() >= loadFactor)
			return true;
		return false;
	}

	private int hash(String key) {
		int sum=0;
		for(int i=0;i<key.length();i++) {
			sum+=key.charAt(i)*Math.pow(31,(i+1));
		}
		return (sum%capacity);
	}

	public boolean put(KeyValue kv) {
		if(get(kv.getKey())==null) {
			int pos= hash(kv.getKey());
			table[pos].addUltimo(kv);
			population+=1;
			if(checkLoad())
				reHash();
			return true;
		}
		return false;
	}
	
	public boolean remove(String key) {
		int pos = hash(key);
		int index = table[pos].remove(new KeyValue(key, null));
		if (index != -1) {
			population--;
			return true;

		}
		return false;

	}

	public boolean replace(KeyValue kv) {
		int index = hash(kv.getKey());
		return table[index].set(kv);
	}

	public Object get(String k) {
		int pos=hash(k);
		ListaDinamica lista=table[pos];
		Object res=lista.indexOf(new KeyValue (k,null));
		return res;
		
	}
	private void reHash() {
		int newCapacity=2*capacity;
		Hashstable hsTemp = new Hashstable(newCapacity	, loadFactor);
		for(int i=0;i<capacity;i++) {
			ListaDinamica lista = table[i];
			for(int j=0;j<lista.getSize();j++) {
				hsTemp.put((KeyValue)lista.removeFirst());
			}
		}
		this.table=hsTemp.table;
		this.capacity=newCapacity;
	}

	public void serializarFichero() {
		for ( int i = 0; i< capacity;i++) {
			this.table[i].serializarFichero(RUTA_BINARY+i+".data");
		}
	}
	public void deserializarFichero() {
		for(int i =0; i<capacity;i++) {
			this.table[i].deserializarFichero(RUTA_BINARY+i+".data");
			this.population+= this.table[i].getSize();
		}
	}
	public void escribirFicheroTexto() {
		for ( int i =0;i<capacity;i++) {
			this.table[i].escribirFicheroTexto(RUTA_TXT+i+".data");
		}
	}
	public void leerFicheroTexto() {
		for (int i =0;i<capacity;i++) {
			String texto = this.table[i].leerFicheroTexto(RUTA_TXT+i+".data");
			String[] pares = texto.split("\n");
			for ( int j = 0; j<pares.length-1;j++) {
				String [] par = pares[i].split("#");
				String [] atributos = par[1].split("@");
				//TODO ESTO NO SE CAMBIA NO METER COCHE
				KeyValue kv = new KeyValue(par[0], new Moto(atributos[0],atributos[1]));
				this.put(kv);
			}
			
		}
	}
	
}
