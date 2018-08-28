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
import tomlg.doormax.output.Environment;
import tomlg.doormax.output.ReasoningMind;
import tomlg.doormax.utils.OOMDPReaderFromFile;
import tomlg.doormax.utils.RandomGameGenerator;
import tomlg.doormax.oomdpformalism.Action;

public class Experiment {
	private TaxiEnvironment evs;
	private Environment environment;
	private ReasoningMind agent;
	private OOMDP oomdp;
	private OOMDPState currentState;

	private int maxSteps;

	public Experiment(String oomdpFile, String environmentFile, String outputFile, int maxSteps) {
		this.maxSteps = maxSteps;
		this.createEnvironmentInstance(oomdpFile, environmentFile);

		this.agent = new ReasoningMind("Taxi", (Action[]) this.oomdp.actions.toArray(new Action[0]), this.environment,
				outputFile);
	}

	private void createEnvironmentInstance(String oomdpFile, String environmentFile) {
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
			this.oomdp = a.leitura(oomdpFile, pfss);
			this.currentState = a.stateReader(environmentFile, this.oomdp);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		// Uncomment For new game generation
		// RandomGameGenerator rgg = new RandomGameGenerator();
		// for (int j=0; j<1000; j++) {
		// while(!rgg.gerar("Jogo"+j+".txt"));
		// }

		List<EffectType> effectsToUse = new ArrayList<>();
		for (EffectType e : Effect.γ) {
			effectsToUse.add(e);
		}

		this.evs = new TaxiEnvironment("Taxi");
		this.environment = new Environment(this.currentState, this.evs);
	}

	// 1 tick de simulação
	public void simulateExperiment() {
		System.out.println("Experiment Starting...");
		while (maxSteps >= 0) {
			this.agent.tick();
			maxSteps--;
		}
		this.agent.end();
		System.out.println("Experiment Ended...");
	}
}
