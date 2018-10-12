package taxi.pf;

import doormax.OOMDPState;
import doormax.ObjectInstance;
import doormax.PropositionalFunction;
import doormax.structures.attribute.AttributeBoolean;
import doormax.structures.attribute.AttributeInteger;
import taxi.Configurations;

public class In extends PropositionalFunction {
	private final String objClass;
	private final String x;

	public In(String objClass, String x) {
		super("In(" + objClass + ", " + x + ")");
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
		
		boolean passengerInTaxi = ((AttributeBoolean) objIntance.getAttributeValByName(Configurations.TAXI_PASSENGER_INSIDE)).isValue();
		
		return taxiX == checkX && taxiY == checkY && passengerInTaxi;
	}
}
