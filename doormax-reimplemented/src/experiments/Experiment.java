package experiments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import doormax.OOMDP;
import doormax.OOMDPState;
import doormax.PropositionalFunction;
import doormax.structures.Action;
import doormax.structures.Effect;
import doormax.structures.EffectType;
import experiments.utils.OOMDPReaderFromFile;
import taxi.TaxiEnvironment;
import taxi.pf.PassengerInTaxi;
import taxi.pf.TaxiAtPassenger;
import taxi.pf.WallToEastOfTaxi;
import taxi.pf.WallToNorthOfTaxi;
import taxi.pf.WallToSouthOfTaxi;
import taxi.pf.WallToWestOfTaxi;
import tomlg.Agent;
import tomlg.Environment;
import tomlg.EnvironmentSimulator;

/**
 * Experiment 01
 * 
 * @author chronius
 *
 */
public class Experiment {
	private int maxSteps;
	private OOMDP oomdp;
	private OOMDPState currentState;
	private Environment environment;

	private Agent agent;

	public Experiment(String oomdpFile, String stateFile, String outputFile, int maxSteps) {
		this.maxSteps = maxSteps;
		this.createEnvironmentInstance(oomdpFile, stateFile);
		// cria OOMDP e OOMDPState inicial

		this.agent = new Agent("Taxi", (Action[]) oomdp.actions.toArray(), this.environment);

	}

	/// TODO deixar esse código descente
	private void createEnvironmentInstance(String oomdpFile, String stateFile) {
		List<PropositionalFunction> pfs = new ArrayList<PropositionalFunction>();
		pfs.add(new PassengerInTaxi());
		pfs.add(new TaxiAtPassenger());
		pfs.add(new WallToEastOfTaxi());
		pfs.add(new WallToNorthOfTaxi());
		pfs.add(new WallToSouthOfTaxi());
		pfs.add(new WallToWestOfTaxi());

		PropositionalFunction[] pfss = new PropositionalFunction[pfs.size()];
		pfs.toArray(pfss);

		try {
			OOMDPReaderFromFile a = new OOMDPReaderFromFile();
			this.oomdp = a.leitura(oomdpFile, pfss);
			this.currentState = a.stateReader(stateFile, this.oomdp);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		List<EffectType> effectsToUse = new ArrayList<>();
		for (EffectType e : Effect.γ) {
			effectsToUse.add(e);
		}

		this.environment = new Environment(this.currentState, new TaxiEnvironment("Taxi"));
	}

	private void runExperiment() {
		int step = this.maxSteps;
		while(step-- > 0) {
			System.out.println("On step: " + step);
			this.agent.step();
		}
	}

	public static void main(String args[]) {
		if (true) {
			args = new String[4];
			args[0] = "src/Environment01.oomdp";
			args[1] = "src/Environment01.state";
			args[2] = "experiment01.xml";
			args[3] = "50";
		}
		System.out.println(Arrays.toString(args));
		if (args.length < 4) {
			System.out.println("The params must be: oodmp_file, state_file, output_file, max_steps");
			System.exit(-1);
		}
		String oomdpFile = args[0];
		String stateFile = args[1];
		String outputFile = args[2];
		int maxSteps = Integer.parseInt(args[3]);

		Experiment experiment = new Experiment(oomdpFile, stateFile, outputFile, maxSteps);
		experiment.runExperiment();
	}
}
