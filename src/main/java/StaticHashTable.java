import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Esta clase representa una tabla hash estática que almacena pares clave-valor.
 * Incluye contadores para pasos (time) y memoria (memory) para monitorear el rendimiento.
 */
public class StaticHashTable implements Serializable {
    private int size; // Número de elementos insertados en la tabla hash
    private KeyValue[] table; // Array para almacenar los pares clave-valor
    private double loadFactor; // Factor de carga máximo permitido antes de rehashing
    private int TABLE_SIZE; // Tamaño actual de la tabla hash
    private int time; // Contador de pasos
    private int memory; // Contador de memoria

    /**
     * Constructor para la tabla hash.
     *
     * @param size El tamaño inicial de la tabla hash.
     * @param loadFactor El factor de carga máximo permitido antes de realizar un rehashing.
     */
    public StaticHashTable(int size, double loadFactor) {
        this.TABLE_SIZE = size;
        this.loadFactor = loadFactor;
        table = new KeyValue[TABLE_SIZE];
        this.size = 0;
        this.time = 0;
        this.memory = 0;
    }

    /**
     * Calcula el índice de hash para una clave dada utilizando SHA-256.
     * Incrementa los contadores de tiempo y memoria para monitorear el rendimiento.
     *
     * @param key clave para la cual calcular el índice de hash.
     * @return índice de hash calculado.
     */
    private int hash(String key) {
        incrementTime(); // Contar como un paso para la operación de hashing
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            incrementMemory(); // Contar la obtención del MessageDigest como una operación de memoria
            byte[] hash = digest.digest(key.getBytes("UTF-8"));
            incrementMemory(); // Contar la conversión de la clave a bytes como una operación de memoria
            int hashInt = 0;

            // Convertir los primeros 4 bytes del hash a un entero
            for (int i = 0; i < 4; i++) {
                hashInt <<= 8; // Desplazar 8 bits a la izquierda
                hashInt |= (hash[i] & 0xFF); // Añadir el siguiente byte del hash
                incrementTime(); // Contar como un paso para cada operación en el bucle
            }

            incrementMemory(); // Contar el acceso al array hash como una operación de memoria
            return Math.abs(hashInt % TABLE_SIZE); // Usar el valor absoluto para asegurarnos de que el índice es positivo
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            // Manejar de alguna manera el error en caso de que el algoritmo SHA-256 no esté disponible
            throw new RuntimeException("SHA-256 no disponible", e);
        }
    }

    /**
     * Incrementa el contador de pasos (time).
     * Cada llamada a este método representa una operación realizada,
     * ayudando a medir la complejidad en términos de pasos de ejecución.
     */
        private void incrementTime() {
            this.time++;
        }
    /**
     * Incrementa el contador de memoria (memory).
     * Cada llamada a este método representa una operación de acceso a memoria,
     * ayudando a estimar el uso de memoria de la tabla hash.
     */
        private void incrementMemory() {
            this.memory++;
        }
    /**
     * Inserta un par clave-valor en la tabla hash. Si la clave ya existe, actualiza el valor.
     * Si se alcanza el factor de carga, realiza un rehashing antes de insertar.
     * Incrementa los contadores de tiempo y memoria para monitorear el rendimiento.
     *
     * @param key La clave a insertar.
     * @param value El valor asociado a la clave.
     * @return true si la inserción fue exitosa, false si la tabla está llena.
     */

    public boolean put(String key, Object value) {
            incrementTime(); // Contar como un paso
            int hashedKey = hash(key);
            int stepSize = hash(key);
            int probingIndex = hashedKey;

            if ((size + 1.0) / TABLE_SIZE > loadFactor) { // Si se alcanza el factor de carga, rehash
                rehash();
            }
            for (int i = 0; i < size; i++) {
                incrementMemory(); // Contar la operación de acceso a la memoria
                if (table[probingIndex] == null) {
                    table[probingIndex] = new KeyValue(key, value);
                    size++;
                    return true;
                }

            if (table[probingIndex].getKey().equals(key)) {
                table[probingIndex].setValue(value);
                return true;
            }

            // Calcular la siguiente ranura usando el doble hash
            probingIndex = (probingIndex + stepSize) % TABLE_SIZE;
        }

            return false;
        }
    /**
     * Realiza un rehashing de la tabla hash cuando el factor de carga excede el máximo permitido.
     * Crea una nueva tabla con el doble de capacidad y reubica los elementos.
     * Incrementa los contadores de tiempo y memoria para monitorear el rendimiento.
     */
        private void rehash() {
            incrementTime(); // Contar como un paso
            KeyValue[] oldTable = table;
            TABLE_SIZE = getNextPrime(TABLE_SIZE * 2);
            table = new KeyValue[TABLE_SIZE];
            this.size = 0;

            for (KeyValue kv : oldTable) {
                if (kv != null && kv.isActive()) {
                    put(kv.getKey(), kv.getValue());
                    incrementMemory(); // Contar la operación de reubicación en memoria
                }
            }
        }
    /**
     * Obtiene el número total de pasos realizados en las operaciones de la tabla hash.
     *
     * @return El número total de pasos.
     */
        public int getTime() {
            return time;
        }
    /**
     * Obtiene el número total de operaciones de memoria realizadas en las operaciones de la tabla hash.
     *
     * @return El número total de operaciones de memoria.
     */
        public int getMemory() {
            return memory;
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
     * Reemplaza un par clave-valor existente en la tabla hash con otro valor dado.
     *
     * @param kv par clave valor
     * @return true si el reemplazo fue exitoso, false si la clave no se encuentra en la tabla.
     */
    public boolean replace(KeyValue kv) {
        int hashedKey = hash(kv.getKey());
        int stepSize = hash(kv.getKey());
        int probingIndex = hashedKey;

        for (int i = 0; i < size; i++) {
            if (table[probingIndex] == null) {
                return false; // La clave no está en la tabla
            }

            if (table[probingIndex].getKey().equals(kv.getKey())) {
                table[probingIndex].setValue(kv.getValue());
                return true;
            }

            // Calcular la siguiente ranura usando el doble hash
            probingIndex = (probingIndex + stepSize) % size;
        }

        return false; // La clave no está en la tabla
    }
    /**
     * Elimina un par clave-valor de la tabla hash.
     *
     * @param key La clave del par clave-valor a eliminar.
     * @return true si la eliminación fue exitosa, false si la clave no se encuentra en la tabla.
     */
    public boolean remove(String key) {
        int hashedKey = hash(key);
        int stepSize = hash(key);
        int probingIndex = hashedKey;

        for (int i = 0; i < size; i++) {
            KeyValue entry = table[probingIndex];

            if (entry == null) {
                return false; // La clave no está en la tabla
            }

            if (entry.isActive() && entry.getKey().equals(key)) {
                entry.delete();
                return true;
            }

            // Calcular la siguiente ranura usando el doble hash
            probingIndex = (probingIndex + stepSize) % size;
        }

        return false; // La clave no está en la tabla
    }

    /**
     * Obtiene el valor asociado a una clave en la tabla hash.
     *
     * @param key La clave para la cual se busca el valor.
     * @return El valor asociado a la clave o null si la clave no está en la tabla.
     */
    public Object get(String key) {
        int hashedKey = hash(key);
        int stepSize = hash(key);
        int probingIndex = hashedKey;

        for (int i = 0; i < size; i++) {
            if (table[probingIndex] == null) {
                return null; // La clave no está en la tabla
            }

            if (table[probingIndex].getKey().equals(key)) {
                return table[probingIndex].getValue();
            }

            // Calcular la siguiente ranura usando el doble hash
            probingIndex = (probingIndex + stepSize) % size;
        }

        return null; // La clave no está en la tabla
    }

    /**
     * Imprime todos los pares clave-valor almacenados en la tabla hash.
     */
    public void printAll() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (table[i] != null) {
                KeyValue kv = table[i];
                System.out.println("Clave: " + kv.getKey() + ", Valor: " + kv.getValue());
            }
        }
    }
    private enum EntryState {
        ACTIVE, DELETED, EMPTY // Estados de las entradas en la tabla
    }
    /**
     * Clase interna para almacenar pares clave-valor y su estado.
     */
    public static class KeyValue {
        private String key;
        private Object value;
        private EntryState state;

        public KeyValue(String key, Object value) {
            this.key = key;
            this.value = value;
            this.state = EntryState.ACTIVE;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public EntryState getState() {
            return state;
        }

        public void setState(EntryState state) {
            this.state = state;
        }

        public boolean isActive() {
            return state == EntryState.ACTIVE;
        }

        public void delete() {
            state = EntryState.DELETED;
        }
    }
}
