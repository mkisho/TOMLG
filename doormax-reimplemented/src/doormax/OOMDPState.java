package doormax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OOMDPState {
	private OOMDP oomdp;
	private List<ObjectInstance> objects;

	public OOMDPState(OOMDP oomdp, List<ObjectInstance> objects) {
		this.oomdp = oomdp;
		this.objects = objects;
	}
	
	public OOMDPState(OOMDP oomdp) {
		this(oomdp, new ArrayList<ObjectInstance>());
	}

	public OOMDPState copy() {
		List<ObjectInstance> objsCopy = new ArrayList<ObjectInstance>();
		for(ObjectInstance instance: this.objects) {
			objsCopy.add(instance.copy());
		}
		return new OOMDPState(oomdp, objsCopy);
	}
	
	/**
	 * Returns sorted objects list by id
	 * 
	 * @return
	 */
	public List<ObjectInstance> getObjects() {
		Collections.sort(this.objects);
		return this.objects;
	}

	public void addObjectInstance(ObjectInstance newObjInst) {
		this.objects.add(newObjInst);
	}

	// TODO pode ser otimizado com uma tabela de lookup
	public ObjectInstance getObjectOfClass(String name) {
		for (ObjectInstance o : this.objects) {
			if (o.getOclass().getName().equals(name))
				return o;
		}
		return null;
	}

	// TODO pode ser otimizado com uma tabela de lookup
	public List<ObjectInstance> getObjectsOfClass(String name) {
		List<ObjectInstance> objs = new ArrayList<ObjectInstance>();
		for (ObjectInstance o : this.objects) {
			if (o.getOclass().getName().equals(name))
				objs.add(o);
		}
		return objs;
	} 

	public ObjectInstance getObjById(String id) {
		for (ObjectInstance o : this.objects) {
			if (o.getId().equals(id))
				return o;
		}
		return null;
	}

}