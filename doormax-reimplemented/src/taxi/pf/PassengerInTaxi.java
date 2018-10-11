package taxi.pf;

import doormax.OOMDPState;
import doormax.ObjectInstance;
import doormax.PropositionalFunction;

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

		ObjectInstance passenger = state.getObjectOfClass("passenger");
		if(passenger == null) throw new RuntimeException("Nooooo, errou");
		
		AttributeValueBoolean val =(AttributeValueBoolean) passenger.getAttributeValByName("inTaxi");
		return val.value;
	}
}