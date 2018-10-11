package tomlg;

import doormax.OOMDPState;
import doormax.structures.Action;

public class Agent {
	private Mind mind;

	private String name;
	private Action[] actionRepertoire;
	private SensoryMonitor sensories;
	private AgentActuator bodyActuators;

	public Agent(String name, Action[] actionRepertoire, Environment environment) {
		this.name = name;
		this.actionRepertoire = actionRepertoire;
		System.out.println("Initializing agent: " + name);
		this.sensories = new SensoryMonitor(environment);
		this.bodyActuators = new AgentActuator(environment);
		this.initializeMind();
	}

	private void initializeMind() {
		// TODO Auto-generated method stub

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
