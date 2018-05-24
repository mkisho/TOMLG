package pf;

import tomlg.doormax.PropositionalFunction;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectInstance;

public class WallToSouthOfTaxi extends PropositionalFunction {
	public WallToSouthOfTaxi() {
		super("WallToSouthOfTaxi");
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
				if(((Double)o.getAttributeVal("xLocation") == taxiX)
					&& ((Double)o.getAttributeVal("yLocation") == taxiY -1))
				return true;
			}
		}
		return false;
	}
}