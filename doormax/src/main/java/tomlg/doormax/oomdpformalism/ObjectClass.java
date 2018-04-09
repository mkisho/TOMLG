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
}
