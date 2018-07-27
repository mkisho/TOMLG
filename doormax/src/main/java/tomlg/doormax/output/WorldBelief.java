package tomlg.doormax.output;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import tomlg.doormax.PropositionalFunction;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectInstance;

public class WorldBelief extends Belief{
	private String name;
	private String type;
	private Map<String, String> params;
	
	public WorldBelief(String name, String type, Map<String, String> params) {
		this.name = name;
		this.type = type;
		this.params = params;
	}
	
	public static WorldBelief fromObjInstance(ObjectInstance objInstance) {
		String type = "OBJECT";
		Map<String, String> params = new HashMap<String, String>();
		
		for (ObjectAttribute att : objInstance.attributesVal.keySet()) {
			params.put(att.name, objInstance.getAttributeVal(att).toStringVal());
		}
		params.put("id", objInstance.getId());
		
		
		return new WorldBelief(objInstance.getObjectClass().name, type, params);
	}
	
	public static WorldBelief fromPropositionalFunction(PropositionalFunction propositionalFunction, boolean c) {
		String type = "PF";
		Map<String, String> params = new HashMap<String, String>();
		params.put("value", (c ? "true" : "false"));
		
		return new WorldBelief(propositionalFunction.name, type, params);
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "WorldBelief [name=" + name + ", type=" + type + ", params="
				+ (params != null ? toString(params.entrySet(), maxLen) : null) + "]";
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public Map<String, String> getParams() {
		return params;
	}
}
