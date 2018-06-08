package tomlg.doormax.oomdpformalism;

import java.util.List;

/**
 * OOMDP - Defines a class of objects as in the OO-MDP formalism
 * 
 * @author chronius
 *
 */
public class ObjectClass {

	public final List<ObjectAttribute> attributes;
	public final String name;
   
	public List<ObjectAttribute> getAttributes() {
		return attributes;
	}

	public ObjectClass(List<ObjectAttribute> attributes, String name) {
		super();
		this.attributes = attributes;
		this.name = name;
	}

	@Override
	public String toString() {
		final int maxLen = 14;
		return "ObjectClass [name=" + name + ", attributes="
				+ (attributes != null ? attributes.subList(0, Math.min(attributes.size(), maxLen)) : null) + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		ObjectClass other = (ObjectClass) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
