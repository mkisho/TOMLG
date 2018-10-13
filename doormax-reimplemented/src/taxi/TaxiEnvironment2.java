package taxi;

import doormax.OOMDPState;
import doormax.ObjectInstance;
import doormax.structures.Action;
import doormax.structures.Condition;
import doormax.structures.attribute.*;

public class TaxiEnvironment2 extends tomlg.EnvironmentSimulator {

	public TaxiEnvironment2() {
		super("TaxiSimulator2");
		// TODO Auto-generated constructor stub
	}

	@Override
	public OOMDPState simulateAction(OOMDPState state0, Action action) {
		OOMDPState resultState = state0.copy();

		Condition cond = Condition.evaluate(state0.getOomdp().getPfIndex(), resultState);
		switch (action.getName()) {
		case Configurations.NORTH:
			if (!cond.getEvalOfPropositionalFunction(Configurations.WALL_NORHT_PF)) {
				ObjectInstance taxi = resultState.getObjectOfClass(Configurations.TAXI_CLASS_NAME);
				AttributeInteger att = (AttributeInteger) taxi.getAttributeValByName(Configurations.TAXI_ATT_Y);
				att.setValue(att.getValue() - 1);
			} else {
				System.out.println("Parede em frente, não pode mover");
			}
			break;
		case Configurations.SOUTH:
			if (!cond.getEvalOfPropositionalFunction(Configurations.WALL_SOUTH_PF)) {
				ObjectInstance taxi = resultState.getObjectOfClass(Configurations.TAXI_CLASS_NAME);
				AttributeInteger att = (AttributeInteger) taxi.getAttributeValByName(Configurations.TAXI_ATT_Y);
				att.setValue(att.getValue() + 1);
			} else {
				System.out.println("Parede em frente, não pode mover");
			}
			break;
		case Configurations.EAST:
			if (!cond.getEvalOfPropositionalFunction(Configurations.WALL_EAST_PF)) {
				ObjectInstance taxi = resultState.getObjectOfClass(Configurations.TAXI_CLASS_NAME);
				AttributeInteger att = (AttributeInteger) taxi.getAttributeValByName(Configurations.TAXI_ATT_X);
				att.setValue(att.getValue() - 1);
			} else {
				System.out.println("Parede em frente, não pode mover");
			}
			break;
		case Configurations.WEST:
			if (!cond.getEvalOfPropositionalFunction(Configurations.WALL_WEST_PF)) {
				ObjectInstance taxi = resultState.getObjectOfClass(Configurations.TAXI_CLASS_NAME);
				AttributeInteger att = (AttributeInteger) taxi.getAttributeValByName(Configurations.TAXI_ATT_X);
				att.setValue(att.getValue() + 1);
			} else {
				System.out.println("Parede em frente, não pode mover");
			}
			break;
		case Configurations.DROP:
			if (cond.getEvalOfPropositionalFunction(Configurations.PASSENGER_IN_TAXI_PF)) {
				// TODO FIX
			} else {
				System.out.println("Não deu DROP");
			}
			break;
		case Configurations.PICK:
			if (!cond.getEvalOfPropositionalFunction(Configurations.TAXI_AT_PASSENGER_PF) &&
					cond.getEvalOfPropositionalFunction(Configurations.PASSENGER_IN_TAXI_PF)) {

			} else {
				System.out.println("Não deu PICK");
			}
			
			break;
		}

		return resultState;
	}

}
