package tomlg.doormax.oomdpformalism.domains;

public class BooleanDomain extends ObjectDomain{

	@Override
	public boolean valueInDomain(Object val) {
		return (val instanceof Boolean);
	}

	@Override
	public Object generateDomainValueInstanciation() {
		return new Boolean(false);
	}

}
