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
		OOMDPReaderFromFile a = new OOMDPReaderFromFile();
		List<PropositionalFunction> pfs = new ArrayList<PropositionalFunction>();
		pfs.add(new PassengerInTaxi());
		pfs.add(new TaxiAtPassenger());
		pfs.add(new WallToEastOfTaxi());
		pfs.add(new WallToNorthOfTaxi());
		pfs.add(new WallToSouthOfTaxi());
		pfs.add(new WallToWestOfTaxi());

		PropositionalFunction[] pfss = new PropositionalFunction[pfs.size()];
		for (int i = 0; i < pfs.size(); i++)
			pfss[i] = pfs.get(i);
//		RandomGameGenerator rgg = new RandomGameGenerator("aa.txt");
//		rgg.gerar();
		OOMDP oomdp = a.leitura(pfss);
		OOMDPState initialState = a.stateReader(oomdp);
		TaxiEnvironment evs = new TaxiEnvironment("Taxi");
		
		List<EffectType> effectsToUse = new ArrayList<>();
		for(EffectType e : Effect.γ) {
			effectsToUse.add(e);
		}
		
		Doormax doormax = new Doormax(oomdp, pfs, effectsToUse,initialState,
				100, null);

		OOMDPState curState = initialState;
		int steps = 0;
		while (steps < 10) {

			Action ga = oomdp.actions.get(0);
			OOMDPState nextState = evs.simulateAction(curState, ga);
			double r = -1;

			if (!doormax.transitionIsModeled(curState, ga)) {
				doormax.updateModel(curState, ga, nextState, r, false);
				if (doormax.transitionIsModeled(curState, ga)) {
					// doormax.modelPlanner.modelChanged(curState);
					// policy = new DomainMappedPolicy(domain,
					// this.modelPlanner.modelPlannedPolicy());
					// policy = this.createDomainMappedPolicy();
				}
			}
			
			curState = nextState;

			steps++;
			break;
		}
		System.out.println("GO Horse");
	}

}
