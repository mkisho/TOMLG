package tomlg.doormax.oomdpformalism.domains;

import tomlg.doormax.oomdpformalism.AttributeValue;

public class AttributeValueInteger extends AttributeValue{
	public final int value;
	
	public AttributeValueInteger(int value) {
		this.value = value;
	}
	
	public AttributeValueInteger(AttributeValueInteger o) {
		this.value = o.value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeValueInteger other = (AttributeValueInteger) obj;
		if (value != other.value)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AttributeValueInteger [value=" + value + "]";
	}

	@Override
	public Double getNumericValForAttribute() {
		return (double)this.value;
	}
}
