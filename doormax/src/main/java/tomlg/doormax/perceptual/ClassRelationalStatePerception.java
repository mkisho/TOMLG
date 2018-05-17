package tomlg.doormax.perceptual;

import java.util.ArrayList;
import java.util.List;

import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectInstance;
import tomlg.doormax.perceptual.AttributeRelations.AttributeRelation;

public class ClassRelationalStatePerception extends StatePerception {
	private List<String> attributeNames;
	private List<Double> dataList;
	Boolean tag;

	public ClassRelationalStatePerception(OOMDPState state, Boolean posInstance, List<AttributeRelation> attRelations) {
		this.tag = posInstance;
		this.attributeNames = new ArrayList<String>();
		List<Double> dataList = new ArrayList<Double>();
		for (ObjectInstance objInstance: state.objects.values()) {
			for (ObjectAttribute att : objInstance.getAttributesVal().keySet()) {
				for (AttributeRelation attRel : attRelations) {
					for (ObjectInstance objectIntance2 : state.objects.values()) {
						if (!objInstance.getObjectClass().equals(objectIntance2.getObjectClass())) {
							for (ObjectAttribute attOther : objectIntance2.getAttributesVal().keySet()){
								double attRelationVal = Integer.MAX_VALUE;

								// Loop over pairs of each object instance
								for (ObjectInstance o : state.objects.values()) {
									for (ObjectInstance oOther : state.objects.values()) {

										if (!oOther.equals(o) || !attOther.equals(att)) {
											double oValue = (Double)o.getAttributesVal().get(att);
											double otherOValue = (Double)oOther.getAttributesVal().get(attOther);
											attRelationVal = Math.min(attRelationVal,
													attRel.getRelationValue(otherOValue, oValue));
										}
									}

								}
								attributeNames.add(attRel.toString() + objInstance + att.name + objectIntance2
										+ attOther.name + " NUMERIC\n");

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
		double[] data = this.getData();
		String prefix = "";
		for (int i = 0; i < data.length; i++) {
			sb.append(prefix + data[i]);
			prefix = ",";
		}
		sb.append(",");
		if (tag != null) {
			sb.append(tag);
		} else {
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
		double[] toReturn = new double[this.dataList.size()];
		for (int i = 0; i < this.dataList.size(); i++) {
			toReturn[i] = this.dataList.get(i);
		}
		return toReturn;
	}

}
