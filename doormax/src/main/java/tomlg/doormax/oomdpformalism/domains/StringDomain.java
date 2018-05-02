package tomlg.doormax.oomdpformalism.domains;

public class StringDomain extends ObjectDomain{

	@Override
	public boolean valueInDomain(Object val) {
		return (val instanceof String);
	}

	@Override
	public Object generateDomainValueInstanciation() {
		return new String("");
	}

}
