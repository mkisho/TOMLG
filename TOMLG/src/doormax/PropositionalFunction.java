package doormax;

import java.io.Serializable;

public abstract class PropositionalFunction implements Serializable{
	private static final long serialVersionUID = 1L;
	public final String name;

	public PropositionalFunction(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public abstract boolean evaluate(OOMDPState state);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropositionalFunction other = (PropositionalFunction) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
