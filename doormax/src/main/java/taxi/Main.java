package taxi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		if (true) {
			args = new String[4];
			args[0] = "src/Untitled1";
			args[1] = "src/State0";
			args[2] = "teste.xml";
			args[3] = "3";
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
		experiment.simulateExperiment();
	}
}
