
public class Main {

	public static void main(String[] args) {
		testDinamicHashTable();
		testStaticHashTable();
	}

	private static void testStaticHashTable() {
		StaticHashTable hashTable = new StaticHashTable(10);

		// Prueba de put y get
		hashTable.put("clave1", "valor1");
		hashTable.put("clave2", "valor2");
		hashTable.put("clave3", "valor3");

		System.out.println("Prueba de put y get:");
		System.out.println("Clave1: " + hashTable.get("clave1"));
		System.out.println("Clave2: " + hashTable.get("clave2"));
		System.out.println("Clave3: " + hashTable.get("clave3"));

		// Prueba de put con clave duplicada
		boolean result = hashTable.put("clave1", "nuevo_valor");
		System.out.println("\nPrueba de put con clave duplicada:");
		System.out.println("Resultado: " + result);
		System.out.println("Clave1 después de duplicada: " + hashTable.get("clave1"));

		// Prueba de remove
		hashTable.remove("clave2");
		System.out.println("\nPrueba de remove:");
		System.out.println("Clave2 después de remove: " + hashTable.get("clave2"));

		// Prueba de replace
		hashTable.replace(new StaticHashTable.KeyValue("clave3", "nuevo_valor3"));
		System.out.println("\nPrueba de replace:");
		System.out.println("Clave3 después de replace: " + hashTable.get("clave3"));

		// Prueba de printAll
		System.out.println("\nPrueba de printAll:");
		hashTable.printAll();
	}

	private static void testDinamicHashTable() {
		// Crear una HashTable con capacidad inicial y factor de carga máxima
		HashTable ht = new HashTable(5, 0.75);

		// Insertar elementos en la tabla hash
		System.out.println("Insertando elementos...");
		ht.put(new KeyValue("clave1", new Moto("jaja equis", "negro")));
		ht.put(new KeyValue("clave2", "valor2"));
		ht.put(new KeyValue("clave3", "valor3"));
		ht.put(new KeyValue("clave4", "valor4"));

		// Imprimir todos los elementos
		System.out.println("Elementos después de las inserciones:");
		ht.printAll();

		// Reemplazar un elemento
		System.out.println("Reemplazando 'clave2'...");
		ht.replace(new KeyValue("clave2", "valorNuevo2"));

		// Imprimir todos los elementos
		System.out.println("Elementos después del reemplazo:");
		ht.printAll();

		// Obtener un elemento
		System.out.println("Obteniendo el valor para 'clave3': " + ht.get("clave3"));

		// Eliminar un elemento
		System.out.println("Eliminando 'clave3'...");
		ht.remove("clave3");

		// Imprimir todos los elementos
		System.out.println("Elementos después de la eliminación:");
		ht.printAll();

		// Probar el rehashing insertando más elementos para superar el factor de carga
		System.out.println("Insertando más elementos para probar el rehashing...");
		ht.put(new KeyValue("clave5", "valor5"));
		ht.put(new KeyValue("clave6", "valor6")); // Esto debería provocar el rehashing

		// Imprimir todos los elementos
		System.out.println("Elementos después del rehashing:");
		ht.printAll();

		// Serializar y deserializar la tabla hash
		ht.serializarFichero();
		ht.deserializarFichero();
		System.out.println("Elementos después de la deserialización:");
		ht.printAll();

		// Escribir y leer la tabla hash en/desde un archivo de texto
		ht.escribirFicheroTexto();
		ht.leerFicheroTexto();
		System.out.println("Elementos después de leer el archivo de texto:");
		ht.printAll();
	}
}
