package tomlg.doormax.oomdpformalism;

import tomlg.doormax.oomdpformalism.domains.ObjectDomain;

/**
 * Defines an attribute as in the OO-MDP formalism
 * 
 * @author chronius
 *
 */
public class ObjectAttribute {
	public final ObjectDomain domain;
	public final String name;

	public ObjectAttribute(String name, ObjectDomain domain) {
		super();  
		this.name = name;
		this.domain = domain;
	}

	public ObjectDomain getDomain() {
		return domain;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "ObjectAttribute [name=" + name + ", domain=" + domain + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ObjectAttribute other = (ObjectAttribute) obj;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
