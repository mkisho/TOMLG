package tomlg.doormax;

import java.util.ArrayList;
import java.util.List;

import tomlg.doormax.effects.ArithmeticEffect;
import tomlg.doormax.effects.AssignmentEffect;
import tomlg.doormax.effects.EffectType;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectClass;
import tomlg.doormax.oomdpformalism.ObjectInstance;

/**
 * Defines an Effect as in the OO-MDP formalism
 */
public class Effect {
	// TODO Modificar para usar reflexão
	public static final EffectType[] γ = { new ArithmeticEffect(), new AssignmentEffect() };

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

	/**
	 * Return all effects that capture how oClass’s att changes from s to s 0 for
	 * all effect types in Y. MUST BE UPDATED FOR EVERY NEW EFFECT_TYPE IMPLEMENTED
	 */
	public static List<Effect> possibleEffectsExplanation(ObjectInstance o1, ObjectInstance o2, ObjectAttribute att) {
		List<Effect> hypothesisEffects = new ArrayList<Effect>();

		for (EffectType e : γ) {
			Effect hyptothesis = e.possibleEffectsExplanation(o1, o2, att);
			if (hyptothesis != null)
				hypothesisEffects.add(hyptothesis);
		}
		return hypothesisEffects;
	}
}
