package taxi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import burlap.behavior.singleagent.Policy;
import burlap.oomdp.core.State;
import burlap.oomdp.singleagent.GroundedAction;
import taxi.pf.PassengerInTaxi;
import taxi.pf.TaxiAtPassenger;
import taxi.pf.WallToEastOfTaxi;
import taxi.pf.WallToNorthOfTaxi;
import taxi.pf.WallToSouthOfTaxi;
import taxi.pf.WallToWestOfTaxi;
import tomlg.doormax.Doormax;
import tomlg.doormax.PropositionalFunction;
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
		
		PropositionalFunction [] pfss = new PropositionalFunction[pfs.size()];
		for (int i=0; i< pfs.size(); i++)
			pfss[i] = pfs.get(i);
		
		RandomGameGenerator rgg =new RandomGameGenerator("aa.txt");
		while(!rgg.gerar());
		
		OOMDP oomdp = a.leitura(pfss);
		OOMDPState initialState = a.stateReader(oomdp);
		TaxiEnvironment evs = new TaxiEnvironment("Taxi");
		Doormax doormax = new Doormax(oomdp, initialState, 100, 0.1);
	
		OOMDPState curState = initialState;
		int steps = 0;
		while(!steps < 10){
			
			GroundedAction ga = (GroundedAction)policy.getAction(curState);
			State nextState = ga.executeIn(curState);
			double r = this.rf.reward(curState, ga, nextState);
			
			ea.recordTransitionTo(ga, nextState, r);
			
			if(!this.model.transitionIsModeled(curState, ga)){
				this.model.updateModel(curState, ga, nextState, r, this.tf.isTerminal(nextState));
				if(this.model.transitionIsModeled(curState, ga)){
					this.modelPlanner.modelChanged(curState);
					//policy = new DomainMappedPolicy(domain, this.modelPlanner.modelPlannedPolicy());
					policy = this.createDomainMappedPolicy();
				}
			}
			
			
			curState = nextState;
			
			steps++;
		}
	}
}
