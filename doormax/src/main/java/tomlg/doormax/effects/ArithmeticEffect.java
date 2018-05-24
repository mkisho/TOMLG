package tomlg.doormax.effects;

import tomlg.doormax.Effect;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectInstance;

public class ArithmeticEffect extends EffectType {

	@Override
	public ObjectInstance doEffect(ObjectInstance object, ObjectAttribute attribute, Double number) {

		ObjectInstance objectWithEffect = object.makeCopy();

		Double updatedAttribute = (Double) object.attributesVal.get(attribute);
		updatedAttribute += number;

		objectWithEffect.attributesVal.put(attribute, updatedAttribute);
		return objectWithEffect;
	}

	public Effect possibleEffectsExplanation(ObjectInstance o1, ObjectInstance o2, ObjectAttribute att) {
		try {
			double diference = (Double) o1.attributesVal.get(att) - (Double) o2.attributesVal.get(att);
			if (diference == 0)
				return null;
			return new Effect(this, att, o1.objectClass, diference);
		} catch (Exception e) {
			return null;
		}
	}

}
