package taxi.pf.old;

import doormax.OOMDPState;
import doormax.ObjectInstance;
import doormax.PropositionalFunction;
import doormax.structures.attribute.AttributeBoolean;
import taxi.Configurations;

public class PassengerInTaxi extends PropositionalFunction {
	private static final long serialVersionUID = 3499466464254924211L;

	public PassengerInTaxi() {
		super("PassengerInTaxi");
	}

	@Override
	public boolean evaluate(OOMDPState state) {
		/*
		 * ObjectInstance taxi = null; for (ObjectInstance o : state.objects.values()) {
		 * if (o.objectClass.name.equals(Configurations.TAXI_CLASS_NAME) { taxi = o; break; } }
		 */

		ObjectInstance passenger = state.getObjectOfClass(Configurations.PASSENGER_CLASS_NAME);
		if(passenger == null) throw new RuntimeException("Nooooo, errou");
		
		AttributeBoolean val = (AttributeBoolean) passenger.getAttributeValByName("inTaxi");
		return val.isValue();
	}
}