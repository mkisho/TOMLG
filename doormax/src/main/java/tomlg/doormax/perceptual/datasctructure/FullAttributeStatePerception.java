package tomlg.doormax.perceptual.datasctructure;

import java.util.ArrayList;
import java.util.List;

import tomlg.doormax.Condition;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectInstance;

/**
 * TODO ERRADO 
 * @author chronius
 *
 */
public class FullAttributeStatePerception extends StatePerception {
	private double[] data;
	private Boolean tag;
	private List<String> attributeNames;
	
	public FullAttributeStatePerception(OOMDPState state, Boolean posInstance) {
		this.tag = posInstance;
		this.attributeNames = new ArrayList<String>();
		//Get allValues
/**		Set<ObjectInstance> allObjects = state.objects;
		List<Value> allValues = new ArrayList<Value>();
		for (ObjectInstance o : allObjects) {
			for (ObjectAttribute att : o.getObjectClass().attributes) {
				this.attributeNames.add(o.getName()+att.name+ " NUMERIC\n");
				allValues.add(o.getValueForAttribute(att.name));
			}
		}
		
		//Set data to all values
		double[] data = new double[allValues.size()];
		
		int index = 0;
		for (Value val: allValues) {
			data[index] = val.getNumericRepresentation();
			index++;
		}
	*/	
		this.data = data;
	}
	
	@Override
	public String getArffValueString(boolean labeled) {
		StringBuffer sb = new StringBuffer();
		String prefix = "";
		for (int i = 0; i < data.length; i++) {
			sb.append(prefix + Double.toString(this.data[i]));
			prefix = ",";
		} 
		sb.append(",");
		if (labeled) {
			sb.append(this.tag);
		}
		else {
			sb.append("?");
		}

		sb.append("\n");
		String toReturn = sb.toString();
		
		return toReturn;
	}

	@Override
	public Condition getCondition() {
		return null;
		//return this.data;
	}

	@Override
	public List<String> getattributeNames() {
		return this.attributeNames;
	}
	

}
