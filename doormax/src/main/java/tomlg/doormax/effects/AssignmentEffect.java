package tomlg.doormax.effects;

import tomlg.doormax.Effect;
import tomlg.doormax.oomdpformalism.AttributeValue;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectInstance;

public class AssignmentEffect extends EffectType {
	@Override
	public ObjectInstance applyEffect(ObjectInstance object, ObjectAttribute attribute, AttributeValue value) {
		ObjectInstance objectWithEffect = new ObjectInstance(object);

		objectWithEffect.attributesVal.put(attribute, value);
		return objectWithEffect;
	}

	public Effect possibleEffectsExplanation(ObjectInstance o1, ObjectInstance o2, ObjectAttribute att) {
		try {
			AttributeValue a1 = o1.getAttributeVal(att);
			AttributeValue a2 = o2.getAttributeVal(att);
			if(a2.equals(a1)) return null;
			
			return new Effect(this, att, o1.objectClass, a2);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String name() {
		return "AssignmentEffect";
	}
	
}
