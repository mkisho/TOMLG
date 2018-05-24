package tomlg.doormax.effects;

import tomlg.doormax.Effect;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectInstance;

public abstract class EffectType {
	
	public abstract ObjectInstance doEffect(ObjectInstance object, ObjectAttribute attribute, Double number);	

	public abstract Effect possibleEffectsExplanation(ObjectInstance o1, ObjectInstance o2, ObjectAttribute att);
}
