package tomlg.doormax.oomdpformalism;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines a object instanciation as in the OO-MDP formalism
 * @author chronius
 *
 */
public class ObjectInstance {
	public final ObjectClass objectClass;
	
	public final Map<ObjectAttribute, Object> attributesVal;

	public ObjectClass getObjectClass() {
		return objectClass;
	}

	public Map<ObjectAttribute, Object> getAttributesVal() {
		return attributesVal;
	}

	public ObjectInstance(ObjectClass objectClass) {
		super();
		this.objectClass = objectClass;
		this.attributesVal = new HashMap<ObjectAttribute, Object>(this.objectClass.attributes.size());
		
		for(ObjectAttribute attribute: this.objectClass.attributes) {
			this.attributesVal.put(attribute, attribute.domain.generateDomainValueInstanciation());
		}
	}
	

}
