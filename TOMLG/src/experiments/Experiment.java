package experiments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.security.auth.login.Configuration;

import doormax.OOMDP;
import doormax.OOMDPState;
import doormax.PropositionalFunction;
import doormax.structures.Action;
import doormax.structures.Effect;
import doormax.structures.EffectType;
import experiments.utils.OOMDPReaderFromFile;
import taxi.Configurations;
import taxi.TaxiEnvironment;
import taxi.TaxiEnvironment2;
import taxi.pf.old.PassengerInTaxi;
import taxi.pf.old.TaxiAtPassenger;
import taxi.pf.old.WallToEastOfTaxi;
import taxi.pf.old.WallToNorthOfTaxi;
import taxi.pf.old.WallToSouthOfTaxi;
import taxi.pf.old.WallToWestOfTaxi;
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

		this.agent = new Agent("Taxi", (Action[]) oomdp.getActionsArray(), this.environment, this.oomdp);

	}

	/// TODO deixar esse código descente
	private void createEnvironmentInstance(String oomdpFile, String stateFile) {
		List<PropositionalFunction> pfs = new ArrayList<PropositionalFunction>();
		pfs.add(Configurations.WALL_NORHT_PF);
		pfs.add(Configurations.WALL_SOUTH_PF);
		pfs.add(Configurations.WALL_WEST_PF);
		pfs.add(Configurations.WALL_EAST_PF);
		pfs.add(Configurations.TAXI_ON_PASSENGER_PF);
		pfs.add(Configurations.PASSENGER_IN_TAXI_PF);
		pfs.add(Configurations.TAXI_ON_DESTINATION_PF);

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

		this.environment = new Environment(this.currentState, new TaxiEnvironment2());
	}

	private void runExperiment() {
		System.out.println(this.environment.getState());		
		int step = 0;
		boolean episodeEnded = false;
		while (step <= this.maxSteps && !episodeEnded) {
			System.out.println("On step: " + step++);
			this.agent.step(episodeEnded);
			System.out.println(this.environment.getState());
			episodeEnded = ((TaxiEnvironment2)this.environment.getEnvironmnetSimulator()).isEpisodeEnded();
			System.out.println(this.agent.getMind());
		}
		this.agent.step(episodeEnded);
		System.out.println(this.agent.getMind());
	}

	public static void main(String args[]) {
		if (true) {
			args = new String[4];
			args[0] = "src/Environment01.oomdp";
			args[1] = "src/Environment01.state";
			args[2] = "experiment01.xml";
			args[3] = "100000";
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
