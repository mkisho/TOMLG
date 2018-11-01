package tomlg;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import doormax.OOMDP;
import doormax.OOMDPState;
import doormax.structures.Condition;

public class Agent implements Serializable {
	private static final long serialVersionUID = -8490880585101019596L;

	private Mind mind;

	private String name;
	private transient SensoryMonitor sensories;
	private transient AgentActuator bodyActuators;
	private transient OOMDP oomdp;

	public Agent(String name, Environment environment, OOMDP oomdp) {
		this.name = name;
		System.out.println("Initializing agent: " + name);
		this.sensories = new SensoryMonitor(environment);
		this.bodyActuators = new AgentActuator(environment);
		this.oomdp = oomdp;
		this.initializeMind();
	}

	private void initializeMind() {
		this.mind = new Mind(this.name, this.oomdp);
	}

	public void step(boolean finalEpisode) {
		// perceive()
		OOMDPState currentState = this.sensories.environmentToPerception();
		Condition condition = Condition.evaluate(oomdp.getPfIndex(), currentState);

		this.mind.learn(currentState);

		if (finalEpisode) {// TODO fix this
//			this.mind.saveHistory(); // TODO Remover na versão final
			this.mind.reasonAboutGoals();
//			this.saveMindToFile();
			return;
		}

		Intention intention = this.mind.reasoning(currentState);
		assert (intention != null);

		System.out.println("Reasoning >> " + intention + " cond: " + condition.toString());

		this.bodyActuators.doActionOnEnvironment(intention.getAction());
		this.mind.addIntentionToHistory(intention);
	}

	public void saveMindToFile(String fileName) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(this.mind);
			objectOutputStream.flush();
			objectOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void loadMindFromFile(String fileName) {
		try {
			FileInputStream fileInputStream = new FileInputStream(fileName);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			this.mind = (Mind) objectInputStream.readObject();
			this.mind.initializeMindWhenLoadedFromFile();
			objectInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public Mind getMind() {
		return this.mind;
	}
}
