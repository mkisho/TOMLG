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
		int taxiX = (int)taxi.getAttributeVal("xLocation");
		int taxiY = (int)taxi.getAttributeVal("yLocation");
		for (ObjectInstance o :state.objects.values()) {
			if (o.objectClass.name.equals("wall")) {
				if(((int)o.getAttributeVal("xLocation") == taxiX)
					&& ((int)o.getAttributeVal("yLocation") == taxiY -1))
				return true;
			}
		}
		return false;
	}
}