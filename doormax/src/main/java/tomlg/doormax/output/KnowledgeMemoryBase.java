package tomlg.doormax.output;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import tomlg.doormax.Condition;
import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectInstance;

public class KnowledgeMemoryBase {
	private String myAgent;

	private List<WorldBelief> worldBeliefs;

	private List<TaxiGoalBelief> goalsBeliefs;

	private List<BeliefAboutAction> effectsBeliefs;
	
	private List<PrevisionBelief> previsionsBeliefs;

	private TaxiGoalBelief choosenGoal;

	private OOMDPState currentState;
	private OOMDPState previousState;
	private OOMDPState initialState;

	public KnowledgeMemoryBase(String myAgent) {
		this.myAgent = myAgent;
		this.worldBeliefs = new ArrayList<WorldBelief>(100);
		this.goalsBeliefs = new ArrayList<TaxiGoalBelief>(10);
		this.effectsBeliefs = new ArrayList<BeliefAboutAction>(100);
		this.previsionsBeliefs = new ArrayList<PrevisionBelief>(100);
		this.choosenGoal = null;
		this.currentState = null;
		this.previousState = null;
	}

	public List<PrevisionBelief> getPrevisionsBeliefs() {
		return previsionsBeliefs;
	}

	public void initialize(OOMDPState initialState) {
		this.initialState = initialState;
	}

	public void addBelief(Belief belief) {
		System.out.println("Adding new Belief to base of agent " + this.myAgent);

		if (belief instanceof TaxiGoalBelief) {
			this.goalsBeliefs.add((TaxiGoalBelief) belief);
		}
	}

	public KnowledgeMemoryBase saveState() {
		return null;
	}

	public boolean hasChoosenGoal() {
		return (this.choosenGoal != null);
	}

	public List<TaxiGoalBelief> getGoals() {
		return this.goalsBeliefs;
	}

	public void setChoosenGoal(TaxiGoalBelief goalBelief, String selectionMotivation) {
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
		if (this.currentState == null)
			this.initialState = worldState;
		this.previousState = this.currentState;
		this.currentState = worldState;

		this.worldBeliefs.clear();

		for (ObjectInstance objInstance : worldState.objects) {
			this.worldBeliefs.add(WorldBelief.fromObjInstance(objInstance));
		}
		Condition worldCondition = worldState.toCondition();
		for (int i = 0; i < worldCondition.conditionBitArray.length; i++) {
			char cond = worldCondition.conditionBitArray[i];
			boolean eval = false;
			if (cond == '1') {
				eval = true;
			}
			this.worldBeliefs
					.add(WorldBelief.fromPropositionalFunction(worldCondition.bitStringPropositionsIndex[i], eval));
		}
	}

	public TaxiGoalBelief getChoosenGoal() {
		return choosenGoal;
	}

	public List<WorldBelief> getWorldBeliefs() {
		return worldBeliefs;
	}

	public OOMDPState getCurrentState() {
		return currentState;
	}

	public OOMDPState getPreviousState() {
		if (this.previousState == null)
			return this.initialState;
		return previousState;
	}

	public void updateBeliefsAboutAction(List<BeliefAboutAction> modeledBeliefs) {
		this.effectsBeliefs.clear();
		this.effectsBeliefs.addAll(((List<BeliefAboutAction>) modeledBeliefs));
	}

	public List<BeliefAboutAction> getActionBeliefs() {
		return this.effectsBeliefs;
	}

	public List<Action> whichActionsUnknow(List<Action> movActions) {
		List<Action> unkownActions = new ArrayList<Action>();
		for (Action action : movActions) {
			if (!isActionKnow(action))
				unkownActions.add(action);
		}
		return unkownActions;
	}

	public boolean isActionKnow(Action action) {
		for (BeliefAboutAction belief : this.getActionBeliefs()) {
			if (belief.getAction().equals(action)) {
				return belief.getPredictions().size() > 0;
			}
		}
		return false;
	}

	public void updatePredictionBeliefs(List<PrevisionBelief> predictionBeliefs) {
		this.previsionsBeliefs.clear();
		this.previsionsBeliefs.addAll(predictionBeliefs);
	}
}
