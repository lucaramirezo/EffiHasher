
public class Main {

	public static void main(String[] args) {
		Hashstable tabla = new Hashstable(10,0.7);
		for ( int i = 0; i < 7; i++) {
			tabla.put( new KeyValue("mat"+ i, new Moto("mat"+i, "marca"+i)));
		}

	}

}
