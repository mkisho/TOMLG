package doormax;
import java.util.Arrays;
import java.util.List;

import doormax.structures.Action;

public class OOMDP {
	private List<Action> actions;
	private Action[] actionsArray;
	private List<ObjectClass> objectClasses;
	private PropositionalFunction[] pfIndex;
	
	public OOMDP(List<Action> actions, List<ObjectClass> objectClasses, PropositionalFunction[] pfIndex) {
		super();
		this.actions = actions;
		this.objectClasses = objectClasses;
		this.pfIndex = pfIndex;
		
		this.actionsArray = new Action[this.actions.size()];
		for(int i=0;i<actionsArray.length; i++)
			this.actionsArray[i] = this.actions.get(i);
	}
	


	
	public Action[] getActionsArray() {
		return actionsArray;
	}




	public List<Action> getActions() {
		return actions;
	}




	public PropositionalFunction[] getPfIndex() {
		return pfIndex;
	}




	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OOMDP [actions={");
		builder.append(actions);
		builder.append("},\n    objectClasses={");
		builder.append(objectClasses);
		builder.append("},\n    pfIndex=");
		builder.append(Arrays.toString(pfIndex));
		builder.append("},\n]");
		return builder.toString();
	}



	public List<ObjectClass> getObjectClasses() {
		return objectClasses;
	}

	public PropositionalFunction [] propositionsIndex() {
		return this.pfIndex;
	}
}
