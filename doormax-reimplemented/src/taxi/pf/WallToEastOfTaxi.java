package taxi.pf;

import doormax.OOMDPState;
import doormax.ObjectInstance;
import doormax.PropositionalFunction;

public class WallToEastOfTaxi extends PropositionalFunction {
	public WallToEastOfTaxi() {
		super("WallToEastOfTaxi");
	}

	@Override
	public boolean evaluate(OOMDPState state) {
		ObjectInstance taxi = state.getObjectOfClass("taxi");
		if (taxi == null)
			throw new RuntimeException("Nooooo, errou");

		int taxiX = ((AttributeValueInteger) taxi.getAttributeValByName("xLocation")).value;
		int taxiY = ((AttributeValueInteger) taxi.getAttributeValByName("yLocation")).value;
		for (ObjectInstance o : state.getObjectsOfClass("wall")) {
			int wallX = ((AttributeValueInteger) o.getAttributeValByName("xLocation")).value;
			int wallY = ((AttributeValueInteger) o.getAttributeValByName("yLocation")).value;

			if (wallY == taxiY && wallX == taxiX + 1)
				return true;

		}
		return false;
	}
}