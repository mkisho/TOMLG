package tomlg.doormax.oomdpformalism.domains;

import tomlg.doormax.oomdpformalism.AttributeValue;

public class AttributeValueString extends AttributeValue{
	public final String value;

	public AttributeValueString(String value) {
		super();
		this.value = value;
	}
	
	public AttributeValueString(AttributeValueString o) {
		super();
		this.value = o.value;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeValueString other = (AttributeValueString) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "AttributeValueString [value=" + value + "]";
	}
}
