package pf;

import tomlg.doormax.PropositionalFunction;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectInstance;

public class WallToEastOfTaxi extends PropositionalFunction {
	public WallToEastOfTaxi() {
		super("WallToEastOfTaxi");
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
				if(((int)o.getAttributeVal("yLocation") == taxiY)
					&& ((int)o.getAttributeVal("xLocation") == taxiX + 1))
				return true;
			}
		}
		return false;
	}
}