package tomlg.doormax.perceptual.datasctructure;

import java.util.List;

import tomlg.doormax.Condition;

public abstract class StatePerception {
	public static String FullAttributeStatePerception = "fullAttStatePerc";
	public static String PFStatePerception = "pfStatePerc";
	public static String RelationalStatePerception = "relatStatePerc";
	public static String ClassRelationalStatePerception = "classRelateStatePerc";

	public String getFullArffString(boolean labeled) {
		StringBuffer sb = new StringBuffer();
		// Set up relation
		sb.append("@RELATION " + "junkFileName" + "\n");

		StatePerception samplePerception = this;

		List<String> attributeList = samplePerception.getattributeNames();
		// Set up arff attributes -- attributes in OOMDP state
		for (String attributeName : attributeList) {
			String attributeLine = "@ATTRIBUTE " + attributeName;
			sb.append(attributeLine);
		}
		// Set up attributes -- label of data
		sb.append("@ATTRIBUTE class {true,false}\n");

		// Set up data
		sb.append("@DATA\n");
		String currArffString = this.getArffValueString(labeled);
		sb.append(currArffString);

		return sb.toString();
	}

	public abstract String getArffValueString(boolean labeled);

	public abstract List<String> getattributeNames();

	public abstract Condition getCondition();

	@Override
	public String toString() {
		return this.getArffValueString(true);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof FullAttributeStatePerception) {
			return this.getCondition().equals(((FullAttributeStatePerception) other).getCondition());
		} else
			return false;
	}
}
