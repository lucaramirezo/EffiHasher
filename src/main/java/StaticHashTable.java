import java.io.Serializable;

/**
 * Esta clase representa una tabla hash estática que almacena pares clave-valor.
 */
public class StaticHashTable implements Serializable {
    private final int TABLE_SIZE; // Definir un tamaño fijo para la tabla hash
    private KeyValue[] table; // Array para almacenar los pares clave-valor

    /**
     * Constructor de la clase StaticHashTable.
     *
     * @param size El tamaño deseado para la tabla hash.
     */
    public StaticHashTable(int size) {
        this.TABLE_SIZE = size;
        table = new KeyValue[TABLE_SIZE];
    }

    /**
     * Calcula el primer valor hash de una clave utilizando la función hash1.
     *
     * @param key La clave para la cual se calculará el valor hash.
     * @return El valor hash calculado.
     */
    private int hash1(String key) {
        return key.hashCode() % TABLE_SIZE;
    }

    /**
     * Calcula el segundo valor hash de una clave utilizando la función hash2.
     *
     * @param key La clave para la cual se calculará el valor hash.
     * @return El valor hash calculado.
     */
    private int hash2(String key) {
        // Usar un número primo menor que el tamaño de la tabla para el doble hash
        int hash2 = key.hashCode() % (TABLE_SIZE - 1) + 1;
        return hash2;
    }

    /**
     * Inserta un par clave-valor en la tabla hash.
     *
     * @param key   La clave a insertar.
     * @param value El valor asociado a la clave.
     * @return true si la inserción fue exitosa, false si la tabla está llena.
     */
    public boolean put(String key, Object value) {
        int hashedKey = hash1(key);
        int stepSize = hash2(key);
        int probingIndex = hashedKey;

        for (int i = 0; i < TABLE_SIZE; i++) {
            if (table[probingIndex] == null) {
                table[probingIndex] = new KeyValue(key, value);
                return true;
            }

            if (table[probingIndex].getKey().equals(key)) {
                table[probingIndex].setValue(value);
                return true;
            }

            // Calcular la siguiente ranura usando el doble hash
            probingIndex = (probingIndex + stepSize) % TABLE_SIZE;
        }

        // Si se recorrió toda la tabla y no se encontró una ranura vacía, la tabla está llena
        return false;
    }

    /**
     * Reemplaza un par clave-valor existente en la tabla hash con otro valor dado.
     *
     * @param kv par clave valor
     * @return true si el reemplazo fue exitoso, false si la clave no se encuentra en la tabla.
     */
    public boolean replace(KeyValue kv) {
        int hashedKey = hash1(kv.getKey());
        int stepSize = hash2(kv.getKey());
        int probingIndex = hashedKey;

        for (int i = 0; i < TABLE_SIZE; i++) {
            if (table[probingIndex] == null) {
                return false; // La clave no está en la tabla
            }

            if (table[probingIndex].getKey().equals(kv.getKey())) {
                table[probingIndex].setValue(kv.getValue());
                return true;
            }

            // Calcular la siguiente ranura usando el doble hash
            probingIndex = (probingIndex + stepSize) % TABLE_SIZE;
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
        int hashedKey = hash1(key);
        int stepSize = hash2(key);
        int probingIndex = hashedKey;

        for (int i = 0; i < TABLE_SIZE; i++) {
            KeyValue entry = table[probingIndex];

            if (entry == null) {
                return false; // La clave no está en la tabla
            }

            if (entry.isActive() && entry.getKey().equals(key)) {
                entry.delete();
                return true;
            }

            // Calcular la siguiente ranura usando el doble hash
            probingIndex = (probingIndex + stepSize) % TABLE_SIZE;
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
        int hashedKey = hash1(key);
        int stepSize = hash2(key);
        int probingIndex = hashedKey;

        for (int i = 0; i < TABLE_SIZE; i++) {
            if (table[probingIndex] == null) {
                return null; // La clave no está en la tabla
            }

            if (table[probingIndex].getKey().equals(key)) {
                return table[probingIndex].getValue();
            }

            // Calcular la siguiente ranura usando el doble hash
            probingIndex = (probingIndex + stepSize) % TABLE_SIZE;
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
