package taxi.experiments;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import doormax.OOMDP;
import doormax.OOMDPState;
import doormax.PropositionalFunction;
import doormax.structures.Effect;
import doormax.structures.EffectType;
import doormax.utils.Util;
import experiments.utils.OOMDPReaderFromFile;
import taxi.Configurations;
import taxi.TaxiEnvironment2;
import tomlg.Agent;
import tomlg.Environment;
import tomlg.Intention;
import tomlg.tom.AgentTOM;
import tomlg.tom.Observation;

public class Experiment03 {
	private OOMDP oomdp;
	private int maxSteps;
	private Environment environment;
	private Agent agent;
	private OOMDPState currentState;
	private final String mindSaveFile;
	private final String statiscsFile;
	private final String stateFile;
	private final String mindLoadFile;

	public Experiment03(String oomdpFile, String stateFile, String outputFile, int maxSteps, String mindSaveFile,
			String mindLoadObservationFile, String mindLoadFile, String statiscsFile) {
		this.createEnvironmentInstance(oomdpFile, stateFile);
		// cria OOMDP e OOMDPState inicial
		this.maxSteps = maxSteps;
		this.mindSaveFile = mindSaveFile;
		this.stateFile = stateFile;
		this.statiscsFile = statiscsFile;
		this.mindLoadFile = mindLoadObservationFile;

		AgentTOM agent= new AgentTOM("Taxi", this.environment, this.oomdp);

		if (mindLoadObservationFile != null) {
			System.out.println("Loading Mind of Agent From file: " + mindLoadObservationFile);
			agent.loadMindFromFile(mindLoadObservationFile);
		}
		
		/*
		 * Realiza a transferência de conhecimento
		 */
		this.agent = new Agent("Taxi", this.environment, this.oomdp);
		
		if (mindLoadFile != null) {
			System.out.println("Loading Mind of Agent From file: " + mindLoadFile);
			this.agent.loadMindFromFile(mindLoadFile);
		}
		System.out.println("Transfering Knowlodge");
		this.agent.getMind().transferLearning(agent.getMind());
		//this.agent.getMind().
	}

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
		System.out.println(this.environment.getState());
		int step = 0;
		boolean episodeEnded = false;
		while (step <= this.maxSteps && !episodeEnded) {
			System.out.println("On step: " + step++);
			this.agent.step(episodeEnded);
			System.out.println(this.environment.getState());
			episodeEnded = ((TaxiEnvironment2) this.environment.getEnvironmnetSimulator()).isEpisodeEnded();
//			System.out.println(this.agent.getMind());
		}
		this.agent.step(episodeEnded);
		System.out.println("!!Episode Finished!!");
		if (this.mindSaveFile != null) {
			System.out.println("Saving agent Mind to File: " + this.mindSaveFile);
			this.agent.saveMindToFile(this.mindSaveFile);
		}

		if (this.statiscsFile != null) {
			try {
				String str = this.stateFile + ", " + this.mindLoadFile + ", " + this.mindSaveFile + ", " + step;

				BufferedWriter writer = new BufferedWriter(new FileWriter(statiscsFile, true));
				writer.append(str);
				writer.append("\n");

				writer.close();
			} catch (Exception e) {
				System.out.println("Error saving statistics file");
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		if (false) {
			args = new String[8];
			args[0] = "src/Environment01.oomdp";
			args[1] = "../TOMLG-EXPERIMENTS/Cenarios/exp03/exp01-cenario-10x10-064.state";
			args[2] = "experiment01.xml";
			args[3] = "100000";
			args[4] = "../TOMLG-EXPERIMENTS/results/exp03/agent-obs-transfer-learning.csv";
			args[5] = "NULL_SAVE";
			args[6] = "../TOMLG-EXPERIMENTS/trainned_minds/exp03/continuos-obs-train.mind";
			args[7] = "../TOMLG-EXPERIMENTS/trainned_minds/exp03/only-first.trainned.mind";
						
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
		String statiscsFile = (args.length > 4 ? args[4] : null);
		String mindSaveFile = (args.length > 5 ? args[5] : null);
		String mindLoadObservationFile = (args.length > 6 ? args[6] : null);
		String mindLoad = (args.length > 7 ? args[7] : null);
		
		Experiment03 experiment = new Experiment03(oomdpFile, stateFile, outputFile, maxSteps, mindSaveFile,
				mindLoadObservationFile, mindLoad, statiscsFile);
		experiment.runExperiment();
	}
} 