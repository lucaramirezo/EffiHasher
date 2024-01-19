import java.io.Serializable;

public class KeyValue implements Serializable {
	String key;
	Object Value;
	
	public KeyValue(String key, Object value) {
		super();
		this.key = key;
		Value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return Value;
	}
	public void setValue(Object value) {
		Value = value;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj!=null) {
			return (this.key == ((KeyValue)obj).getKey());
		}
		return false;
	}
	@Override
	public String toString() {
		return key +"#"+Value;
	}
}
