package taxi.pf;

import doormax.OOMDPState;
import doormax.ObjectInstance;
import doormax.structures.attribute.AttributeInteger;
import taxi.Configurations;

public class Touch extends doormax.PropositionalFunction {
	private final String objClass;
	private final String direction;

	public Touch(String objClass, String direction) {
		super("Touch(" + objClass + ", " + direction + ")");
		this.objClass = objClass;
		this.direction = direction;
		assert (this.direction.equals(Configurations.DIRECTION_EAST)
				|| this.direction.equals(Configurations.DIRECTION_WEST)
				|| this.direction.equals(Configurations.DIRECTION_NORTH)
				|| this.direction.equals(Configurations.DIRECTION_SOUTH));
	}

	@Override
	public boolean evaluate(OOMDPState state) {
		ObjectInstance objIntance = state.getObjectOfClass(this.objClass);// state.getObjectOfClass(Configurations.TAXI_CLASS_NAME);
		if (objIntance == null)
			throw new RuntimeException("Touch with wrong class: " + this.objClass);

		int taxiX = ((AttributeInteger) objIntance.getAttributeValByName(Configurations.TAXI_ATT_X)).getValue();
		int taxiY = ((AttributeInteger) objIntance.getAttributeValByName(Configurations.TAXI_ATT_Y)).getValue();

		String wallName = null;
		if (this.direction.equals(Configurations.DIRECTION_NORTH)
				|| this.direction.equals(Configurations.DIRECTION_SOUTH)) {
			wallName = Configurations.WALL_HORIZONTAL;
		} else {
			wallName = Configurations.WALL_VERTICAL;
		}

		for (ObjectInstance o : state.getObjectsOfClass(wallName)) {
			if (this.direction.equals(Configurations.DIRECTION_NORTH)
					|| this.direction.equals(Configurations.DIRECTION_SOUTH)) {

				int wallOffSet = ((AttributeInteger) o.getAttributeValByName(Configurations.WALL_HORIZONTAL_OFF_SET))
						.getValue();
				int leftStartOfWall = ((AttributeInteger) o.getAttributeValByName(Configurations.WALL_HORIZONTAL_LEFT))
						.getValue();
				int rightStartOfWall = ((AttributeInteger) o
						.getAttributeValByName(Configurations.WALL_HORIZONTAL_RIGHT)).getValue();

				if (!(between(leftStartOfWall, rightStartOfWall, taxiX)))
					continue;
				if (this.direction.equals(Configurations.DIRECTION_NORTH) && (taxiY == wallOffSet)) {
					return true;
				} else if (this.direction.equals(Configurations.DIRECTION_SOUTH) && (taxiY + 1 == wallOffSet))
					return true;
			} else {
				int wallOffSet = ((AttributeInteger) o.getAttributeValByName(Configurations.WALL_VERTICAL_OFF_SET))
						.getValue();
				int bottomOfWall = ((AttributeInteger) o.getAttributeValByName(Configurations.WALL_VERTICAL_BOTTOM))
						.getValue();
				int topOfWall = ((AttributeInteger) o.getAttributeValByName(Configurations.WALL_VERTICAL_TOP))
						.getValue();

				if (!(between(bottomOfWall, topOfWall, taxiY)))
					continue;

				if (this.direction.equals(Configurations.DIRECTION_WEST) && ((taxiX  == wallOffSet))) {
					return true;
				} else if (this.direction.equals(Configurations.DIRECTION_EAST) && (taxiX + 1 == wallOffSet)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean between(int start, int end, int x) {
		assert (start < end);
		return (start <= x && x <= end);
	}

}
