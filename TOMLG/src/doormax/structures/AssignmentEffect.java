package doormax.structures;

import doormax.ObjectClass;
import doormax.structures.attribute.Attribute;
import doormax.structures.attribute.AttributeBoolean;
import doormax.structures.attribute.AttributeInteger;

public class AssignmentEffect extends EffectType {
	@Override
	public String name() {
		return "AssignmentEffect";
	}

	@Override
	public Effect possibleEffectsExplanation(Attribute oldAtt, Attribute newAtt, ObjectClass objClass) {
		assert (oldAtt.getName().equals(newAtt.getName()));

		if (oldAtt instanceof AttributeInteger || oldAtt instanceof AttributeBoolean) {
			Double val1 = oldAtt.getDoubleValue();
			Double val2 = newAtt.getDoubleValue();

			if (val1 == val2)
				return null;

			return new Effect(this, oldAtt, objClass, val2);
		} else
			return null;
	}

	public void apply(Attribute att, Object value) {
		if (att instanceof AttributeInteger) {
			((AttributeInteger) att).setValue((Integer) value);
		} else if (att instanceof AttributeBoolean) {
			((AttributeBoolean) att).setValue(((Integer) value) == 0 ? false : true);
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
