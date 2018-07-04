package taxi;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Experiment experiment = new Experiment("src/Untitled1", "Jogo982.txt", 10);
		experiment.simulateStep();
		experiment.simulateStep();
		experiment.simulateStep();
		experiment.simulateStep();
		experiment.simulateStep();
		experiment.simulateStep();

	}

}
