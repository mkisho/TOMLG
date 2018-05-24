package tomlg.doormax.oomdpformalism;

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

	public final Map<ObjectAttribute, Object> attributesVal;
	public ObjectClass getObjectClass() {
		return objectClass;
	}

	public Map<ObjectAttribute, Object> getAttributesVal() {
		return attributesVal;
	}
	
	public Object getAttributeVal(String name) {
		for (ObjectAttribute att: attributesVal.keySet()) {
			if(att.name.equals(name)) {
				return this.attributesVal.get(att);
			}
		}
		return null;
	}
	
	public ObjectInstance(ObjectClass objectClass) {
		super();
		this.objectClass = objectClass;
		this.attributesVal = new HashMap<ObjectAttribute, Object>(this.objectClass.attributes.size());
		for (ObjectAttribute attribute : this.objectClass.attributes) {
			this.attributesVal.put(attribute, attribute.domain.generateDomainValueInstanciation());
		}
		
		this.id = this.objectClass.name +" - " + UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ObjectInstance makeCopy() {
		ObjectInstance copy = new ObjectInstance(this.objectClass);

		for (Map.Entry<ObjectAttribute, Object> entry : this.attributesVal.entrySet()) {
			ObjectAttribute key = entry.getKey();
			Object value = entry.getValue();

			copy.attributesVal.put(key, value);
		}
		return copy;
	}

	public int compareTo(ObjectInstance o2) {
		return this.id.compareTo(o2.id);
	}
	
	@Override
	public String toString() {
		return "ObjectInstance [Name = " + objectClass.name + "] [attributesVal=" + attributesVal + "]";
	}


}
