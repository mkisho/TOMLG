package pf;

import tomlg.doormax.PropositionalFunction;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectInstance;

public class WallToWestOfTaxi extends PropositionalFunction {
	public WallToWestOfTaxi() {
		super("WallToWestOfTaxi");
	}

	@Override
	public boolean evaluate(OOMDPState state) {
		ObjectInstance taxi = null;
		for (ObjectInstance o:state.objects.values()) {
			if (o.objectClass.name.equals("taxi")) {
				taxi = o;
				break;
			}
		}
		Double taxiX = (Double)taxi.getAttributeVal("xLocation");
		Double taxiY = (Double)taxi.getAttributeVal("yLocation");
		for (ObjectInstance o :state.objects.values()) {
			if (o.objectClass.name.equals("wall")) {
				if(((Double)o.getAttributeVal("yLocation") == taxiY)
					&& ((Double)o.getAttributeVal("xLocation") == taxiX -1))
				return true;
			}
		}
		return false;
	}
}