package doormax.structures;

import java.io.Serializable;

import doormax.ObjectClass;
import doormax.structures.attribute.Attribute;
import doormax.structures.attribute.AttributeBoolean;
import doormax.structures.attribute.AttributeInteger;

public class AssignmentEffect extends EffectType implements Serializable{
	private static final long serialVersionUID = -6010459199171598446L;
	private String name = "AssignmentEffect";
	@Override
	public String name() {
		return this.name;
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
		value = ((Double) value).intValue();
		if (att instanceof AttributeInteger) {
			((AttributeInteger) att).setValue((Integer) value);
		} else if (att instanceof AttributeBoolean) {
			((AttributeBoolean) att).setValue(((Integer) value) == 0 ? false : true);
		} else
			assert (true == false);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		AssignmentEffect other = (AssignmentEffect) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	 
	
}
