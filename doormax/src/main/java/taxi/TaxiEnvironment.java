package taxi;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
		String goalId= "";
		List<String> horizontalIds = new ArrayList<String>();
		List<String>  verticalIds = new ArrayList<String>();
		
//		
		Collection<ObjectInstance> collection = state0.objects.values();
		
		
		for (ObjectInstance objTemp : collection){	
			ObjectClass obj = objTemp.objectClass;
			Object temp;
			if(objTemp.getObjectClass().name.equals("taxi")) {
				
				taxiId = objTemp.getId();
				
				temp = objTemp.attributesVal.get(obj.attributes.get(0));
				xTaxi = (Double)temp;//.toString();
				
				temp = objTemp.attributesVal.get(obj.attributes.get(1));
				yTaxi = (Double)temp;//.toString();
				
				temp = objTemp.attributesVal.get(obj.attributes.get(2));
				passengerIn = Boolean.parseBoolean(temp.toString());	
				
				
			}
			
			
			if(objTemp.getObjectClass().name.equals("passenger")) {
				passengerId = objTemp.getId();
				
				obj = objTemp.objectClass;
				temp = objTemp.attributesVal.get(obj.attributes.get(0));
				xPassenger = (Double)temp;
				
				temp = objTemp.attributesVal.get(obj.attributes.get(1));
				yPassenger = (Double)temp;
				
			
			}
			
			if(objTemp.getObjectClass().name.equals("goalLocation")) {
				goalId = objTemp.getId();
				obj = objTemp.objectClass;
				temp = objTemp.attributesVal.get(obj.attributes.get(0));
				xDestino = (Double)temp;
				
				temp = objTemp.attributesVal.get(obj.attributes.get(1));
				yDestino = (Double)temp;
			}
			
			if(objTemp.getObjectClass().name.equals("horizontalWall")) {
				horizontalIds.add(objTemp.getId());
				
			}
			
			if(objTemp.getObjectClass().name.equals("verticalWall")) {
				verticalIds.add(objTemp.getId());
				
			}
			
   		}
		
		Double offset;
		Double wallBegin;
		Double wallEnd;
		
		for (String str : horizontalIds){
			ObjectInstance wall = state0.objects.get(str);
			offset = (Double)wall.attributesVal.get(wall.objectClass.attributes.get(0));
			
			if(yTaxi == offset+1) {
				wallBegin =(Double)wall.attributesVal.get(wall.objectClass.attributes.get(1));
				wallEnd = (Double)wall.attributesVal.get(wall.objectClass.attributes.get(2));
				if(xTaxi > wallBegin && xTaxi<wallEnd) {
					touchWallWest=true;
				}	
			}
			
			if(yTaxi == offset) {
				wallBegin =(Double)wall.attributesVal.get(wall.objectClass.attributes.get(1));
				wallEnd = (Double)wall.attributesVal.get(wall.objectClass.attributes.get(2));
				if(xTaxi > wallBegin && xTaxi<wallEnd) {
					touchWallEast=true;
				}	
			}
			
		}
		
		for (String str : verticalIds){
			ObjectInstance wall = state0.objects.get(str);
			offset = (Double)wall.attributesVal.get(wall.objectClass.attributes.get(0));
			
			if(xTaxi == offset+1) {
				wallBegin =(Double)wall.attributesVal.get(wall.objectClass.attributes.get(1));
				wallEnd = (Double)wall.attributesVal.get(wall.objectClass.attributes.get(2));
				if(yTaxi > wallBegin && yTaxi<wallEnd) {
					touchWallSouth=true;
				}
			}
			
			if(xTaxi == offset) {
				wallBegin =(Double)wall.attributesVal.get(wall.objectClass.attributes.get(1));
				wallEnd = (Double)wall.attributesVal.get(wall.objectClass.attributes.get(2));
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
				tmpAtt = (ObjectAttribute) tmpInst.attributesVal.keySet().toArray()[1];
				tmpInst.attributesVal.put(tmpAtt, ++yTaxi);
				state1.objects.put(tmpInst.getId(), tmpInst);

			}
			
		}
		if(action.name.equals("taxiMoveEast")) {
			if(touchWallEast) {
				
			}
			else {
				ObjectInstance tmpInst;
				ObjectAttribute tmpAtt;
				tmpInst = state1.objects.get(taxiId); 
				tmpAtt = (ObjectAttribute) tmpInst.attributesVal.keySet().toArray()[0];
				tmpInst.attributesVal.put(tmpAtt, ++xTaxi);
				state1.objects.put(tmpInst.getId(), tmpInst);
			}		
		}
		if(action.name.equals("taxiMoveSouth")) {
			if(touchWallSouth) {
				
			}
			else {
				ObjectInstance tmpInst;
				ObjectAttribute tmpAtt;
				tmpInst = state1.objects.get(taxiId);
				tmpAtt = (ObjectAttribute) tmpInst.attributesVal.keySet().toArray()[1];
				tmpInst.attributesVal.put(tmpAtt, --yTaxi);
				state1.objects.put(tmpInst.getId(), tmpInst);

			}
		}
		
		if(action.name.equals("taxiMoveWest")) {
			if(touchWallWest) {
				
			}
			else {
				ObjectInstance tmpInst;
				ObjectAttribute tmpAtt;
				tmpInst = state1.objects.get(taxiId);
				tmpAtt = (ObjectAttribute) tmpInst.attributesVal.keySet().toArray()[0];
				tmpInst.attributesVal.put(tmpAtt, --xTaxi);
				state1.objects.put(tmpInst.getId(), tmpInst);
			}
		}
		
		if(action.name.equals("pickupPassenger")) {
			if(passengerIn==true || xTaxi!=xPassenger|| yTaxi!=yPassenger) {}
			else {		
				ObjectInstance tmpInst;
				ObjectAttribute tmpAtt;
				
				tmpInst = state1.objects.get(passengerId);
				tmpAtt = (ObjectAttribute) tmpInst.attributesVal.keySet().toArray()[1];
				tmpInst.attributesVal.put(tmpAtt, true);
				state1.objects.put(tmpInst.getId(), tmpInst);
				
				tmpInst = state1.objects.get(taxiId);
				tmpAtt = (ObjectAttribute) tmpInst.attributesVal.keySet().toArray()[2];
				tmpInst.attributesVal.put(tmpAtt, true);
				state1.objects.put(tmpInst.getId(), tmpInst);
				
				
				
			}
		}
		
		if(action.name.equals("dropOffPassenger")) {
			if(passengerIn=false || xTaxi!=xDestino || yTaxi!=yDestino) {}
			else {
				ObjectInstance tmpInst;
				ObjectAttribute tmpAtt;
				
				tmpInst = state1.objects.get(passengerId);
				tmpAtt = (ObjectAttribute) tmpInst.attributesVal.keySet().toArray()[1];
				tmpInst.attributesVal.put(tmpAtt, false);
				state1.objects.put(tmpInst.getId(), tmpInst);
				
				tmpInst = state1.objects.get(taxiId);
				tmpAtt = (ObjectAttribute) tmpInst.attributesVal.keySet().toArray()[2];
				tmpInst.attributesVal.put(tmpAtt, false);
				state1.objects.put(tmpInst.getId(), tmpInst);
				
			}
		}
		
		return state1;
	}

}
