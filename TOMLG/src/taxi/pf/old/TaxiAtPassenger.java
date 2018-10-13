package taxi.pf.old;

import doormax.OOMDPState;
import doormax.ObjectInstance;
import doormax.PropositionalFunction;
import taxi.Configurations;

public class TaxiAtPassenger extends PropositionalFunction {
	public TaxiAtPassenger() {
		super("TaxiAtPassenger");
	}

	@Override
	public boolean evaluate(OOMDPState state) {
		ObjectInstance passenger = state.getObjectOfClass(Configurations.PASSENGER_CLASS_NAME);
		ObjectInstance taxi = state.getObjectOfClass(Configurations.TAXI_CLASS_NAME);

		if (passenger == null || taxi == null)
			throw new RuntimeException("Nooooo, errou");

		if (taxi.getAttributeValByName("xLocation").equals(passenger.getAttributeValByName(Configurations.PASSENGER_ATT_X))
				&& taxi.getAttributeValByName("yLocation").equals(passenger.getAttributeValByName(Configurations.PASSENGER_ATT_Y)))
			return true;

		return false;
	}
}