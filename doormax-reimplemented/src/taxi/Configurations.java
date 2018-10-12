package taxi;

import doormax.PropositionalFunction;
import taxi.pf.PassengerInTaxi;
import taxi.pf.TaxiAtPassenger;
import taxi.pf.WallToEastOfTaxi;
import taxi.pf.WallToNorthOfTaxi;
import taxi.pf.WallToSouthOfTaxi;
import taxi.pf.WallToWestOfTaxi;

public final class Configurations {
	public static final String NORTH = "taxiMoveNorth";
	public static final PropositionalFunction WALL_NORHT_PF = new WallToNorthOfTaxi();
	public static final String EAST = "taxiMoveEast";
	public static final PropositionalFunction WALL_EAST_PF = new WallToEastOfTaxi();
	public static final String SOUTH = "taxiMoveSouth";
	public static final PropositionalFunction WALL_SOUTH_PF = new WallToSouthOfTaxi();
	public static final String WEST = "taxiMoveWest";
	public static final PropositionalFunction WALL_WEST_PF = new WallToWestOfTaxi();
	public static final String PICK = "pickupPassenger";
	public static final PropositionalFunction TAXI_AT_PASSENGER_PF = new TaxiAtPassenger();
	public static final String DROP = "dropOffPassenger";
	public static final PropositionalFunction PASSENGER_IN_TAXI_PF = new PassengerInTaxi();

	public static final String TAXI_CLASS_NAME = "taxi";
	public static final String TAXI_ATT_Y = "yLocation";
	public static final String TAXI_ATT_X = "xLocation";
}
