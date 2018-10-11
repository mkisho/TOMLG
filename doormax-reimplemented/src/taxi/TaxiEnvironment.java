package taxi;

import java.util.List;

import doormax.EnvironmentSimulator;
import doormax.OOMDPState;
import doormax.ObjectClass;
import doormax.ObjectInstance;
import doormax.structures.Action;
import doormax.structures.Attribute;
import doormax.structures.AttributeBoolean;
import doormax.structures.AttributeInteger;

import java.util.ArrayList;
import java.util.Collection;

public class TaxiEnvironment extends EnvironmentSimulator {

	public TaxiEnvironment(String name) {
		super(name);
	}

	@Override
	public OOMDPState simulateAction(OOMDPState state0, Action action) {
		Double xTaxi = 0.0;
		Double yTaxi = 0.0;
		Double xDestino = 0.0;
		Double yDestino = 0.0;
		Double xPassenger = 0.0;
		Double yPassenger = 0.0;
		boolean passengerIn = false;
		OOMDPState state1 = state0.copy();
		boolean touchWallNorth = false;
		boolean touchWallSouth = false;
		boolean touchWallEast = false;
		boolean touchWallWest = false;
		// String goalId= "";
		List<String> horizontalIds = new ArrayList<String>();
		List<String> verticalIds = new ArrayList<String>();
		Collection<ObjectInstance> collection = state0.getObjects();
		for (ObjectInstance objTemp : collection) {
			ObjectClass obj = objTemp.getOclass();
			Object temp;
			if (objTemp.getClass().getName().equals("taxi")) {
				// taxiId = objTemp.getId();
				for (Attribute attribute : obj.getAttributes()) {
					if (attribute.getName().equals("xLocation")) {
						xTaxi = ((AttributeInteger) attribute).getDoubleValue();
					} else if (attribute.getName().equals("yLocation")) {
						yTaxi = ((AttributeInteger) attribute).getDoubleValue();
					}

					else if (attribute.getName().equals("passengerInTaxi")) {
						temp = ((AttributeBoolean) attribute).getDoubleValue();
						passengerIn = Boolean.parseBoolean(temp.toString());
					}
				}
			}

			if (objTemp.getClass().getName().equals("passenger")) {
				// passengerId = objTemp.getId();
				obj = objTemp.getOclass();
				for (Attribute attribute : obj.getAttributes()) {
					if (attribute.getName().equals("xLocation")) {
						xPassenger = ((AttributeInteger) attribute).getDoubleValue();

					} else if (attribute.getName().equals("yLocation")) {
						yPassenger = ((AttributeInteger) attribute).getDoubleValue();
					}
				}

			}

			if (objTemp.getClass().getName().equals("goalLocation")) {
				// goalId = objTemp.getId();
				obj = objTemp.getOclass();

				for (Attribute attribute : obj.getAttributes()) {
					if (attribute.getName().equals("xLocation")) {
						xDestino = ((AttributeInteger) attribute).getDoubleValue();
					} else if (attribute.getName().equals("yLocation")) {
						yDestino = ((AttributeInteger) attribute).getDoubleValue();
					}
				}
			}

			if (objTemp.getClass().getName().equals("horizontalWall")) {
				horizontalIds.add(objTemp.getId());
			}

			if (objTemp.getClass().getName().equals("verticalWall")) {
				verticalIds.add(objTemp.getId());
			}
		}
		Double offset = -1.0;
		Double wallBegin = -1.0;
		Double wallEnd = -1.0;
		for (String str : horizontalIds) {
			ObjectInstance wall = state0.getObjById(str);
			for (Attribute attribute : wall.getOclass().getAttributes()) {
				if (attribute.getName().equals("wallOffSet")) {
					offset = ((AttributeInteger) attribute).getDoubleValue();
				}
			}

			if ((Double.compare(yTaxi, offset)) == 0) {
				for (Attribute attribute : wall.getOclass().getAttributes()) {
					if (attribute.getName().equals("leftStartOfWall")) {
						wallBegin = ((AttributeInteger) attribute).getDoubleValue();
					} else if (attribute.getName().equals("rightStartOfWall")) {
						wallEnd = ((AttributeInteger) attribute).getDoubleValue();
					}

				}
				if ((Double.compare(xTaxi, wallBegin)) >= 0 && (Double.compare(xTaxi, wallEnd)) <= 0) {
					touchWallSouth = true;
				}
			}

			if ((Double.compare(yTaxi + 1, offset)) == 0) {
				for (Attribute attribute : wall.getOclass().getAttributes()) {
					if (attribute.getName().equals("leftStartOfWall")) {
						wallBegin = ((AttributeInteger) attribute).getDoubleValue();
					} else if (attribute.getName().equals("rightStartOfWall")) {
						wallEnd = ((AttributeInteger) attribute).getDoubleValue();
					}

				}
				if ((Double.compare(xTaxi, wallBegin)) >= 0 && (Double.compare(xTaxi, wallEnd)) <= 0) {
					touchWallNorth = true;
				}
			}
		}

		for (String str : verticalIds) {
			ObjectInstance wall = state0.getObjById(str);
			for (Attribute attribute : wall.getOclass().getAttributes()) {
				if (attribute.getName().equals("wallOffSet")) {
					offset = ((AttributeInteger) attribute).getDoubleValue();
				}
			}

			if ((Double.compare(xTaxi, offset)) == 0) {
				for (Attribute attribute : wall.getOclass().getAttributes()) {
					if (attribute.getName().equals("bottomOfWall")) {
						wallBegin = ((AttributeInteger) attribute).getDoubleValue();
					} else if (attribute.getName().equals("topOfWall")) {
						wallEnd = ((AttributeInteger) attribute).getDoubleValue();
					}

				}
				if ((Double.compare(yTaxi, wallBegin)) >= 0 && (Double.compare(yTaxi, wallEnd)) <= 0) {
					touchWallWest = true;
				}
			}

			if ((Double.compare(xTaxi + 1, offset)) == 0) {
				for (Attribute attribute : wall.getOclass().getAttributes()) {
					if (attribute.getName().equals("bottomOfWall")) {
						wallBegin = ((AttributeInteger) attribute).getDoubleValue();
					} else if (attribute.getName().equals("topOfWall")) {
						wallEnd = ((AttributeInteger) attribute).getDoubleValue();
					}

				}
				if ((Double.compare(yTaxi, wallBegin)) >= 0 && (Double.compare(yTaxi, wallEnd)) <= 0) {
					touchWallEast = true;
				}

			}
		}

		touchWallNorth = (yTaxi >= 8 ? true : touchWallNorth);
		touchWallSouth = (yTaxi <= 0 ? true : touchWallSouth);

		touchWallEast = (xTaxi >= 8 ? true : touchWallEast);
		touchWallWest = (xTaxi <= 0 ? true : touchWallWest);

		if (action.getName().equals("taxiMoveNorth")) {

			if (touchWallNorth) {

			} else {
				ObjectInstance tmpInst = state1.getObjectOfClass("taxi");

				for (Attribute att : tmpInst.getAttributes()) {
					if (att.getName().equals("yLocation")) {
						((AttributeInteger) att).setValue((int) Math.round(++yTaxi));
					}
				}

			}

		}
		if (action.getName().equals("taxiMoveEast")) {
			if (touchWallEast) {

			} else {
				ObjectInstance tmpInst = state1.getObjectOfClass("taxi");
				for (Attribute attribute : tmpInst.getAttributes()) {
					if (attribute.getName().equals("xLocation")) {
						((AttributeInteger) attribute).setValue((int) Math.round(++xTaxi));
					}
				}
			}
		}
		if (action.getName().equals("taxiMoveSouth")) {
			if (touchWallSouth) {

			} else {
				ObjectInstance tmpInst;
				tmpInst = state1.getObjectOfClass("taxi");
				for (Attribute attribute : tmpInst.getAttributes()) {
					if (attribute.getName().equals("yLocation")) {
						((AttributeInteger) attribute).setValue((int) Math.round(--yTaxi));

					}
				}

			}
		}

		if (action.getName().equals("taxiMoveWest")) {
			if (touchWallWest) {

			} else {
				ObjectInstance tmpInst;
				tmpInst = state1.getObjectOfClass("taxi");
				for (Attribute attribute : tmpInst.getAttributes()) {
					if (attribute.getName().equals("xLocation")) {
						((AttributeInteger) attribute).setValue((int) Math.round(--xTaxi));
					}
				}
			}
		}

		if (action.getName().equals("pickupPassenger")) {
			if (passengerIn == true || xTaxi != xPassenger || yTaxi != yPassenger) {
			} else {
				ObjectInstance tmpInst;

				tmpInst = new ObjectInstance(state1.getObjectOfClass("passenger"));
				for (Attribute attribute : tmpInst.getAttributes()) {
					if (attribute.getName().equals("xLocation")) {
						((AttributeBoolean) attribute).setValue(true);
					}
				}

				tmpInst = state1.getObjectOfClass("taxi");
				for (Attribute att : tmpInst.getAttributes()) {
					if (att.getName().equals("xLocation")) {
						((AttributeBoolean) att).setValue(true);
					}
				}

			}
		}

		if (action.getName().equals("dropOffPassenger")) {
			if (passengerIn = false || xTaxi != xDestino || yTaxi != yDestino) {
			} else {
				ObjectInstance tmpInst;

				tmpInst = new ObjectInstance(state1.getObjectOfClass("passenger"));
				for (Attribute attribute : tmpInst.getAttributes()) {
					if (attribute.getName().equals("xLocation")) {
						((AttributeBoolean) attribute).setValue(false);

					}
				}

				tmpInst = state1.getObjectOfClass("taxi");
				for (Attribute attribute : tmpInst.getAttributes()) {
					if (attribute.getName().equals("xLocation")) {
						((AttributeBoolean) attribute).setValue(false);
					}
				}

			}
		}

		return state1;
	}

}