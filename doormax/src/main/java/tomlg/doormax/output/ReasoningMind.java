package tomlg.doormax.output;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.OOMDPState;

public class ReasoningMind {
	private String name;
	private KnowledgeMemoryBase knowledge;
	private Action[] actionRepertoire;
	private Scanner scanner;
	private SensoryOrgans sensories;
	private EnvironmentActuation bodyActuators;
	
	public ReasoningMind(String name, Action[] actionRepertoire, Environment environment) {
		this.name = name;
		this.actionRepertoire = actionRepertoire;
		this.scanner = new Scanner(new InputStreamReader(System.in));
		System.out.println("Initializing agent: " + name);
		this.initializeMind();
		this.sensories = new SensoryOrgans(environment);
		this.bodyActuators = new EnvironmentActuation(environment);
	}

	private void initializeMind() {
		// initialize Beliefs
		this.knowledge = new KnowledgeMemoryBase(this.name);

		for (Action action : this.actionRepertoire) {
			this.knowledge.addBelief(new BeliefAboutAction(action, this.name, null, null));
		}
	}

	public void addGoalBelief(GoalBelief goal) {
		System.out.println("Agent " + this.name + " has a new goal");
		this.knowledge.addBelief(goal);
	}

	public void perceive() {
		System.out.println("Agent "+this.name+" is perceiving the environment");
		OOMDPState worldState = this.sensories.environmentToPerception();
		this.knowledge.updateWorldStateBelief(worldState);
		// update Beliefs about the state of the world
	}

	public void updateBeliefs() {

	}
	
	public void tick() {
		// update what I know about the state
		this.perceive();
		
		Action action = reasoning();
		if(action!= null)
			this.bodyActuators.doActionOnEnvironment(action);
	}

	// get next action of agent
	public Action reasoning() {
		System.out.println("Agent "+this.name+" is reasoning");
	/*	if (!knowlodge.hasChoosenGoal()) {
			List<GoalBelief> goals = knowlodge.getGoals();
			if(goals.size() == 0) {
				System.out.println("Agent " + this.name + " has no goals! " + "No action selected");
				return null;
			}
			this.knowlodge.setChoosenGoal(goals.get(0),"Random goal selection");
		}*/
		
		int val = this.scanner.nextInt();
		return this.actionRepertoire[val];
		
		// if do not know what to do, then I will try some random action that the agent don't know the outcome
	}
}
