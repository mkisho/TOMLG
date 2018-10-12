package taxi.pf.old;

import doormax.OOMDPState;
import doormax.ObjectInstance;
import doormax.PropositionalFunction;
import doormax.structures.attribute.AttributeInteger;
import taxi.Configurations;

public class WallToSouthOfTaxi extends PropositionalFunction {
	public WallToSouthOfTaxi() {
		super("WallToSouthOfTaxi");
	}

	@Override
	public boolean evaluate(OOMDPState state) {
		ObjectInstance taxi = state.getObjectOfClass(Configurations.TAXI_CLASS_NAME);
		if (taxi == null)
			throw new RuntimeException("Nooooo, errou");

		int taxiX = ((AttributeInteger) taxi.getAttributeValByName(Configurations.TAXI_ATT_X)).getValue();
		int taxiY = ((AttributeInteger) taxi.getAttributeValByName(Configurations.TAXI_ATT_Y)).getValue();
		for (ObjectInstance o : state.getObjectsOfClass(Configurations.WALL_CLASS_NAME)) {
			int wallX = ((AttributeInteger) o.getAttributeValByName(Configurations.WALL_ATT_X)).getValue();
			int wallY = ((AttributeInteger) o.getAttributeValByName(Configurations.WALL_ATT_Y)).getValue();

			if (wallX == taxiX && wallY == taxiY - 1)
				return true;
		}
		return false;
	}
}