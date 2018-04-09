package tomlg.doormax.effects;

import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectInstance;

public class AssignmentEffect extends EffectType {

	@Override
	public ObjectInstance doEffect(ObjectInstance object, ObjectAttribute attribute, Double number) {
		ObjectInstance objectWithEffect = object.makeCopy();
		
		objectWithEffect.attributesVal.put(attribute, number);
		return objectWithEffect;
	}

}
