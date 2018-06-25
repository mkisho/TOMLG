package tomlg.doormax.output;

import java.util.ArrayList;
import java.util.List;

import tomlg.doormax.Condition;
import tomlg.doormax.PropositionalFunction;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectInstance;

public class KnowledgeMemoryBase {
	private String myAgent;

	private List<Belief> worldBeliefs;

	private List<GoalBelief> goalsBeliefs;
	private GoalBelief choosenGoal;

	public KnowledgeMemoryBase(String myAgent) {
		this.myAgent = myAgent;
		this.worldBeliefs = new ArrayList<Belief>(100);
		this.goalsBeliefs = new ArrayList<GoalBelief>(10);
		
		this.choosenGoal = null;
	}

	public void initialize() {

	}

	public void addBelief(Belief belief) {
		System.out.println("Adding new Belief to base of agent " + this.myAgent);

		if (belief instanceof GoalBelief) {
			this.goalsBeliefs.add((GoalBelief) belief);
		}
	}

	public void updateBelief() {

	}

	public void removeBelief() {

	}

	public KnowledgeMemoryBase saveState() {
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
			System.out.println("Agent " + this.myAgent
					+ " tried to select a choosen goal without the goal being in the goals beliefs of the agent");
		}
	}

	public void updateWorldStateBelief(OOMDPState worldState) {
		this.worldBeliefs.clear();

		for (ObjectInstance objInstance : worldState.objects) {
			this.worldBeliefs.add(WorldBelief.fromObjInstance(objInstance));
		}
		Condition worldCondition = worldState.toCondition();
		for(int i=0; i < worldCondition.conditionBitArray.length; i++) {
			char cond = worldCondition.conditionBitArray[i];
			boolean eval = false;
			if(cond =='1') {
				eval = true;
			}
			this.worldBeliefs.add(WorldBelief.fromPropositionalFunction(worldCondition.bitStringPropositionsIndex[i], 
					eval));
		}
	}

}
