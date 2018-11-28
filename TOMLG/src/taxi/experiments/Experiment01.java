package taxi.experiments;

import java.io.BufferedWriter;
import java.io.FileWriter;
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

/**
 * Experiment 01
 * 
 * @author chronius
 *
 */
public class Experiment01 {
	private int maxSteps;
	private OOMDP oomdp;
	private OOMDPState currentState;
	private Environment environment;
	private Agent agent;
	private final String mindSaveFile;
	private final String statiscsFile;
	private final String stateFile;
	private final String mindLoadFile;

	public Experiment01(String oomdpFile, String stateFile, String outputFile, int maxSteps, String mindSaveFile,
			String mindLoadFile, String statiscsFile) {
		this.maxSteps = maxSteps;
		this.createEnvironmentInstance(oomdpFile, stateFile);
		// cria OOMDP e OOMDPState inicial
		this.mindSaveFile = mindSaveFile;
		this.stateFile = stateFile;
		this.statiscsFile = statiscsFile;
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
}