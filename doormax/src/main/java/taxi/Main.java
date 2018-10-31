package taxi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import tomlg.doormax.utils.RandomGameGenerator;
import weka.gui.beans.StripChartBeanInfo;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		RandomGameGenerator randomGameGenerator = new RandomGameGenerator();

		int size = 10;
		String fileNamePrefix = "exp01-cenario-" + size + "x" + size + "-";
		for (int i = 1; i <= 100; i++) {
			String fileName = fileNamePrefix + String.format("%03d", i) + ".state";
			while (!randomGameGenerator.gerar(fileName, size))
				;
			System.out.println(i);

		}
		System.out.println("xxxx");

		if (false) {
			args = new String[4];
			args[0] = "src/Environment01.oomdp";
			args[1] = "src/Environment01.state";
			args[2] = "environment01.xml";
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
		experiment.simulateExperiment();
	}
}
