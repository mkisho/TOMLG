package tomlg.doormax.oomdpformalism.domains;

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
		this.intervalEnd   = Integer.MAX_VALUE;
	}
	@Override
	public boolean valueInDomain(Object val) {
		if (val instanceof Integer) {
			if (((Integer) val) >= intervalStart && ((Integer) val <= intervalEnd)) {
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
	public Object generateDomainValueInstanciation() {
		return new Integer(0);
	}
}
