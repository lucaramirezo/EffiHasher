import java.io.Serializable;

public class Moto implements Serializable{
	private String color;
	private String matricula;
	
	public Moto ( String matricula, String color) {
		this.matricula = matricula;
		this.color = color;
		
	}

	@Override
	public String toString() {
		return "Moto [color=" + color + ", matricula=" + matricula + "]";
	}

}
