package doormax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import doormax.structures.attribute.Attribute;
import doormax.structures.attribute.AttributeBoolean;

public class ObjectInstance implements Comparable<ObjectInstance> {
	private static int UNIVERSAL_ID;
	private String id;

	private ObjectClass oclass;
	private List<Attribute> attributes;

	public ObjectClass getOclass() {
		return oclass;
	}

	public void setOclass(ObjectClass oclass) {
		this.oclass = oclass;
	}

	public List<Attribute> getAttributes() {
		Collections.sort(this.attributes);

		return this.attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public ObjectInstance(ObjectClass oclass, List<Attribute> attributes) {
		super();
		this.oclass = oclass;
		this.attributes = attributes;
		this.id = this.oclass.getName() + "-" + UNIVERSAL_ID++;
	}

	public ObjectInstance copy() {
		return new ObjectInstance(this);
	}

	public ObjectInstance(ObjectInstance copy) {
		super();
		this.oclass = copy.oclass;
		this.attributes = new ArrayList<Attribute>();

		for (Attribute attribute : copy.attributes) {
			this.attributes.add(attribute.copy());
		}

		this.id = copy.id;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "[" + id + " " + oclass.getName() + " attributes={" + attributes + "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((id == null) ? 0 : attributes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjectInstance other = (ObjectInstance) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(ObjectInstance o) {
		return this.id.compareTo(o.id);
	}

	public Attribute getAttributeValByName(String string) {
		for (Attribute attribute : this.attributes) {
			if (attribute.getName().equals(string))
				return attribute;
		}
		return null;
	}

	public int hashAllCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((id == null) ? 0 : attributes.hashCode());
		for (Attribute att : attributes) {
			result += att.getDoubleValue().intValue() ^ 2;
		}
		return result;
	}

	public boolean equalsPlus(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjectInstance other = (ObjectInstance) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		for (int index = 0; index < attributes.size(); index++) {
			if (this.attributes.get(index).getDoubleValue() != other.attributes.get(index).getDoubleValue()) {
				return false;
			}
		}

		return true;
	}

}
