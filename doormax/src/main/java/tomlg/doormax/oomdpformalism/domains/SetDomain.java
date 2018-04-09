package tomlg.doormax.oomdpformalism.domains;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author chronius
 *
 */
public class SetDomain extends ObjectDomain {
	public final Set<Object> elements;

	public SetDomain(Set<Object> elements) {
		super();
		this.elements = elements;
	}

	@Override
	public boolean valueInDomain(Object val) {
		return elements.contains(val);
	}

	@Override
	public String toString() {
		return "SetDomain [elements=" + elements + "]";
	}

	@Override
	public Object generateDomainValueInstanciation() {
		return new HashSet<Object>();
	}
}
