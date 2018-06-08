package tomlg.doormax.oomdpformalism.domains;

import java.util.HashSet;
import java.util.Set;

import tomlg.doormax.oomdpformalism.AttributeValue;

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
	public AttributeValue generateDomainValueInstanciation() {
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
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
		SetDomain other = (SetDomain) obj;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		return true;
	}
}
