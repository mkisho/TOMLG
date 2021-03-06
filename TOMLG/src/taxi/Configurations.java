package taxi;

import java.util.Random;

import doormax.PropositionalFunction;
import taxi.pf.In;
import taxi.pf.On;
import taxi.pf.Touch;

public final class Configurations {
	public static final String TAXI_CLASS_NAME = "taxi";
	public static final String TAXI_ATT_Y = "yLocation";
	public static final String TAXI_ATT_X = "xLocation";
	public static final String TAXI_ATT_PASSENGER_INSIDE = "passengerInTaxi";

	public static final String PASSENGER_CLASS_NAME = "passenger";
	public static final String PASSENGER_ATT_Y = "yLocation";
	public static final String PASSENGER_ATT_X = "xLocation";
	public static final String PASSENGER_ATT_IN_TAXI = "inTaxi";

	public static final String WALL_CLASS_NAME = "wall";
	public static final String WALL_ATT_X = "";
	public static final String WALL_ATT_Y = "";

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
	
	public static final String DESTINATION_CLASS_NAME = "destination";

	public static final PropositionalFunction TAXI_ON_PASSENGER_PF = new On(TAXI_CLASS_NAME, PASSENGER_CLASS_NAME);
	public static final PropositionalFunction PASSENGER_IN_TAXI_PF = new In(TAXI_CLASS_NAME, PASSENGER_CLASS_NAME);
	public static final PropositionalFunction TAXI_ON_DESTINATION_PF = new On(TAXI_CLASS_NAME, DESTINATION_CLASS_NAME);



//////////////////////////////////
	public static final Random random = new Random(0);
	public static final int MAX_GOALS_GENERATED = 2;
	public static final String INTRINSIC_MOTIVATION_GOAL_LABEL = "IntrinsicMotivation";
	public static final boolean CONTINUE_WHEN_MIND_NOT_FOUND = true;
	public static final int MIN_NUM_SEQUENCES_DERIVE_FDI = 7;
}
