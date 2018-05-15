package tomlg.doormax;

import java.util.HashMap;
import java.util.Map;

import tomlg.doormax.effects.EffectType;
import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectClass;
import tomlg.doormax.utils.Quadruple;

/**
 * 	for the contradictory (effect types, object class, attribute, action) tuples
 *
 */
public class ContradictoryWSet {
	private Map<Quadruple<Action, EffectType, ObjectAttribute, ObjectClass>, Map<Effect, Prediction>> ω;
	
	public ContradictoryWSet() {
		this.ω = new HashMap<Quadruple<Action, EffectType, ObjectAttribute, ObjectClass>, Map<Effect, Prediction>>();
	}

	public void add(Action action, Effect effect, Prediction pred) {
		Map<Effect, Prediction> temp = new HashMap<>();
		temp.put(effect, pred);
		this.ω.put(new Quadruple<Action, EffectType, ObjectAttribute, ObjectClass>(action, effect.type, effect.attribute, 
				effect.objectClass), temp);
	}
	
	public Map<Effect, Prediction> search(Action action, EffectType effectType, ObjectAttribute att,
			ObjectClass objClass) {
		
		return this.ω.get(
				new Quadruple<Action, EffectType, ObjectAttribute, ObjectClass>(action, effectType, att, objClass));
	}

}
