package doormax.structures;
import doormax.ObjectClass;

public abstract class EffectType {
	public abstract Effect possibleEffectsExplanation(Attribute oldAtt, Attribute newAtt, ObjectClass objClass);

	public abstract String name();
	
}
