package doormax.structures;

import doormax.ObjectClass;
import doormax.structures.attribute.Attribute;
import doormax.structures.attribute.AttributeBoolean;
import doormax.structures.attribute.AttributeInteger;

public class ArithmeticEffect extends EffectType {
	private static final long serialVersionUID = 2545374839507160749L;
	private String name = "ArithmeticEffect";

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
		return this.name;
	}

	public void apply(Attribute att, Object value) {
		value = ((Double) value).intValue();
		if (att instanceof AttributeInteger) {
			((AttributeInteger) att).setValue(((AttributeInteger) att).getValue() + ((Integer) value));
		} else if (att instanceof AttributeBoolean) {// TODO ???
			((AttributeBoolean) att).setValue(((Integer) value) == 1 ? true : false);
		} else
			assert (true == false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		ArithmeticEffect other = (ArithmeticEffect) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
