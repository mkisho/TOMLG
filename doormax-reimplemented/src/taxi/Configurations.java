package taxi;

import doormax.PropositionalFunction;
import taxi.pf.In;
import taxi.pf.On;
import taxi.pf.Touch;
import taxi.pf.old.PassengerInTaxi;
import taxi.pf.old.TaxiAtPassenger;
import taxi.pf.old.WallToEastOfTaxi;
import taxi.pf.old.WallToNorthOfTaxi;
import taxi.pf.old.WallToSouthOfTaxi;
import taxi.pf.old.WallToWestOfTaxi;

public final class Configurations {
	public static final String TAXI_CLASS_NAME = "taxi";
	public static final String TAXI_ATT_Y = "yLocation";
	public static final String TAXI_ATT_X = "xLocation";

	public static final String PASSENGER_CLASS_NAME = "passenger";
	public static final String PASSENGER_ATT_Y = "yLocation";
	public static final String PASSENGER_ATT_X = "xLocation";
	public static final String PASSENGER_ATT_IN_TAXI = "inTaxi";

	public static final String WALL_CLASS_NAME = "wall";
	public static final String WALL_ATT_X = "";
	public static final String WALL_ATT_Y = "";
	public static final String TAXI_PASSENGER_INSIDE = "passengerInTaxi";

	public static final String DIRECTION_NORTH = "North";
	public static final String DIRECTION_SOUTH = "South";
	public static final String DIRECTION_WEST = "West";
	public static final String DIRECTION_EAST = "East";

	public static final String WALL_VERTICAL = "verticalWall";
	public static final String WALL_VERTICAL_OFF_SET = "wallOffSet";
	public static final String WALL_VERTICAL_BOTTOM = "bottomOfWall";
	public static final String WALL_VERTICAL_TOP = "topOfWall";

	public static final String WALL_HORIZONTAL = "horizontalWall";
	public static final String WALL_HORIZONTAL_OFF_SET = "wallOffSet";
	public static final String WALL_HORIZONTAL_LEFT = "leftStartOfWall";
	public static final String WALL_HORIZONTAL_RIGHT = "rightStartOfWall";

	public static final String NORTH = "taxiMoveNorth";
	public static final PropositionalFunction WALL_NORHT_PF = new Touch(TAXI_CLASS_NAME, DIRECTION_NORTH);
	public static final String EAST = "taxiMoveEast";
	public static final PropositionalFunction WALL_EAST_PF = new Touch(TAXI_CLASS_NAME, DIRECTION_EAST);
	public static final String SOUTH = "taxiMoveSouth";
	public static final PropositionalFunction WALL_SOUTH_PF = new Touch(TAXI_CLASS_NAME, DIRECTION_SOUTH);
	public static final String WEST = "taxiMoveWest";
	public static final PropositionalFunction WALL_WEST_PF = new Touch(TAXI_CLASS_NAME, DIRECTION_WEST);
	public static final String PICK = "pickupPassenger";
	public static final String DROP = "dropOffPassenger";

	public static final PropositionalFunction TAXI_AT_PASSENGER_PF = new On(TAXI_CLASS_NAME, PASSENGER_CLASS_NAME);
	public static final PropositionalFunction PASSENGER_IN_TAXI_PF = new In(TAXI_CLASS_NAME, PASSENGER_CLASS_NAME);
}
