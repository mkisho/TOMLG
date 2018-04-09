package tomlg.doormax.effects;

import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectInstance;

public class ArithmeticEffect extends EffectType {

	@Override
	public ObjectInstance doEffect(ObjectInstance object, ObjectAttribute attribute, Double number) {

		ObjectInstance objectWithEffect = object.makeCopy();

		Double updatedAttribute = (Double)object.attributesVal.get(attribute);
		updatedAttribute += number;
		
		objectWithEffect.attributesVal.put(attribute, updatedAttribute);
		return objectWithEffect;
	}

}
