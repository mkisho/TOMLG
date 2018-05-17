package tomlg.doormax.perceptual;


import java.util.ArrayList;
import java.util.List;

import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectInstance;
import tomlg.doormax.perceptual.AttributeRelations.AttributeRelation;


public class RelationalStatePerception extends StatePerception {
	private List<String> attributeNames;
	private List<Double> dataList;
	Boolean tag;

	public RelationalStatePerception(OOMDPState state, Boolean posInstance, 
			List<AttributeRelation> attRelations) {
		this.tag = posInstance;
		this.attributeNames = new ArrayList<String>();
		List<Double> dataList = new ArrayList<Double>();
		for (ObjectInstance o : state.objects.values()) {
			for (ObjectAttribute att : o.getObjectClass().getAttributes()) {
				for (ObjectInstance oOther : state.objects.values()) {
					for (ObjectAttribute attOther : oOther.getObjectClass().getAttributes()) {
						double oValue = (double) o.attributesVal.get(att);
						double otherOValue = (double) oOther.attributesVal.get(att);
						for (AttributeRelation attRel : attRelations) {
							attributeNames.add(attRel.toString() + o.getId()+att.name+oOther.getId()+attOther.name+" NUMERIC\n");
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
		double [] data = this.getData();
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

	@Override
	public double[] getData() {
		double [] toReturn = new double[this.dataList.size()];
		for (int i = 0; i < this.dataList.size(); i ++) {
			toReturn[i] = this.dataList.get(i);
		}
		return toReturn;
	}

}
