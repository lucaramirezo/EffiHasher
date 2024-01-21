
public class Main {

	public static void main(String[] args) {
		HashTable tabla = new HashTable(10,0.7);
		for ( int i = 0; i < 7; i++) {
			tabla.put( new KeyValue("mat"+ i, new Moto("mat"+i, "marca"+i)));
		}
	}

}
