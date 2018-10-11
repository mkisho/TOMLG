package taxi.pf;

import doormax.OOMDPState;
import doormax.ObjectInstance;
import doormax.PropositionalFunction;
import doormax.structures.AttributeInteger;

public class WallToWestOfTaxi extends PropositionalFunction {
	public WallToWestOfTaxi() {
		super("WallToWestOfTaxi");
	}

	@Override
	public boolean evaluate(OOMDPState state) {
		ObjectInstance taxi = state.getObjectOfClass("taxi");
		if (taxi == null)
			throw new RuntimeException("Nooooo, errou");

		int taxiX = ((AttributeInteger) taxi.getAttributeValByName("xLocation")).getValue();
		int taxiY = ((AttributeInteger) taxi.getAttributeValByName("yLocation")).getValue();
		for (ObjectInstance o : state.getObjectsOfClass("wall")) {
			int wallX = ((AttributeInteger) o.getAttributeValByName("xLocation")).getValue();
			int wallY = ((AttributeInteger) o.getAttributeValByName("yLocation")).getValue();

			if (wallY == taxiY && wallX == taxiX - 1)
				return true;
		}
		return false;
	}
}