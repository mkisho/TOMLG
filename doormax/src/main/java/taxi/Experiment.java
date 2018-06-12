package taxi;

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
import tomlg.doormax.oomdpformalism.OOMDP;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.output.ReasoningMind;
import tomlg.doormax.utils.OOMDPReaderFromFile;
import tomlg.doormax.oomdpformalism.Action;

public class Experiment {
	private Doormax doormaxInstance;
	private TaxiEnvironment evs;
	private ReasoningMind agent;
	private OOMDP oomdp; 
	private OOMDPState currentState;
	
	private int maxSteps;
	private int steps;
	
	public Experiment(String oomdpFile, String environmentFile, int maxSteps){
		this.maxSteps = maxSteps;
		this.createEnvironmentInstance(oomdpFile, environmentFile);
		
		this.agent = new ReasoningMind("Taxi", 
				(Action [])this.oomdp.actions.toArray(new Action [0]));
	}
	
	
	private void  createEnvironmentInstance(String oomdpFile, String environmentFile) {
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
		
		try {
			OOMDPReaderFromFile a = new OOMDPReaderFromFile();
			this.oomdp = a.leitura(pfss);
			this.currentState = a.stateReader(this.oomdp);
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		List<EffectType> effectsToUse = new ArrayList<>();
		for(EffectType e : Effect.γ) {
			effectsToUse.add(e);
		}
		
		this.doormaxInstance = new Doormax(this.oomdp, pfs, effectsToUse, 
				this.currentState,
				100, null);
		this.evs = new TaxiEnvironment("Taxi");
		this.steps = 0;
	}
	
	// 1 tick de simulação
	public void simulateStep() {
		this.agent.perceive();//this.currentState);
		Action action  = this.agent.reasoning();
		OOMDPState nextState = evs.simulateAction(this.currentState, action);

		if (!this.doormaxInstance.transitionIsModeled(this.currentState, action)) {
			this.doormaxInstance.updateModel(this.currentState, action, 
					nextState, -1, false);
			if (this.doormaxInstance.transitionIsModeled(this.currentState, 
					action)) {
				// doormax.modelPlanner.modelChanged(curState);
				// policy = new DomainMappedPolicy(domain,
				// this.modelPlanner.modelPlannedPolicy());
				// policy = this.createDomainMappedPolicy();
			}
		}
		this.currentState  = nextState;
	}

}
