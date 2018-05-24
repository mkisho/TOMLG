package pf;

import tomlg.doormax.PropositionalFunction;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectInstance;

public class PassengerInTaxi extends PropositionalFunction {
	public PassengerInTaxi() {
		super("PassengerInTaxi");
	}

	@Override
	public boolean evaluate(OOMDPState state) {
		/*
		 * ObjectInstance taxi = null; for (ObjectInstance o : state.objects.values()) {
		 * if (o.objectClass.name.equals("Taxi") { taxi = o; break; } }
		 */

		ObjectInstance passenger = null;
		for (ObjectInstance o : state.objects.values()) {
			if (o.objectClass.name.equals("passenger")) {
				if ((Boolean) o.getAttributeVal("inTaxi")) {
					return true;
				} else
					return false;
			}
		}
		return false;
	}
}