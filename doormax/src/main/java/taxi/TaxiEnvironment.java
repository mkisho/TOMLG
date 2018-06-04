package taxi;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import tomlg.doormax.EnvironmentSimulator;
import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectClass;
import tomlg.doormax.oomdpformalism.ObjectInstance;

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
		OOMDPState state1 = new OOMDPState(state0);
		boolean touchWallNorth = false;
		boolean touchWallSouth = false;
		boolean touchWallEast = false;
		boolean touchWallWest = false;
		String taxiId = "";
		String passengerId= "";
		//	String goalId= "";
		List<String> horizontalIds = new ArrayList<String>();
		List<String>  verticalIds = new ArrayList<String>();
		Collection<ObjectInstance> collection = state0.objects.values();
		for (ObjectInstance objTemp : collection){	
			ObjectClass obj = objTemp.objectClass;
			Object temp;
			if(objTemp.getObjectClass().name.equals("taxi")) {
				taxiId = objTemp.getId();
				for (ObjectAttribute att : obj.attributes) {
					if(att.name.equals("xLocation")) {
						xTaxi = (Double) objTemp.attributesVal.get(att);
					}
					else if(att.name.equals("yLocation")) {
						yTaxi = (Double) objTemp.attributesVal.get(att);
					}

					else if(att.name.equals("passengerInTaxi")) {
						temp = objTemp.attributesVal.get(att);
						passengerIn = Boolean.parseBoolean(temp.toString());	
					}
				}
			}


			if(objTemp.getObjectClass().name.equals("passenger")) {
				passengerId = objTemp.getId();
				obj = objTemp.objectClass;
				for (ObjectAttribute att : obj.attributes) {
					if(att.name.equals("xLocation")) {
						xPassenger = (Double) objTemp.attributesVal.get(att);
					}
					else if(att.name.equals("yLocation")) {
						yPassenger = (Double) objTemp.attributesVal.get(att);
					}
				}

			}

			if(objTemp.getObjectClass().name.equals("goalLocation")) {
				//				goalId = objTemp.getId();
				obj = objTemp.objectClass;

				for (ObjectAttribute att : obj.attributes) {
					if(att.name.equals("xLocation")) {
						xDestino = (Double) objTemp.attributesVal.get(att);
					}
					else if(att.name.equals("yLocation")) {
						yDestino = (Double) objTemp.attributesVal.get(att);
					}
				}
			}

			if(objTemp.getObjectClass().name.equals("horizontalWall")) {
				horizontalIds.add(objTemp.getId());
			}

			if(objTemp.getObjectClass().name.equals("verticalWall")) {
				verticalIds.add(objTemp.getId());
			}
		}
		Double offset=-1.0;
		Double wallBegin=-1.0;
		Double wallEnd=-1.0;
		for (String str : horizontalIds){
			ObjectInstance wall = state0.objects.get(str);
			for (ObjectAttribute att : wall.objectClass.attributes) {
				if(att.name.equals("wallOffSet")) {
					offset = (Double)wall.attributesVal.get(att);
				}
			}

			if(yTaxi == offset+1) {
				for (ObjectAttribute att : wall.objectClass.attributes) {
					if(att.name.equals("leftStartOfWall")) {
						wallBegin =(Double)wall.attributesVal.get(att);
					}
					else if(att.name.equals("rightStartOfWall")) {
						wallEnd = (Double)wall.attributesVal.get(att);
					}
				}
				if(xTaxi > wallBegin && xTaxi<wallEnd) {
					touchWallWest=true;
				}	

			}

			if(yTaxi == offset) {
				for (ObjectAttribute att : wall.objectClass.attributes) {
					if(att.name.equals("leftStartOfWall")) {
						wallBegin =(Double)wall.attributesVal.get(att);
					}
					else if(att.name.equals("rightStartOfWall")) {
						wallEnd = (Double)wall.attributesVal.get(att);
					}
				}
				if(xTaxi > wallBegin && xTaxi<wallEnd) {
					touchWallEast=true;
				}	
			}
		}


		for (String str : verticalIds){
			ObjectInstance wall = state0.objects.get(str);
			for (ObjectAttribute att : wall.objectClass.attributes) {
				if(att.name.equals("wallOffSet")) {
					offset = (Double)wall.attributesVal.get(att);
				}
			}

			if(xTaxi == offset+1) {
				for (ObjectAttribute att : wall.objectClass.attributes) {
					if(att.name.equals("bottomOfWall")) {
						wallBegin =(Double)wall.attributesVal.get(att);
					}
					else if(att.name.equals("bottomOfWall")) {
						wallEnd = (Double)wall.attributesVal.get(att);
					}
				}
				if(yTaxi > wallBegin && yTaxi<wallEnd) {
					touchWallSouth=true;
				}
			}

			if(xTaxi == offset) {
				for (ObjectAttribute att : wall.objectClass.attributes) {
					if(att.name.equals("bottomOfWall")) {
						wallBegin =(Double)wall.attributesVal.get(att);
					}
					else if(att.name.equals("bottomOfWall")) {
						wallEnd = (Double)wall.attributesVal.get(att);
					}
				}
				if(yTaxi > wallBegin && yTaxi<wallEnd) {
					touchWallNorth=true;
				}	
			}
		}

		if(action.name.equals("taxiMoveNorth")) {

			if(touchWallNorth) {

			}
			else {
				ObjectInstance tmpInst;
				ObjectAttribute tmpAtt;
				tmpInst = state1.objects.get(taxiId);
				for (ObjectAttribute att : tmpInst.attributesVal.keySet()) {
					if(att.name.equals("yLocation")) {
						tmpAtt = (ObjectAttribute) att;
						tmpInst.attributesVal.put(tmpAtt, ++yTaxi);
						state1.objects.put(tmpInst.getId(), tmpInst);						

					}
				}

			}

		}
		if(action.name.equals("taxiMoveEast")) {
			if(touchWallEast) {

			}
			else {
				ObjectInstance tmpInst;
				ObjectAttribute tmpAtt;
				tmpInst = state1.objects.get(taxiId); 
				for (ObjectAttribute att : tmpInst.attributesVal.keySet()) {
					if(att.name.equals("xLocation")) {
						tmpAtt = (ObjectAttribute) att;
						tmpInst.attributesVal.put(tmpAtt, ++xTaxi);
						state1.objects.put(tmpInst.getId(), tmpInst);
					}
				}
			}		
		}
		if(action.name.equals("taxiMoveSouth")) {
			if(touchWallSouth) {

			}
			else {
				ObjectInstance tmpInst;
				ObjectAttribute tmpAtt;
				tmpInst = state1.objects.get(taxiId);
				for (ObjectAttribute att : tmpInst.attributesVal.keySet()) {
					if(att.name.equals("yLocation")) {
						tmpAtt = (ObjectAttribute) att;
						tmpInst.attributesVal.put(tmpAtt, --yTaxi);
						state1.objects.put(tmpInst.getId(), tmpInst);

					}
				}

			}
		}

		if(action.name.equals("taxiMoveWest")) {
			if(touchWallWest) {

			}
			else {
				ObjectInstance tmpInst;
				ObjectAttribute tmpAtt;
				tmpInst = state1.objects.get(taxiId);
				for (ObjectAttribute att : tmpInst.attributesVal.keySet()) {
					if(att.name.equals("xLocation")) {
						tmpAtt = (ObjectAttribute) att;
						tmpInst.attributesVal.put(tmpAtt, --xTaxi);
						state1.objects.put(tmpInst.getId(), tmpInst);
					}
				}
			}
		}

		if(action.name.equals("pickupPassenger")) {
			if(passengerIn==true || xTaxi!=xPassenger|| yTaxi!=yPassenger) {}
			else {		
				ObjectInstance tmpInst;
				ObjectAttribute tmpAtt;

				tmpInst = state1.objects.get(passengerId);
				for (ObjectAttribute att : tmpInst.attributesVal.keySet()) {
					if(att.name.equals("xLocation")) {
						tmpAtt = (ObjectAttribute) att;
						tmpInst.attributesVal.put(tmpAtt, true);
						state1.objects.put(tmpInst.getId(), tmpInst);
					}
				}


				tmpInst = state1.objects.get(taxiId);
				for (ObjectAttribute att : tmpInst.attributesVal.keySet()) {
					if(att.name.equals("xLocation")) {
						tmpAtt = (ObjectAttribute) att;
						tmpInst.attributesVal.put(tmpAtt, true);
						state1.objects.put(tmpInst.getId(), tmpInst);
					}
				}



			}
		}

		if(action.name.equals("dropOffPassenger")) {
			if(passengerIn=false || xTaxi!=xDestino || yTaxi!=yDestino) {}
			else {
				ObjectInstance tmpInst;
				ObjectAttribute tmpAtt;

				tmpInst = state1.objects.get(passengerId);
				for (ObjectAttribute att : tmpInst.attributesVal.keySet()) {
					if(att.name.equals("xLocation")) {
						tmpAtt = (ObjectAttribute) att;
						tmpInst.attributesVal.put(tmpAtt, false);
						state1.objects.put(tmpInst.getId(), tmpInst);
					}
				}


				tmpInst = state1.objects.get(taxiId);
				for (ObjectAttribute att : tmpInst.attributesVal.keySet()) {
					if(att.name.equals("xLocation")) {
						tmpAtt = (ObjectAttribute) att;
						tmpInst.attributesVal.put(tmpAtt, false);
						state1.objects.put(tmpInst.getId(), tmpInst);
					}
				}

			}
		}

		return state1;
	}

}
