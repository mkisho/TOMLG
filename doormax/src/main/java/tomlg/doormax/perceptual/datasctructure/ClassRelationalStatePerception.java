package tomlg.doormax.perceptual.datasctructure;

import java.util.ArrayList;
import java.util.List;

import tomlg.doormax.Condition;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectClass;
import tomlg.doormax.oomdpformalism.ObjectInstance;
import tomlg.doormax.perceptual.AttributeRelations.AttributeRelation;

/**
 * A relational state perception for a single state where every object instance attribute - object instance attribute pair is examined
 * @author Dhershkowitz
 *
 */
public class ClassRelationalStatePerception extends StatePerception {
	private List<String> attributeNames;
	private List<Double> dataList;
	Boolean tag;

	public ClassRelationalStatePerception(OOMDPState state, Boolean posInstance, List<AttributeRelation> attRelations) {
		this.tag = posInstance;
		this.attributeNames = new ArrayList<String>();
		List<Double> dataList = new ArrayList<Double>();
		for (ObjectClass oClass : state.oomdp.objectClasses) {
			for (ObjectAttribute att : state.getObjectsOfClass(oClass.name).get(0).getObjectClass().attributes) {
				for (AttributeRelation attRel : attRelations) {
					for (ObjectClass o2Class : state.oomdp.objectClasses) {
						if (!oClass.equals(o2Class)) {
							for (ObjectAttribute attOther : state.getObjectsOfClass(o2Class.name).get(0).getObjectClass().attributes) {
								double attRelationVal = Integer.MAX_VALUE;

								//Loop over pairs of each object instance
								for (ObjectInstance o : state.getObjectsOfClass(oClass.name)) {
									for (ObjectInstance oOther : state.getObjectsOfClass(o2Class.name)) {


										if (!oOther.equals(o) || !attOther.equals(att)) {
							//				double oValue = o.getNumericValForAttribute(att.name);
							//				double otherOValue = oOther.getNumericValForAttribute(attOther.name);
							//				attRelationVal = Math.min(attRelationVal, attRel.getRelationValue(otherOValue, oValue));
										}
									}


								}
								attributeNames.add(attRel.toString() + oClass+att.name+o2Class+attOther.name+" NUMERIC\n");

								dataList.add(attRelationVal);

							}
						}
					}
				}
			}
			this.dataList = dataList;
		}
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

	/*@Override
	public double[] getCondition() {
		double [] toReturn = new double[this.dataList.size()];
		for (int i = 0; i < this.dataList.size(); i ++) {
			toReturn[i] = this.dataList.get(i);
		}
		return toReturn;
	}*/
	public Condition getCondition() {
		return null;
	}

}
