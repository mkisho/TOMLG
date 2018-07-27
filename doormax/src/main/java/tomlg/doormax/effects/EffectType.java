package tomlg.doormax.effects;

import tomlg.doormax.Effect;
import tomlg.doormax.oomdpformalism.AttributeValue;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectInstance;

public abstract class EffectType {
	

	public abstract ObjectInstance applyEffect(ObjectInstance object, ObjectAttribute attribute, AttributeValue number);	

	public abstract Effect possibleEffectsExplanation(ObjectInstance o1, ObjectInstance o2, ObjectAttribute att);
	
	public abstract String name();
	
	public boolean equals(Object o) {
		if(o == null) return false;
		if(this.getClass().equals(o.getClass())) return true;
		else return false; 
	}
}
