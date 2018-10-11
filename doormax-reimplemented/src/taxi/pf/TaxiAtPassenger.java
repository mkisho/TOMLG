package taxi.pf;

import doormax.OOMDPState;
import doormax.ObjectInstance;
import doormax.PropositionalFunction;

public class TaxiAtPassenger extends PropositionalFunction {
	public TaxiAtPassenger() {
		super("TaxiAtPassenger");
	}

	@Override
	public boolean evaluate(OOMDPState state) {
		ObjectInstance passenger = state.getObjectOfClass("passenger");
		ObjectInstance taxi = state.getObjectOfClass("taxi");

		if (passenger == null || taxi == null)
			throw new RuntimeException("Nooooo, errou");

		if (taxi.getAttributeValByName("xLocation").equals(passenger.getAttributeValByName("xLocation"))
				&& taxi.getAttributeValByName("yLocation").equals(passenger.getAttributeValByName("yLocation")))
			return true;

		return false;
	}
}