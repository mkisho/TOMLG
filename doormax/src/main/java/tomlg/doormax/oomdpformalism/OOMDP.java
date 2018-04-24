package tomlg.doormax.oomdpformalism;

import java.util.List;

public class OOMDP {
	public final List<Action> actions;
	public final List<ObjectClass> objectClasses;
	
	

	public OOMDP(List<Action> actions, List<ObjectClass> objectClasses) {
		super();
		this.actions = actions;
		this.objectClasses = objectClasses;
	}

	@Override
	public String toString() {
		return "OOMDP [actions=" + actions + ", objectClasses=" + objectClasses + "]";
	}
	
	public List<ObjectClass> getObjectClasses() {
		return objectClasses;
	}
	
	
}
