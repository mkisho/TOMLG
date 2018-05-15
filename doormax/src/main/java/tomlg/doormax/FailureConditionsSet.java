package tomlg.doormax;

import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.OOMDP;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectClass;
import tomlg.doormax.utils.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * conditions under which that action has been observed to not effect that
 * attribute of that object instance. Each of these sets is essentially a
 * tabular set of failure conditions for when a does not change att of any
 * instances of oClass.
 */
public class FailureConditionsSet {
	private Map<FailureCondition, Set<Condition>> f;

	public FailureConditionsSet(OOMDP oomdp) {
		Log.info("Creating F set");
		this.f = new HashMap<>();
		initialize(oomdp);
		Log.info("F set created");
	}

	private void initialize(OOMDP oomdp) {
		Log.info("Initializing F set");
		for (ObjectClass objClass : oomdp.objectClasses)
			for (ObjectAttribute attribute : objClass.attributes)
				for (Action action : oomdp.actions)
					this.f.put(new FailureCondition(action, attribute, objClass), new HashSet<Condition>());
		Log.info("F set initialized");
	}

	public void add(Action a, ObjectAttribute att, ObjectClass oClass, Condition cond) {
		Log.info("Cond: " + cond + " addet to F set with Failure with (" + a + ", " + att + ", " + oClass + ")");

		this.f.get(new FailureCondition(a, att, oClass)).add(cond);
	}
}

class FailureCondition {
	public final Action action;
	public final ObjectAttribute attribute;
	public final ObjectClass objClass;

	public FailureCondition(Action action, ObjectAttribute attribute, ObjectClass objClass) {
		this.action = action;
		this.attribute = attribute;
		this.objClass = objClass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + ((objClass == null) ? 0 : objClass.hashCode());
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
		FailureCondition other = (FailureCondition) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		if (objClass == null) {
			if (other.objClass != null)
				return false;
		} else if (!objClass.equals(other.objClass))
			return false;
		return true;
	}
}