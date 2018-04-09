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

}
