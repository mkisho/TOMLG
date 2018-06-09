package tomlg.doormax.perceptual.datasctructure;

import java.util.ArrayList;
import java.util.List;

import tomlg.doormax.Condition;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectInstance;
import tomlg.doormax.perceptual.AttributeRelations.AttributeRelation;

/**
 * A relational state perception for a single state where every object instance attribute - object instance attribute pair is examined
 * @author Dhershkowitz
 *
 */
public class RelationalStatePerception extends StatePerception {
	private List<String> attributeNames;
	private List<Double> dataList;
	Boolean tag;

	public RelationalStatePerception(OOMDPState state, Boolean posInstance, List<AttributeRelation> attRelations) {
		this.tag = posInstance;
		this.attributeNames = new ArrayList<String>();
		List<Double> dataList = new ArrayList<Double>();
		for (ObjectInstance o : state.objects) {
			for (ObjectAttribute att : o.getObjectClass().attributes) {
				for (ObjectInstance oOther : state.objects) {
					for (ObjectAttribute attOther : oOther.getObjectClass().attributes) {
						double oValue = o.getAttributeValByName(att.name).getNumericValForAttribute();
						double otherOValue = oOther.getAttributeValByName(attOther.name).getNumericValForAttribute();
						for (AttributeRelation attRel : attRelations) {
							attributeNames.add(attRel.toString() + o.objectClass.name+att.name+oOther.objectClass.name+attOther.name+" NUMERIC\n");
							dataList.add(attRel.getRelationValue(oValue, otherOValue));
						}


					}
				}
			}
		}
		this.dataList = dataList;

	}


	@Override
	public String getArffValueString(boolean labeled) {
		StringBuilder sb = new StringBuilder();
		double [] data = null;//this.getCondition();
		String prefix = "";
		for (int i = 0; i < data.length; i++) {
			sb.append(prefix + data[i]);
			prefix = ",";
		} 
		sb.append(",");
		if (tag != null) {
			sb.append(tag);
		}
		else {
			sb.append("?");
		}

		sb.append("\n");
		return sb.toString();
	}

	@Override
	public List<String> getattributeNames() {
		return this.attributeNames;
	}

/*	@Override
	public double[] getCondition() {
		double [] toReturn = new double[this.dataList.size()];
		for (int i = 0; i < this.dataList.size(); i ++) {
			toReturn[i] = this.dataList.get(i);
		}
		return toReturn;
	}
*/
	public Condition getCondition() {
		return null;
	}
}
