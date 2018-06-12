package tomlg.doormax.oomdpformalism.domains;

import tomlg.doormax.oomdpformalism.AttributeValue;

public class AttributeValueBoolean extends AttributeValue {
	public final boolean value;

	public AttributeValueBoolean(boolean value) {
		super();
		this.value = value;
	}

	public AttributeValueBoolean(AttributeValueBoolean o) {
		this.value = o.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "B[value=" + value + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (value ? 1231 : 1237);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		AttributeValueBoolean other = (AttributeValueBoolean) obj;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public Double getNumericValForAttribute() {
		return (double)(this.value == false ? 0 : 1);
	}

	@Override
	public String toStringVal() {
		return this.value+"s";
	}

}
