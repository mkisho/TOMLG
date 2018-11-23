package taxi;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import doormax.OOMDP;
import doormax.OOMDPState;
import doormax.PropositionalFunction;
import doormax.structures.Action;
import doormax.structures.Effect;
import doormax.structures.EffectType;
import doormax.utils.Util;
import experiments.utils.OOMDPReaderFromFile;
import taxi.experiments.Experiment01;
import tomlg.Agent;
import tomlg.Environment;
import tomlg.tom.Observation;

public class Experiment02DataGeneration {
	private int maxSteps;
	private OOMDP oomdp;
	private OOMDPState currentState;
	private Environment environment;
	private Agent agent;
	private final String observationsSaveFile;
	private final String stateFile;
	private final String mindLoadFile;

	public Experiment02DataGeneration(String oomdpFile, String stateFile, String outputFile, int maxSteps, String observationsSaveFile,
			String mindLoadFile) {
		this.maxSteps = maxSteps;
		this.createEnvironmentInstance(oomdpFile, stateFile);
		// cria OOMDP e OOMDPState inicial
		this.observationsSaveFile = observationsSaveFile;
		this.stateFile = stateFile;
		this.mindLoadFile = mindLoadFile;

		this.agent = new Agent("Taxi", this.environment, this.oomdp);

		if (mindLoadFile != null) {
			System.out.println("Loading Mind of Agent From file: " + mindLoadFile);
			this.agent.loadMindFromFile(mindLoadFile);
		}
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
			
			this.currentState = Util.readOOMDPStateFromFile(stateFile, oomdp);//a.stateReader(stateFile, this.oomdp);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		System.out.println("Current State has "+this.currentState.getObjects().size()+" objects");

		List<EffectType> effectsToUse = new ArrayList<>();
		for (EffectType e : Effect.γ) {
			effectsToUse.add(e);
		}

		this.environment = new Environment(this.currentState, new TaxiEnvironment2());
	}

	private void runExperiment() {
		List<Observation> observations = new ArrayList<Observation>();
		System.out.println(this.environment.getState());
		int step = 0;
		boolean episodeEnded = false;
		
		observations.add(new Observation(null, this.environment.getState()));
		
		while (step <= this.maxSteps && !episodeEnded) {
			System.out.println("On step: " + step++);
			this.agent.step(episodeEnded);
			System.out.println(this.environment.getState());
			episodeEnded = ((TaxiEnvironment2) this.environment.getEnvironmnetSimulator()).isEpisodeEnded();
//			System.out.println(this.agent.getMind());
			
			Action action = this.agent.getLastAction();
			OOMDPState state = this.environment.getState();
			observations.add(new Observation(action, state));
		}
		this.agent.step(episodeEnded);
//		Action action = this.agent.getLastAction();
//		OOMDPState state = this.environment.getState();
//		observations.add(new Observation(action, state));

		
		if (this.observationsSaveFile != null) {
			try {
				FileOutputStream fileOutputStream = new FileOutputStream(this.observationsSaveFile);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(observations);
				objectOutputStream.flush();
				objectOutputStream.close();
			} catch (Exception e) {
				System.out.println("Error saving observations file");
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		if (false) {
			args = new String[7];
			args[0] = "src/Environment01.oomdp";
			args[1] = "../TOMLG-EXPERIMENTS/Cenarios/exp01/exp01-cenario-10x10-001.state";
			args[2] = "experiment01.xml";
			args[3] = "100000";
			args[4] = "../TOMLG-EXPERIMENTS/Cenarios/exp02/observation.obs";//"NULL_SAVE";
		}
		System.out.println(Arrays.toString(args));
		if (args.length < 4) {
			System.out.println(
					"The params must be: oodmp_file, state_file, output_file, max_steps, statiscs_file, mind_save_file,"
							+ "mind_load_file");
			System.exit(-1);
		}
		String oomdpFile = args[0];
		String stateFile = args[1];
		String outputFile = args[2];
		int maxSteps = Integer.parseInt(args[3]);
		String observationsSave = (args.length > 4 ? args[4] : null);
		String mindLoadFile = "../TOMLG-EXPERIMENTS/trainned_minds/exp02.data.gen.mind";

		System.out.println("Observations Save File "+observationsSave);
		
		Experiment02DataGeneration experiment = new Experiment02DataGeneration(oomdpFile, stateFile, outputFile, maxSteps, observationsSave, mindLoadFile);
		experiment.runExperiment();
	}
}
