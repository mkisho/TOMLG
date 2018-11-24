package taxi.experiments;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import doormax.OOMDP;
import doormax.PropositionalFunction;
import doormax.structures.Effect;
import doormax.structures.EffectType;
import experiments.utils.OOMDPReaderFromFile;
import taxi.Configurations;
import tomlg.Environment;
import tomlg.Intention;
import tomlg.tom.AgentTOM;
import tomlg.tom.Observation;

public class Experiment02 {
	private OOMDP oomdp;
	private List<Observation> observations;
	private Environment environment;
	private AgentTOM agent;
	private final String mindSaveFile;
	private final String statiscsFile;
	private final String stateFile;
	private final String mindLoadFile;

	public Experiment02(String oomdpFile, String stateFile, String outputFile, int maxSteps, String mindSaveFile,
			String mindLoadFile, String statiscsFile) {
		this.createEnvironmentInstance(oomdpFile, stateFile);
		// cria OOMDP e OOMDPState inicial
		this.mindSaveFile = mindSaveFile;
		this.stateFile = stateFile;
		this.statiscsFile = statiscsFile;
		this.mindLoadFile = mindLoadFile;

		this.agent = new AgentTOM("Taxi", this.environment, this.oomdp);

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

			
			FileInputStream fileInputStream = new FileInputStream(stateFile);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			this.observations = (List<Observation>) objectInputStream.readObject();
			objectInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		System.out.println("Current State has " + this.observations.size() + " objects");

		List<EffectType> effectsToUse = new ArrayList<>();
		for (EffectType e : Effect.γ) {
			effectsToUse.add(e);
		}
	}

	private void runExperiment() {
		
		assert(observations.get(1).getAction().getName().equals(Configurations.DROP)==true);
		observations.remove(1);

		List<Intention> intentions = this.agent.calculateTOM(observations);
		Integer hits = 0;
		Integer total = observations.size()-1;
		for (int i = 1; i < observations.size(); i++) {
			if(intentions.get(i).getAction().equals(observations.get(i).getAction())){
				hits += 1;
			}
		}

		System.out.println("!!Episode Finished!!");
		if (this.mindSaveFile != null) {
			System.out.println("Saving agent Mind to File: " + this.mindSaveFile);
			this.agent.saveMindToFile(this.mindSaveFile);
		}

		if (this.statiscsFile != null) {
			try {
				String str = this.stateFile + ", " + this.mindLoadFile + ", " + this.mindSaveFile + ", " + hits+", "+total+", "+this.agent.getMind().getGoals();

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
			args = new String[7];
			args[0] = "src/Environment01.oomdp";
			args[1] = "../TOMLG-EXPERIMENTS/Cenarios/exp02/exp02-10x10-013.state";
			args[2] = "experiment01.xml";
			args[3] = "100000";
			args[4] = "../TOMLG-EXPERIMENTS/results/exp0002-xmod.csv";
			args[5] = "../TOMLG-EXPERIMENTS/trainned_minds/complete.trainned.exp00xxx02--mod.1.mind";
			args[6] = "../TOMLG-EXPERIMENTS/trainned_minds/complete.trainned.exp00xxx02--mod.1.mind";
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
		String mindLoadFile = (args.length > 6 ? args[6] : null);

		Experiment02 experiment = new Experiment02(oomdpFile, stateFile, outputFile, maxSteps, mindSaveFile,
				mindLoadFile, statiscsFile);
		experiment.runExperiment();
	}
}