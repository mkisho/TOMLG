package tomlg.doormax.output;

import java.util.ArrayList;
import java.util.List;

public class KnowlodgeMemoryBase {
	private String myAgent;

	private List<Belief> beliefs;

	private List<GoalBelief> goalsBeliefs;
	private GoalBelief choosenGoal;

	public KnowlodgeMemoryBase(String myAgent) {
		this.myAgent = myAgent;
		this.beliefs = new ArrayList<Belief>(100);
		this.goalsBeliefs = new ArrayList<GoalBelief>(10);
		this.choosenGoal = null;
	}

	public void initialize() {

	}

	public void addBelief(Belief belief) {
		System.out.println("Adding new Belief to base of agent " + this.myAgent);
		
		if(belief instanceof GoalBelief) {
			this.goalsBeliefs.add((GoalBelief)belief);
		}
	}

	public void updateBelief() {

	}

	public void removeBelief() {

	}

	public KnowlodgeMemoryBase saveState() {
		return null;
	}

	public boolean hasChoosenGoal() {
		return (this.choosenGoal != null);
	}

	public List<GoalBelief> getGoals() {
		return this.goalsBeliefs;
	}

	public void setChoosenGoal(GoalBelief goalBelief, String selectionMotivation) {
		if (this.goalsBeliefs.contains(goalBelief)) {
			System.out.println("Agent " + this.myAgent + " has choosen a new goal");
			goalBelief.setMotivationForSelection(selectionMotivation);
			this.choosenGoal = goalBelief;
		} else {
			System.out.println("Agent " + this.myAgent + " tried to select a choosen goal without the goal being in the goals beliefs of the agent");
		}
	}

}
