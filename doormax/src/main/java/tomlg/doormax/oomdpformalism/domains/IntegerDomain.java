package tomlg.doormax.oomdpformalism.domains;

import tomlg.doormax.oomdpformalism.AttributeValue;

public class IntegerDomain extends ObjectDomain {
	public final int intervalStart;
	public final int intervalEnd;

	public IntegerDomain(int intervalStart, int intervalEnd) {
		super();
		this.intervalStart = intervalStart;
		this.intervalEnd = intervalEnd;
	}

	public IntegerDomain() {
		this.intervalStart = Integer.MIN_VALUE;
		this.intervalEnd = Integer.MAX_VALUE;
	}

	@Override

	public boolean valueInDomain(Object val) {
		if (val instanceof AttributeValueInteger) {
			int value = ((AttributeValueInteger)val).value; 
			if (value >= intervalStart && (value   <= intervalEnd)) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	@Override
	public String toString() {
		return "IntegerDomain [intervalStart=" + intervalStart + ", intervalEnd=" + intervalEnd + "]";
	}

	@Override
	public AttributeValue generateDomainValueInstanciation() {
		return new AttributeValueInteger(this.intervalStart);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + intervalEnd;
		result = prime * result + intervalStart;
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
		IntegerDomain other = (IntegerDomain) obj;
		if (intervalEnd != other.intervalEnd)
			return false;
		if (intervalStart != other.intervalStart)
			return false;
		return true;
	}
}
