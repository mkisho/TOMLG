package tomlg.doormax.oomdpformalism.domains;

import tomlg.doormax.oomdpformalism.AttributeValue;

public class StringDomain extends ObjectDomain{

	@Override
	public boolean valueInDomain(Object val) {
		return (val instanceof String);
	}

	@Override
	public AttributeValue generateDomainValueInstanciation() {
		return new AttributeValueString("");
	}
	
	public boolean equals(Object o) {
		if (o instanceof StringDomain) {
			return true;
		}else return false;
	}
}
