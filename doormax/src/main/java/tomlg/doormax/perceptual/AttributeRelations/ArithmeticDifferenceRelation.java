package tomlg.doormax.perceptual.AttributeRelations;


public class ArithmeticDifferenceRelation extends AttributeRelation {

	@Override
	public double getRelationValue(double oVal, double otherOVal) {
		return oVal-otherOVal;
	}

	@Override
	public String toString() {
		return "ArithmeticDifferenceRelation";
	}
}
