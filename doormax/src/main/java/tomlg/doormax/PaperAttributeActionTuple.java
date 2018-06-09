package tomlg.doormax;

import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectClass;

public class PaperAttributeActionTuple {
	public ObjectClass oClass;
	public ObjectAttribute att;
	public Action ga;
	
	public PaperAttributeActionTuple(ObjectClass oClass, ObjectAttribute att, Action ga) {
		super();
		this.oClass = oClass;
		this.att = att;
		this.ga = ga;
	}

	@Override 
	public int hashCode() {
		return oClass.hashCode() + att.hashCode() + ga.hashCode();
	}
	
	@Override
	public String toString() {
		return "(" + oClass.name + ", " + att.name + ", " + ga + ")";
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof PaperAttributeActionTuple) {
			return ((PaperAttributeActionTuple) other).oClass.equals(this.oClass) &&
					((PaperAttributeActionTuple) other).att.equals(this.att) &&
					((PaperAttributeActionTuple) other).ga.equals(this.ga);
		}
		return false;
	}
	
}
