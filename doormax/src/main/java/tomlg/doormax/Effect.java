package tomlg.doormax;

import tomlg.doormax.effects.EffectType;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectClass;

/**
 * Defines an Effect as in the OO-MDP formalism
 */
public class Effect {
	public final EffectType type;
	public final ObjectAttribute attribute;
	public final ObjectClass objectClass;
	public Double number;

	public Effect(EffectType type, ObjectAttribute attribute, ObjectClass objectClass, Double number) {
		super();
		this.type = type;
		this.attribute = attribute;
		this.objectClass = objectClass;
		this.number = number;
	}
}
