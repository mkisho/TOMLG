package tomlg.doormax.output;

import java.util.ArrayList;
import java.util.List;

import tomlg.doormax.PropositionalFunction;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectInstance;

public class WorldBelief extends Belief{
	private String name;
	private boolean evaluation;
	private String [] params;
	
	public WorldBelief(String name, String [] params, boolean evaluation) {
		this.name = name;
		this.params = params;
		this.evaluation = evaluation;
	}
	
	public static Belief fromObjInstance(ObjectInstance objInstance) {
		List<String> params = new ArrayList<String>();
		params.add(objInstance.getId());
		for (ObjectAttribute att : objInstance.attributesVal.keySet()) {
			params.add(att.getName() + "=" + objInstance.getAttributeVal(att));
		}
		
//		return new WorldBelief(objInstance.getObjectClass().name, (String [])params.toArray(), true);
		return null;
	}
	
	public String toString() {
		return (this.evaluation?"":"not ")+ this.name+"(" +params +")";
	}

	public static Belief fromPropositionalFunction(PropositionalFunction propositionalFunction, boolean c) {
		return new WorldBelief(propositionalFunction.name, null, c);
	}
	

}
