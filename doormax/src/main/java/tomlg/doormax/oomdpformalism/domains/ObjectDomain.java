package tomlg.doormax.oomdpformalism.domains;


/**
 * Defines an attribute domain as in the OO-MDP formalism
 * @author chronius
 *
 */
public abstract class ObjectDomain {
	
	public abstract boolean valueInDomain(Object val);
	
	public abstract Object generateDomainValueInstanciation();
	
	
}
