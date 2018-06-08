package tomlg.doormax.oomdpformalism.domains;

import tomlg.doormax.oomdpformalism.AttributeValue;

/**
 * Defines an attribute domain as in the OO-MDP formalism
 * @author chronius
 *
 */
public abstract class ObjectDomain {
	
	public abstract boolean valueInDomain(Object val);
	
	public abstract AttributeValue generateDomainValueInstanciation();
	
	
}
