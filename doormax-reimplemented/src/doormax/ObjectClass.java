package doormax;
import java.util.ArrayList;
import java.util.List;

import doormax.structures.Attribute;

public class ObjectClass {
	private String name;
	
	private List<Attribute> attributes;

	
	public ObjectClass(String name) {
		super();
		this.name = name;
		this.attributes = new ArrayList<Attribute>();
	}

	public ObjectClass(String name, List<Attribute> attributes) {
		super();
		this.name = name;
		this.attributes = attributes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjectClass other = (ObjectClass) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	public List<Attribute> getAttributesCopy(){
		List<Attribute> copy = new ArrayList<Attribute>(this.attributes.size());
		for(Attribute att: this.attributes) {
			copy.add(att.copy());
		}
		return copy;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	public void addAttribute(Attribute attribute) {
		this.attributes.add(attribute);
	}
	
}
