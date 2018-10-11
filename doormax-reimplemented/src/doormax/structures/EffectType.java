package doormax.structures;
import doormax.ObjectClass;
import doormax.structures.attribute.Attribute;

public abstract class EffectType {
	public abstract Effect possibleEffectsExplanation(Attribute oldAtt, Attribute newAtt, ObjectClass objClass);

	public abstract String name();
	
}
