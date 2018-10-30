package doormax.structures;

import java.io.Serializable;

import doormax.ObjectClass;
import doormax.structures.attribute.Attribute;

public abstract class EffectType implements Serializable{
	private static final long serialVersionUID = 3162920907094991998L;

	public abstract Effect possibleEffectsExplanation(Attribute oldAtt, Attribute newAtt, ObjectClass objClass);

	public abstract String name();
	
	public abstract void apply(Attribute att, Object value);

}
