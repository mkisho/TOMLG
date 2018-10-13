package doormax.structures;
import doormax.ObjectClass;
import doormax.structures.attribute.Attribute;
import doormax.structures.attribute.AttributeBoolean;
import doormax.structures.attribute.AttributeInteger;

public class ArithmeticEffect extends EffectType {

	@Override
	public Effect possibleEffectsExplanation(Attribute oldAtt, Attribute newAtt, ObjectClass objClass) {
		assert (oldAtt.getName().equals(newAtt.getName()));
		if (oldAtt instanceof AttributeInteger) {
			int val1 = ((AttributeInteger) oldAtt).getValue();
			int val2 = ((AttributeInteger) newAtt).getValue();

			if (val1 == val2)
				return null;

			int assign = val2 - val1;
			return new Effect(this, oldAtt, objClass, assign + 0.0);
		} else
			return null;
	}

	@Override
	public String name() {
		return "ArithmeticEffect";
	}
	
	public void apply(Attribute att, Object value) {
		if (att instanceof AttributeInteger) {
			((AttributeInteger) att).setValue(((AttributeInteger) att).getValue() + (Integer) value);
		} else if (att instanceof AttributeBoolean) {//TODO ???
			((AttributeBoolean) att).setValue(((Integer) value) == 1 ? true : false);
		} else
			assert (true == false);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

}
