package tomlg.doormax.oomdpformalism.domains;

import tomlg.doormax.oomdpformalism.AttributeValue;

public class BooleanDomain extends ObjectDomain{

	@Override
	public boolean valueInDomain(Object val) {
		return (val instanceof AttributeValueBoolean);
	}

	@Override
	public AttributeValue  generateDomainValueInstanciation() {
		return new AttributeValueBoolean(false);
	}

	public boolean equals(Object o) {
		if (o instanceof BooleanDomain) {
			return true;
		}else return false;
	}
	
}
