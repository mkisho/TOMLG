package tomlg.doormax.effects;

import javax.management.RuntimeErrorException;

import tomlg.doormax.Effect;
import tomlg.doormax.oomdpformalism.AttributeValue;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectInstance;
import tomlg.doormax.oomdpformalism.domains.AttributeValueInteger;

public class ArithmeticEffect extends EffectType {

	@Override
	public ObjectInstance applyEffect(ObjectInstance object, ObjectAttribute attribute, AttributeValue value) {

		ObjectInstance objectWithEffect = new ObjectInstance(object);

		if(value instanceof AttributeValueInteger) {
			AttributeValueInteger updatedAttribute = (AttributeValueInteger) object.attributesVal.get(attribute);
			int val = ((AttributeValueInteger) value).value;
			objectWithEffect.attributesVal.put(attribute, new AttributeValueInteger(updatedAttribute.value + val));
			return objectWithEffect;
			
		}
		throw new RuntimeErrorException(null, "Tá errado");
	}

	public Effect possibleEffectsExplanation(ObjectInstance o1, ObjectInstance o2, ObjectAttribute att) {
		try {
			AttributeValue value1 = o1.attributesVal.get(att);
			AttributeValue value2 = o2.attributesVal.get(att);
			
			if(value1 instanceof AttributeValueInteger && value2 instanceof AttributeValueInteger  ) {
				int val1 = ((AttributeValueInteger)value1).value;
				int val2 = ((AttributeValueInteger)value2).value;
				if(val1 == val2) return null;
				
				return new Effect(this, att, o1.objectClass, new AttributeValueInteger(val1 - val2));
			}else return null;
		} catch (Exception e) {
			return null;
		}
	}

}
