import java.io.Serializable;

/**
 * Clase que representa un par clave-valor. Cada objeto de esta clase almacena
 * una clave única y un valor asociado a ella. Implementa Serializable para permitir
 * la serialización y deserialización de sus instancias.
 */
public class KeyValue implements Serializable {
	private String key; // Clave única para el par clave-valor
	private Object value; // Valor asociado a la clave

	/**
	 * Constructor para crear una nueva instancia de KeyValue.
	 *
	 * @param key La clave para el par clave-valor.
	 * @param value El valor asociado a la clave.
	 */
	public KeyValue(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Obtiene la clave del par clave-valor.
	 *
	 * @return La clave del par.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Establece o actualiza la clave del par clave-valor.
	 *
	 * @param key La nueva clave.
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Obtiene el valor asociado a la clave del par clave-valor.
	 *
	 * @return El valor asociado a la clave.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Establece o actualiza el valor asociado a la clave del par clave-valor.
	 *
	 * @param value El nuevo valor.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Compara esta instancia de KeyValue con otro objeto para determinar si son iguales.
	 * Dos instancias de KeyValue se consideran iguales si sus claves son iguales.
	 *
	 * @param obj El objeto con el que comparar.
	 * @return true si las claves son iguales, false en caso contrario.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof KeyValue) {
			KeyValue other = (KeyValue) obj;
			return this.key.equals(other.getKey());
		}
		return false;
	}

	/**
	 * Proporciona una representación en forma de cadena del par clave-valor.
	 *
	 * @return Una cadena que representa el par clave-valor, en el formato "clave#valor".
	 */
	@Override
	public String toString() {
		return key + "#" + value;
	}
}
