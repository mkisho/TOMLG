package taxi.pf;

import doormax.OOMDPState;
import doormax.ObjectInstance;
import doormax.PropositionalFunction;
import doormax.structures.AttributeBoolean;
public class TaxiAgentGoal extends PropositionalFunction {
	public TaxiAgentGoal() {
		super("TaxiAgentGoal");
	}

	@Override
	public boolean evaluate(OOMDPState state) {
		/*
		 * ObjectInstance taxi = null; for (ObjectInstance o : state.objects.values()) {
		 * if (o.objectClass.name.equals("Taxi") { taxi = o; break; } }
		 */
		
		ObjectInstance passenger = state.getObjectOfClass("passenger");

		if(passenger == null) throw new RuntimeException("Nooooo, errou");
		
		AttributeBoolean val = (AttributeBoolean) passenger.getAttributeValByName("inTaxi");
		return val.isValue();
	}
}