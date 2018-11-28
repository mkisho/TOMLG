package tomlg.tom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import doormax.DOORMax;
import doormax.OOMDP;
import doormax.OOMDPState;
import doormax.structures.Action;
import taxi.Configurations;
import tomlg.Goal;
import tomlg.Intention;
import tomlg.Mind;
import tomlg.goallearning.ActionsEpisodeHistory;
import tomlg.goallearning.MatrixCounter;
import tomlg.planner.BreathFirstStateSpaceSearch;
import tomlg.planner.Planner;

public class MindTOM implements Serializable {
	private static final long serialVersionUID = -3257744343001935691L;

	private DOORMax agentLearner;
	private String agentName;

	private Set<Goal> goals;
	private Set<Goal> invalidGoals;
	private transient Goal choosenGoal;
	
	private transient Queue<Intention> intentions;
	private OOMDP oomdp; // TODO é necessário salvar o OOMDP também????
	private final List<Action> actionRepertoire;
	
	private transient List<Action> predictedActions;
	private transient List<Intention> intentionsHistory;
	private transient Planner planner;
	private transient ActionsEpisodeHistory actionsHistory;
	
	private transient Action lastAction;
	
	private MatrixCounter matrixCounter;
	private int minNumSequencesToDeriveFDI = Configurations.MIN_NUM_SEQUENCES_DERIVE_FDI;

	public MindTOM(String agentName, OOMDP oomdp) {
		this.oomdp = oomdp;
		this.agentName = agentName;
		this.agentLearner = new DOORMax(oomdp);
		this.actionRepertoire = this.oomdp.getActions();
		this.goals = new HashSet<Goal>();
		this.choosenGoal = null;
		this.intentions = new LinkedList<>();
		this.intentionsHistory = new ArrayList<Intention>();
		this.planner = new BreathFirstStateSpaceSearch(this.actionRepertoire);
		this.actionsHistory = new ActionsEpisodeHistory();

		this.matrixCounter = new MatrixCounter(this.actionRepertoire);
		this.invalidGoals = new HashSet<Goal>();
		this.predictedActions = new ArrayList<Action>();
	}
	

	public void learn(OOMDPState currentState, Action action) {
		this.agentLearner.learn(currentState, action);
		
		this.matrixCounter.addToCounter(this.lastAction, action);
		this.lastAction = action;
	}

	public Intention reasoning(OOMDPState currentState) {
		/**
		 * Checa a previsão. Se errou, então recriar as crenças.
		 */
		if (!this.predictedActions.isEmpty()
				&& !this.predictedActions.get(this.predictedActions.size() - 1).equals(this.lastAction)) {
			if (this.choosenGoal != null) {
				System.out.println("Invalid chooseen Goal " + this.choosenGoal);
				this.invalidGoals.add(this.choosenGoal);
				this.goals.remove(this.choosenGoal);
				this.choosenGoal = null;
				this.intentions.clear();
			}
			this.inferFDI();
		}

		if (intentions.isEmpty()) {
			choosenGoal = null;
		} else {
			Intention intention = intentions.remove();
			this.predictedActions.add(intention.getAction());
			return intention;
		}

		if (this.choosenGoal == null) {
			if (!this.goals.isEmpty()) {
				// TODO ESSA PARTE É BEM IMPORTANTE, IMPLEMENTA-LA IGUAL O MODELO
				for (Goal goal : this.goals) {
					System.out.println("Planning for goal: "+goal);
					List<Action> actionPlan = planner.planForGoal(goal, currentState, agentLearner);
					if (actionPlan == null) {
						continue;
					}
					actionPlan.remove(actionPlan.size()-1);
					for (Action e : actionPlan) {
						this.intentions.add(new Intention(e, goal));
					}
					// Intention intention = new Intention(
					// withEffect.get(Configurations.random.nextInt(withEffect.size())), goal);
					System.out.println("Plan Generated for Goal: " + actionPlan);
					System.out.println("Agent Choosed goal: " + goal);
					this.choosenGoal = goal;

					Intention intention = intentions.remove();
					this.predictedActions.add(intention.getAction());
					return intention;

				}
				System.out.println("No plan for goals");
			}
			System.out.println("Prevision by probability");
			Goal goal = new Goal("Prevision", "By maximum");
			Intention intention = new Intention(this.matrixCounter.predictNextByMax(this.lastAction), goal);
			if (intention.getAction() != null)
				this.predictedActions.add(intention.getAction());
			return intention;
		}

		System.exit(-1);
		return null;
	}

	public void inferFDI() {
		if(this.matrixCounter.getObservedSequenceCounter() > Configurations.MIN_NUM_SEQUENCES_DERIVE_FDI) {
			List<Goal> invalidNewGoals = new ArrayList<Goal>();
			invalidNewGoals.addAll(this.goals);
			invalidNewGoals.addAll(this.invalidGoals);
			assert(invalidNewGoals.size() == (this.goals.size() + this.invalidGoals.size()));
			
			Goal newInferedGoal = this.matrixCounter.predictGoalByMin(invalidNewGoals);
			if(newInferedGoal != null) {
				newInferedGoal.setTrySucessfully(true);
				this.goals.add(newInferedGoal);
			}else if(newInferedGoal == null) {
				if((this.invalidGoals.size() + this.goals.size())>= this.actionRepertoire.size()) {
					this.invalidGoals.clear();
//					inferFDI();
				}
			}
			
		}
	}

	public void addIntentionToHistory(Intention intention) {
		this.intentionsHistory.add(intention);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Mind [agentLearner=");
		builder.append(agentLearner);
		builder.append("\n, agentName=");
		builder.append(agentName);
		builder.append("]");
		builder.append("History:\n\n" + this.actionsHistory);
		return builder.toString();
	}

	/**
	 * TODO Remover na versão final
	 */
	public void saveHistory() {
		this.actionsHistory.toFile();
	}

	public void initializeMindWhenLoadedFromFile() {
		this.intentionsHistory = new ArrayList<Intention>();
		this.planner = new BreathFirstStateSpaceSearch(this.actionRepertoire);
		this.actionsHistory = new ActionsEpisodeHistory();
		this.intentions = new LinkedList<>();
		this.predictedActions = new ArrayList<Action>();
	}

	public String getGoals() {
		return this.goals.toString();
	}


	public void endReasoning() {
		this.matrixCounter.incObservedSequenceCounter();
	}


	public DOORMax getAgentLearning() {
		return this.agentLearner;
	}


		/**
	 * TODO arrumar em versões posteriores
	 * @param mind
	 */
	public void transferLearning(Mind mind) {
		this.agentLearner.combine(mind.getAgentLearning());
	}

}
