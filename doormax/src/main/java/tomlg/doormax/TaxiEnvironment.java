package tomlg.doormax;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
		int xTaxi = 0;
		int yTaxi = 0;
		int xDestino = 0;
		int yDestino = 0;
		int xPassenger = 0;
		int yPassenger = 0;
		boolean passengerIn = false;
		
		OOMDPState state1 = state0;
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
				xTaxi = Integer.parseInt(temp.toString());
				
				temp = objTemp.attributesVal.get(obj.attributes.get(1));
				yTaxi = Integer.parseInt(temp.toString());
				
				temp = objTemp.attributesVal.get(obj.attributes.get(2));
				passengerIn = Boolean.parseBoolean(temp.toString());	
			}
			
			
			if(objTemp.getObjectClass().name.equals("passenger")) {
				passengerId = objTemp.getId();
				
				obj = objTemp.objectClass;
				temp = objTemp.attributesVal.get(obj.attributes.get(0));
				xPassenger = Integer.parseInt(temp.toString());
				
				temp = objTemp.attributesVal.get(obj.attributes.get(1));
				yPassenger = Integer.parseInt(temp.toString());
				
			
			}
			
			if(objTemp.getObjectClass().name.equals("goalLocation")) {
				goalId = objTemp.getId();
				obj = objTemp.objectClass;
				temp = objTemp.attributesVal.get(obj.attributes.get(0));
				xDestino = Integer.parseInt(temp.toString());
				
				temp = objTemp.attributesVal.get(obj.attributes.get(1));
				yDestino = Integer.parseInt(temp.toString());
			}
			
			if(objTemp.getObjectClass().name.equals("horizontalWall")) {
				horizontalIds.add(objTemp.getId());
				
			}
			
			if(objTemp.getObjectClass().name.equals("verticalWall")) {
				verticalIds.add(objTemp.getId());
				
			}
			
   		}
		
		int offset;
		int wallBegin;
		int wallEnd;
		
		for (String str : horizontalIds){
			ObjectInstance wall = state0.objects.get(str);
			offset = Integer.parseInt(wall.attributesVal.get(wall.objectClass.attributes.get(0)).toString());
			
			if(yTaxi == offset+1) {
				wallBegin =Integer.parseInt(wall.attributesVal.get(wall.objectClass.attributes.get(1)).toString());
				wallEnd = Integer.parseInt(wall.attributesVal.get(wall.objectClass.attributes.get(2)).toString());
				if(xTaxi > wallBegin && xTaxi<wallEnd) {
					touchWallWest=true;
				}	
			}
			
			if(yTaxi == offset) {
				wallBegin =Integer.parseInt(wall.attributesVal.get(wall.objectClass.attributes.get(1)).toString());
				wallEnd = Integer.parseInt(wall.attributesVal.get(wall.objectClass.attributes.get(2)).toString());
				if(xTaxi > wallBegin && xTaxi<wallEnd) {
					touchWallEast=true;
				}	
			}
			
		}
		
		for (String str : verticalIds){
			ObjectInstance wall = state0.objects.get(str);
			offset = Integer.parseInt(wall.attributesVal.get(wall.objectClass.attributes.get(0)).toString());
			
			if(xTaxi == offset+1) {
				wallBegin =Integer.parseInt(wall.attributesVal.get(wall.objectClass.attributes.get(1)).toString());
				wallEnd = Integer.parseInt(wall.attributesVal.get(wall.objectClass.attributes.get(2)).toString());
				if(yTaxi > wallBegin && yTaxi<wallEnd) {
					touchWallSouth=true;
				}
			}
			
			if(xTaxi == offset) {
				wallBegin =Integer.parseInt(wall.attributesVal.get(wall.objectClass.attributes.get(1)).toString());
				wallEnd = Integer.parseInt(wall.attributesVal.get(wall.objectClass.attributes.get(2)).toString());
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
