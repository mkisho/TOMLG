package doormax;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OOMDPState implements Serializable {
	private static final long serialVersionUID = 5220435988536652242L;
	
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
		for (ObjectInstance instance : this.objects) {
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

	public OOMDP getOomdp() {
		return oomdp;
	}

	public String toString() {
		String str = "State [\n" + this.getObjectsOfClass("taxi").toString();
		str += "\n" + this.getObjectsOfClass("passenger").toString();
		str += "\n" + this.getObjectsOfClass("goalLocation").toString();
		str += "]\n";
		return str;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((objects == null) ? 0 : objects.hashCode());

		for (ObjectInstance instance : this.objects) {
			result += prime * instance.hashAllCode();
		}

		result = prime * result + ((oomdp == null) ? 0 : oomdp.hashCode());
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
		OOMDPState other = (OOMDPState) obj;
		if (objects == null) {
			if (other.objects != null)
				return false;
		} // else if (!objects.equals(other.objects))
			// return false;
		if (oomdp == null) {
			if (other.oomdp != null)
				return false;
		} else if (!oomdp.equals(other.oomdp))
			return false;
		List<ObjectInstance> myList = this.getObjects();
		List<ObjectInstance> otherList = other.getObjects();
		for (int i = 0; i < myList.size(); i++) {
			assert (myList.get(i).getId().equals(otherList.get(i).getId()));
			if (!myList.get(i).equalsPlus(otherList.get(i)))
				return false;
		}
		return true;
	}

}