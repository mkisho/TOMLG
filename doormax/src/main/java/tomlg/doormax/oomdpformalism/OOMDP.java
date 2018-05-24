package tomlg.doormax.oomdpformalism;

import java.util.List;

import tomlg.doormax.PropositionalFunction;

public class OOMDP {
	public final List<Action> actions;
	public final List<ObjectClass> objectClasses;
	public final PropositionalFunction[] propositionsList;
	
	public OOMDP(List<Action> actions, List<ObjectClass> objectClasses, PropositionalFunction[] propositionsList) {
		super();
		this.actions = actions;
		this.objectClasses = objectClasses;
		this.propositionsList = propositionsList;
	}

	@Override
	public String toString() {
		return "OOMDP [actions=" + actions + ", objectClasses=" + objectClasses + "]";
	}
	
	public List<ObjectClass> getObjectClasses() {
		return objectClasses;
	}

	public PropositionalFunction [] propositionsList() {
		return this.propositionsList;
	}
}
