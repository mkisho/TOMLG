package taxi;

import doormax.OOMDPState;
import doormax.ObjectInstance;
import doormax.structures.Action;
import doormax.structures.Condition;
import doormax.structures.attribute.*;

public class TaxiEnvironment2 extends tomlg.EnvironmentSimulator {
	private boolean episodeEnded;
	
	public boolean isEpisodeEnded() {
		return episodeEnded;
	}

	public TaxiEnvironment2() {
		super("TaxiSimulator2");
		episodeEnded = false;
	}

	@Override
	public OOMDPState simulateAction(OOMDPState state0, Action action) {
		OOMDPState resultState = state0.copy();
		
		
		ObjectInstance passenger = resultState.getObjectOfClass(Configurations.PASSENGER_CLASS_NAME);
		ObjectInstance taxi = resultState.getObjectOfClass(Configurations.TAXI_CLASS_NAME);

		Condition cond = Condition.evaluate(state0.getOomdp().getPfIndex(), resultState);
		switch (action.getName()) {
		case Configurations.NORTH:
			if (!cond.getEvalOfPropositionalFunction(Configurations.WALL_NORHT_PF)) {
				AttributeInteger att = (AttributeInteger) taxi.getAttributeValByName(Configurations.TAXI_ATT_Y);
				att.setValue(att.getValue() - 1);
			} else {
				System.out.println("Parede em frente, não pode mover");
			}
			break;
		case Configurations.SOUTH:
			if (!cond.getEvalOfPropositionalFunction(Configurations.WALL_SOUTH_PF)) {
				AttributeInteger att = (AttributeInteger) taxi.getAttributeValByName(Configurations.TAXI_ATT_Y);
				att.setValue(att.getValue() + 1);
			} else {
				System.out.println("Parede em frente, não pode mover");
			}
			break;
		case Configurations.EAST:
			if (!cond.getEvalOfPropositionalFunction(Configurations.WALL_EAST_PF)) {
				AttributeInteger att = (AttributeInteger) taxi.getAttributeValByName(Configurations.TAXI_ATT_X);
				att.setValue(att.getValue() - 1);
			} else {
				System.out.println("Parede em frente, não pode mover");
			}
			break;
		case Configurations.WEST:
			if (!cond.getEvalOfPropositionalFunction(Configurations.WALL_WEST_PF)) {
				AttributeInteger att = (AttributeInteger) taxi.getAttributeValByName(Configurations.TAXI_ATT_X);
				att.setValue(att.getValue() + 1);
			} else {
				System.out.println("Parede em frente, não pode mover");
			}
			break;
		case Configurations.DROP:
			if (cond.getEvalOfPropositionalFunction(Configurations.PASSENGER_IN_TAXI_PF)&&
					cond.getEvalOfPropositionalFunction(Configurations.TAXI_ON_DESTINATION_PF)) {
				
				AttributeBoolean att = (AttributeBoolean) taxi.getAttributeValByName(Configurations.TAXI_ATT_PASSENGER_INSIDE);
				att.setValue(false);
				
				((AttributeBoolean) passenger.getAttributeValByName(Configurations.PASSENGER_ATT_IN_TAXI)).setValue(false);
				
				this.episodeEnded = true;//TODO fix gambi
			} else {
				System.out.println("Não deu DROP");
			}
			break;
		case Configurations.PICK:
			if (cond.getEvalOfPropositionalFunction(Configurations.TAXI_ON_PASSENGER_PF) &&
					!cond.getEvalOfPropositionalFunction(Configurations.PASSENGER_IN_TAXI_PF)) {
				AttributeBoolean att = (AttributeBoolean) taxi.getAttributeValByName(Configurations.TAXI_ATT_PASSENGER_INSIDE);
				att.setValue(true);
				
				((AttributeBoolean) passenger.getAttributeValByName(Configurations.PASSENGER_ATT_IN_TAXI)).setValue(true);
			} else {
				System.out.println("Não deu PICK");
			}
			
			break;
		}
		
		if(((AttributeBoolean) passenger.getAttributeValByName(Configurations.PASSENGER_ATT_IN_TAXI)).isValue()) {
			
			int taxiX = ((AttributeInteger) taxi.getAttributeValByName(Configurations.TAXI_ATT_X)).getValue();
			int taxiY = ((AttributeInteger) taxi.getAttributeValByName(Configurations.TAXI_ATT_Y)).getValue();
			((AttributeInteger) passenger.getAttributeValByName(Configurations.PASSENGER_ATT_X)).setValue(taxiX);
			((AttributeInteger) passenger.getAttributeValByName(Configurations.PASSENGER_ATT_Y)).setValue(taxiY);
		}
		return resultState;
	}

}
