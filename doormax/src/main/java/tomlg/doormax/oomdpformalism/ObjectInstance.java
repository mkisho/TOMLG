package tomlg.doormax.oomdpformalism;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import tomlg.doormax.oomdpformalism.ObjectClass;
/**
 * Defines a object instantiation as in the OO-MDP formalism
 * 
 * @author chronius
 *
 */
public class ObjectInstance {
	private String id;
	
	public final ObjectClass objectClass;

	public final Map<ObjectAttribute, AttributeValue> attributesVal;
	public final Map<String, ObjectAttribute> attributeByName;
	
	public ObjectClass getObjectClass() {
		return objectClass;
	}

	public Map<ObjectAttribute, AttributeValue> getAttributesVal() {
		return attributesVal;
	}
	
	public ObjectInstance(ObjectClass objectClass) {
		super();
		this.objectClass = objectClass;
		this.attributesVal = new HashMap<ObjectAttribute, AttributeValue>(this.objectClass.attributes.size());
		this.attributeByName = new HashMap<String, ObjectAttribute>(this.objectClass.attributes.size());
		for (ObjectAttribute attribute : this.objectClass.attributes) {
			this.attributesVal.put(attribute, attribute.domain.generateDomainValueInstanciation());
			this.attributeByName.put(attribute.name, attribute);
		}
		
		this.id = this.objectClass.name +" - " + UUID.randomUUID().toString();
	}
	
	public ObjectInstance copy() {
		return new ObjectInstance(this);
	}
	
	
	public ObjectInstance(ObjectInstance copy) {
		super();
		this.objectClass = copy.objectClass;
		this.attributeByName = copy.attributeByName;
	
		this.attributesVal = new HashMap<ObjectAttribute, AttributeValue>(this.objectClass.attributes.size());
		for (ObjectAttribute attribute : this.objectClass.attributes) {
			this.attributesVal.put(attribute, 
					copy.getAttributeVal(attribute));
		}
		
		this.id = copy.id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int compareTo(ObjectInstance o2) {
		return this.id.compareTo(o2.id);
	}
	
	@Override
	public String toString() {
		String txt = "[name= "+ this.objectClass.name + " id=" + id +
				"class="+this.objectClass.name + " Att[ ";
		
		for(ObjectAttribute att: this.attributesVal.keySet()) {
			txt += att.name+"= " + this.attributesVal.get(att).toStringVal()+", ";
		}
		
		return txt + " ]";
	}

	public AttributeValue getAttributeValByName(String name) {
		ObjectAttribute a = getAttributeByName(name);
		return this.attributesVal.get(a);
	}
	
	public ObjectAttribute getAttributeByName(String name) {
		return this.attributeByName.get(name);
	}
	
	public AttributeValue getAttributeVal(ObjectAttribute att) {
		return this.attributesVal.get(att);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributeByName == null) ? 0 : attributeByName.hashCode());
		result = prime * result + ((attributesVal == null) ? 0 : attributesVal.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((objectClass == null) ? 0 : objectClass.hashCode());
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
		ObjectInstance other = (ObjectInstance) obj;
		if (attributeByName == null) {
			if (other.attributeByName != null)
				return false;
		} else if (!attributeByName.equals(other.attributeByName))
			return false;
		if (attributesVal == null) {
			if (other.attributesVal != null)
				return false;
		} else if (!attributesVal.equals(other.attributesVal))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (objectClass == null) {
			if (other.objectClass != null)
				return false;
		} else if (!objectClass.equals(other.objectClass))
			return false;
		return true;
	}
	
	
}
