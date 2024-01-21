import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashTable implements Serializable {
    private final static String RUTA_TXT = "/home/duo/Desktop/data";
    private final static String RUTA_BINARY = "/home/duo/Desktop/data";
    private final double loadFactor;
    private ListaDinamica[] table;
    private int capacity;
    private int population;

    /**
     * Constructor para HashTable.
     *
     * @param c   capacidad inicial de la tabla hash.
     * @param max factor de carga máximo para la tabla hash.
     */
    public HashTable(int c, double max) {
        this.capacity = c;
        this.loadFactor = max;
        this.table = new ListaDinamica[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new ListaDinamica();
        }
    }

    /**
     * Obtiene el siguiente número primo después de un número dado, utilizado para calcular la capacidad de la tabla.
     *
     * @param num número a partir del cual buscar el siguiente número primo.
     * @return el siguiente número primo.
     */
    private int getNextPrime(int num) {
        while (true) {
            if (!isPrime(num)) {
                num++;
            } else {
                return num;
            }
        }
    }

    /**
     * Comprueba si un número es primo.
     *
     * @param num número a comprobar.
     * @return verdadero si el número es primo, falso en caso contrario.
     */
    private boolean isPrime(int num) {
        if ((num % 2) == 0) {
            return false;
        }
        for (int i = 3; i <= Math.sqrt(num); i += 2) {
            if ((num % i) == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calcula el factor de carga actual de la tabla hash.
     *
     * @return el factor de carga actual.
     */
    private double calculateLoadFactor() {
        return (double) (population / capacity);
    }

    /**
     * Verifica si el factor de carga actual excede el factor de carga máximo permitido.
     *
     * @return verdadero si el factor de carga actual es mayor o igual al máximo permitido, falso en caso contrario.
     */
    private boolean checkLoad() {
        return calculateLoadFactor() > loadFactor;
    }

    /**
     * Calcula el índice de hash para una clave dada utilizando SHA-256.
     *
     * @param key clave para la cual calcular el índice de hash.
     * @return índice de hash calculado.
     */
    private int hash(String key) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(key.getBytes("UTF-8"));
            int hashInt = 0;

            // Convertir los primeros 4 bytes del hash a un entero (para simplificar, podríamos usar más bytes y un tipo de dato más grande si es necesario)
            for (int i = 0; i < 4; i++) {
                hashInt <<= 8; // Desplazar 8 bits a la izquierda
                hashInt |= (hash[i] & 0xFF); // Añadir el siguiente byte del hash
            }

            return Math.abs(hashInt % capacity); // Usar el valor absoluto para asegurarnos de que el índice es positivo
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            // Manejar de alguna manera el error en caso de que el algoritmo SHA-256 no esté disponible
            throw new RuntimeException("SHA-256 no disponible", e);
        }
    }

    /**
     * Inserta un objeto KeyValue en la tabla hash.
     *
     * @param kv objeto KeyValue a insertar.
     * @return verdadero si la inserción fue exitosa, falso si la clave ya existe en la tabla.
     */
    public boolean put(KeyValue kv) {
        if (get(kv.getKey()) == null) {
            int pos = hash(kv.getKey());
            table[pos].addUltimo(kv);
            population += 1;
            if (checkLoad())
                reHash(2);
            return true;
        }
        return false;
    }

    /**
     * Elimina un objeto con la clave dada de la tabla hash.
     *
     * @param key clave del objeto a eliminar.
     * @return verdadero si la eliminación fue exitosa, falso si la clave no se encuentra en la tabla.
     */
    public boolean remove(String key) {
        int pos = hash(key);
        int index = table[pos].remove(new KeyValue(key, null));
        if (index != -1) {
            population--;
            return true;

        }
        return false;

    }

    /**
     * Reemplaza un objeto KeyValue existente en la tabla hash con otro objeto KeyValue dado.
     *
     * @param kv objeto KeyValue con el que reemplazar.
     * @return verdadero si el reemplazo fue exitoso, falso en caso contrario.
     */
    public boolean replace(KeyValue kv) {
        int index = hash(kv.getKey());
        return table[index].set(kv);
    }

    /**
     * Obtiene el objeto asociado a una clave dada en la tabla hash.
     *
     * @param k clave del objeto a obtener.
     * @return objeto asociado a la clave, o null si la clave no se encuentra en la tabla.
     */
    public KeyValue get(String k) {
        int pos = hash(k);
        ListaDinamica lista = table[pos];
        KeyValue res = (KeyValue) lista.get(lista.indexOf(new KeyValue(k, null)));
        return res;
    }

    /**
     * Rehashing de la tabla hash. Se realiza cuando el factor de carga excede el máximo permitido.
     * Crea una nueva tabla con el doble de capacidad y reubica los elementos.
     */
    private void reHash(float factor) {
        int newCapacity = getNextPrime((int) (capacity * factor));
        HashTable hsTemp = new HashTable(newCapacity, loadFactor);
        for (int i = 0; i < capacity; i++) {
            ListaDinamica lista = table[i];
            int size = lista.getSize();
            for (int j = 0; j < size; j++) {
                hsTemp.put((KeyValue) lista.removeFirst());
            }
        }
        this.table = hsTemp.table;
        this.capacity = newCapacity;
    }

    /**
     * Serializa los elementos de la tabla hash y los almacena en archivos binarios.
     */
    public void serializarFichero() {
        for (int i = 0; i < capacity; i++) {
            this.table[i].serializarFichero(RUTA_BINARY + i + ".data");
        }
    }

    /**
     * Deserializa los elementos de la tabla hash desde archivos binarios y los carga en la tabla.
     */
    public void deserializarFichero() {
        for (int i = 0; i < capacity; i++) {
            this.table[i].deserializarFichero(RUTA_BINARY + i + ".data");
            this.population += this.table[i].getSize();
        }
    }

    /**
     * Escribe los elementos de la tabla hash en archivos de texto.
     */
    public void escribirFicheroTexto() {
        for (int i = 0; i < capacity; i++) {
            this.table[i].escribirFicheroTexto(RUTA_TXT + i + ".data");
        }
    }

    /**
     * Lee los elementos de la tabla hash desde archivos de texto y los carga en la tabla.
     */
    public void leerFicheroTexto() {
        for (int i = 0; i < capacity; i++) {
            String texto = this.table[i].leerFicheroTexto(RUTA_TXT + i + ".data");
            String[] pares = texto.split("\n");
            for (int j = 0; j < pares.length - 1; j++) {
                String[] par = pares[i].split("#");
                String[] atributos = par[1].split("@");
                //TODO ESTO NO SE CAMBIA NO METER COCHE
                KeyValue kv = new KeyValue(par[0], new Moto(atributos[0], atributos[1]));
                this.put(kv);
            }

        }
    }
    /**
     * Imprime todos los valores almacenados en la tabla hash.
     */
    public void printAll() {
        for (int i = 0; i < capacity; i++) {
            ListaDinamica lista = table[i];
            for (int j = 0; j < lista.getSize(); j++) {
                KeyValue kv = (KeyValue) lista.get(j);
                System.out.println("Clave: " + kv.getKey() + ", Valor: " + kv.getValue());
            }
        }
    }

}
