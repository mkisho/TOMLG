package doormax;
import java.util.List;

import doormax.structures.Action;

public class OOMDP {
	public final List<Action> actions;
	public final List<ObjectClass> objectClasses;
	public final PropositionalFunction[] pfIndex;
	
	public OOMDP(List<Action> actions, List<ObjectClass> objectClasses, PropositionalFunction[] pfIndex) {
		super();
		this.actions = actions;
		this.objectClasses = objectClasses;
		this.pfIndex = pfIndex;
	}

	@Override
	public String toString() {
		return "OOMDP [actions=" + actions + ", objectClasses=" + objectClasses + "]";
	}
	
	public List<ObjectClass> getObjectClasses() {
		return objectClasses;
	}

	public PropositionalFunction [] propositionsIndex() {
		return this.pfIndex;
	}
}
