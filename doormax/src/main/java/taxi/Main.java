package taxi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import taxi.pf.PassengerInTaxi;
import taxi.pf.TaxiAtPassenger;
import taxi.pf.WallToEastOfTaxi;
import taxi.pf.WallToNorthOfTaxi;
import taxi.pf.WallToSouthOfTaxi;
import taxi.pf.WallToWestOfTaxi;
import tomlg.doormax.Doormax;
import tomlg.doormax.Effect;
import tomlg.doormax.PropositionalFunction;
import tomlg.doormax.effects.EffectType;
import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.OOMDP;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.utils.OOMDPReaderFromFile;
import tomlg.doormax.utils.RandomGameGenerator;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Experiment experiment = new Experiment(null, null, 10);
		experiment.simulateStep();
		experiment.simulateStep();
		experiment.simulateStep();
		experiment.simulateStep();
		experiment.simulateStep();
		experiment.simulateStep();
	}

}
