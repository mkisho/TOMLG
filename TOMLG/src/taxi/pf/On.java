package taxi.pf;

import doormax.OOMDPState;
import doormax.ObjectInstance;
import doormax.PropositionalFunction;
import doormax.structures.attribute.AttributeBoolean;
import doormax.structures.attribute.AttributeInteger;
import taxi.Configurations;

public class On extends PropositionalFunction {
	private static final long serialVersionUID = 2680651379017500092L;
	private final String objClass;
	private final String x;

	public On(String objClass, String x) {
		super("On(" + objClass + ", " + x + ")");
		this.objClass = objClass;
		this.x = x;
	}

	@Override
	public boolean evaluate(OOMDPState state) {
		ObjectInstance objIntance = state.getObjectOfClass(this.objClass);// state.getObjectOfClass(Configurations.TAXI_CLASS_NAME);
		if (objIntance == null)
			throw new RuntimeException("On with wrong class: " + this.objClass);

		int taxiX = ((AttributeInteger) objIntance.getAttributeValByName(Configurations.TAXI_ATT_X)).getValue();
		int taxiY = ((AttributeInteger) objIntance.getAttributeValByName(Configurations.TAXI_ATT_Y)).getValue();

		ObjectInstance objIntanceCheck = state.getObjectOfClass(this.x);// state.getObjectOfClass(Configurations.TAXI_CLASS_NAME);
		if (objIntanceCheck == null)
			throw new RuntimeException("On with wrong class: " + this.objClass + " " + x);

		int checkX = ((AttributeInteger) objIntanceCheck.getAttributeValByName(Configurations.TAXI_ATT_X)).getValue();
		int checkY = ((AttributeInteger) objIntanceCheck.getAttributeValByName(Configurations.TAXI_ATT_Y)).getValue();
		
		if(this.objClass.equals(Configurations.PASSENGER_CLASS_NAME)) {
			boolean checkIn = ((AttributeBoolean) objIntanceCheck
					.getAttributeValByName(Configurations.PASSENGER_ATT_IN_TAXI)).isValue();
			return taxiX == checkX && taxiY == checkY && !checkIn;
		}else return taxiX == checkX && taxiY == checkY; // FIX gambi

	}

}
