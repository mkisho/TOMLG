package tomlg.doormax;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tomlg.doormax.effects.ArithmeticEffect;
import tomlg.doormax.effects.AssignmentEffect;
import tomlg.doormax.effects.EffectType;
import tomlg.doormax.oomdpformalism.AttributeValue;
import tomlg.doormax.oomdpformalism.OOMDPState;
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
	public final AttributeValue value;

	public Effect(EffectType type, ObjectAttribute attribute, ObjectClass objectClass, AttributeValue value) {
		super();
		this.type = type;
		this.attribute = attribute;
		this.objectClass = objectClass;
		this.value = value;
	}

	/**
	 * Return all effects that capture how oClass’s att changes from s to s 0 for
	 * all effect types in Y. IT MUST BE UPDATED FOR EVERY NEW EFFECT_TYPE IMPLEMENTED
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
	
	public boolean sameClassAndAttribute(Effect e) {
		if(e.attribute.equals(e.attribute) && e.objectClass.equals(e.objectClass))
			return true;
		else return false;
	}
	
	/**
	 * Check if some effect in effects contradict this effect
	 * @param effects
	 * @return
	 */
	public boolean contradicts(Set<Effect> effects) {
		for (Effect e : effects) {
			if(this.contradicts(e))
				return true;
		}
		return false;
	}
	
	/**
	 * Check if some effect in effects contradict this effect
	 * @param effects
	 * @return
	 */
	public boolean contradicts(Effect effect) {
		if(this.objectClass != effect.objectClass || this.attribute != effect.attribute)
			return false;
		if(!this.value.equals(effect.value))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + ((objectClass == null) ? 0 : objectClass.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Effect other = (Effect) obj;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		if (objectClass == null) {
			if (other.objectClass != null)
				return false;
		} else if (!objectClass.equals(other.objectClass))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public static List<Effect> possibleEffectsExplanation(OOMDPState s, OOMDPState sPrime, ObjectClass oClass,
			ObjectAttribute att, List<EffectType> effectsToUse) throws EffectNotFoundException {

		List<Effect> toReturn = new ArrayList<Effect>();

		for (ObjectInstance o: s.getObjectsOfClass(oClass.name)) {
			String objectId = o.getId();

			//Find object of same id
			ObjectInstance oInSPrime = sPrime.objectsByName.get(objectId);

			//If object was deleted keep on truckin'
			if (oInSPrime == null) {
				continue;
			}

			AttributeValue attValBefore = o.getAttributeValByName(att.name);
			AttributeValue attValAfter = oInSPrime.getAttributeValByName(att.name);

			if (!attValAfter.equals(attValBefore)) {
				//Hypothesize effects
				for (EffectType effectType: effectsToUse) {
					Effect e = effectType.possibleEffectsExplanation(o, oInSPrime, att);
					if (e == null) throw new RuntimeException("Nooooooooooooooo Erroo");
					toReturn.add(e);
				}
			}
		}
		if(toReturn.size() == 0) throw new EffectNotFoundException(); 
		return toReturn;
	}
	
	public OOMDPState applyEffect(OOMDPState iState) {
		OOMDPState toReturn = new OOMDPState(iState);
		
		for (ObjectInstance o: toReturn.getObjectsOfClass(this.objectClass.name)) {
			toReturn.updateObjectInstance(o, this.type.applyEffect(o, this.attribute, this.value));
		}
		
		return toReturn;
	}

	
}
