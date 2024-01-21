import java.io.Serializable;

/**
 * Clase que representa una moto. Cada moto tiene un color y una matrícula.
 * La clase implementa Serializable, lo que permite que sus instancias puedan
 * ser serializadas y deserializadas para su almacenamiento o transmisión.
 */
public class Moto implements Serializable {
	private String color; // Color de la moto
	private String matricula; // Matrícula de la moto

	/**
	 * Constructor para crear una nueva instancia de Moto.
	 *
	 * @param matricula La matrícula de la moto.
	 * @param color El color de la moto.
	 */
	public Moto(String matricula, String color) {
		this.matricula = matricula;
		this.color = color;
	}

	/**
	 * Proporciona una representación en forma de cadena de la moto,
	 * incluyendo su color y matrícula.
	 *
	 * @return Una cadena que representa la moto.
	 */
	@Override
	public String toString() {
		return "Moto [color=" + color + ", matricula=" + matricula + "]";
	}
}

