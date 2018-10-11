package tomlg;

import doormax.OOMDP;
import doormax.OOMDPState;
import doormax.structures.Action;

public class Agent {
	private Mind mind;

	private String name;
	private Action[] actionRepertoire;
	private SensoryMonitor sensories;
	private AgentActuator bodyActuators;
	private OOMDP oomdp;

	public Agent(String name, Action[] actionRepertoire, Environment environment, OOMDP oomdp) {
		this.name = name;
		this.actionRepertoire = actionRepertoire;
		System.out.println("Initializing agent: " + name);
		this.sensories = new SensoryMonitor(environment);
		this.bodyActuators = new AgentActuator(environment);
		this.oomdp = oomdp;
		this.initializeMind();
	}

	private void initializeMind() {
		this.mind = new Mind(this.name, this.oomdp);
	}

	public void step() {
		// perceive()
		OOMDPState currentState = this.sensories.environmentToPerception();

		this.mind.learn(currentState);

		Intention intention = this.mind.reasoning();
		assert (intention != null);
		this.bodyActuators.doActionOnEnvironment(intention.getAction());

	}
}
