package pf;

import tomlg.doormax.PropositionalFunction;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectInstance;

public class TaxiAtPassenger extends PropositionalFunction {
	public TaxiAtPassenger() {
		super("TaxiAtPassenger");
	}

	@Override
	public boolean evaluate(OOMDPState state) {
		ObjectInstance taxi = null;
		for (ObjectInstance o : state.objects.values()) {
			if (o.objectClass.name.equals("taxi")) {
				taxi = o;
				break;
			}
		}

		ObjectInstance passenger = null;
		for (ObjectInstance o : state.objects.values()) {
			if (o.objectClass.name.equals("passenger")) {
				passenger = o;
				break;
			}
		}

		if (taxi.getAttributeVal("xLocation") == passenger.getAttributeVal("xLocation")
				&& taxi.getAttributeVal("yLocation") == passenger.getAttributeVal("yLocation"))
			return true;

		return false;
	}
}