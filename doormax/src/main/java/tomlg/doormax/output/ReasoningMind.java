package tomlg.doormax.output;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import tomlg.doormax.oomdpformalism.Action;

public class ReasoningMind {
	private String name;
	private KnowlodgeMemoryBase knowlodge;
	private Action[] actionRepertoire;
	private Scanner scanner;

	public ReasoningMind(String name, Action[] actionRepertoire) {
		this.name = name;
		this.actionRepertoire = actionRepertoire;
		this.scanner = new Scanner(new InputStreamReader(System.in));
		System.out.println("Initializing agent: " + name);
		this.initializeMind();
	}

	private void initializeMind() {
		// initialize Beliefs
		this.knowlodge = new KnowlodgeMemoryBase(this.name);

		for (Action action : this.actionRepertoire) {
			this.knowlodge.addBelief(new BeliefAboutAction(action, this.name, null, null));
		}

	}

	public void addGoalBelief(GoalBelief goal) {
		System.out.println("Agent " + this.name + " has a new goal");
		this.knowlodge.addBelief(goal);
	}

	public void perceive() {
		// update Beliefs about the state of the world
	}

	public void updateBeliefs() {

	}

	// get next action of agent
	public Action reasoning() {
		if (!knowlodge.hasChoosenGoal()) {
			List<GoalBelief> goals = knowlodge.getGoals();
			if(goals.size() == 0) {
				System.out.println("Agent " + this.name + " has no goals! " + "No action selected");
				return null;
			}
			this.knowlodge.setChoosenGoal(goals.get(0),"Random goal selection");
		}
		
		int val = this.scanner.nextInt();
		return this.actionRepertoire[val];
		
		// if do not know what to do, then trie some random action that the agent don't know the outcome
		
		
	}

	public void loop() {

	}
}
