package tomlg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import doormax.DOORMax;
import doormax.OOMDP;
import doormax.OOMDPState;
import doormax.structures.Action;
import doormax.structures.Condition;
import doormax.structures.Effect;
import taxi.Configurations;
import tomlg.goallearning.ActionsEpisodeHistory;
import tomlg.planner.BreathFirstStateSpaceSearch;
import tomlg.planner.Planner;

public class Mind {
	private DOORMax agentLearner;
	private String agentName;

	private List<Goal> goals;
	private Goal choosenGoal;
	private Queue<Intention> intentions;
	private OOMDP oomdp;
	private List<Action> actionRepertoire;
	private List<Intention> intentionsHistory;
	private Planner planner;
	private ActionsEpisodeHistory actionsHistory;
	
	
	public Mind(String agentName, OOMDP oomdp) {
		this.oomdp = oomdp;
		this.agentName = agentName;
		this.agentLearner = new DOORMax(oomdp);
		this.actionRepertoire = this.oomdp.getActions();
		this.goals = new ArrayList<Goal>();
		this.choosenGoal = null;
		this.intentions = new LinkedList<>();
		this.intentionsHistory = new ArrayList<Intention>();
		this.planner = new BreathFirstStateSpaceSearch(this.actionRepertoire);
		this.actionsHistory = new ActionsEpisodeHistory();
	}

	public void learn(OOMDPState currentState) {
		OOMDPState oldState = this.agentLearner.getOldState();
		
		this.agentLearner.learn(currentState, (intentionsHistory.size() == 0 ? null
				: intentionsHistory.get(intentionsHistory.size() - 1).getAction()));

		this.addIntentionalActionToHistory(oldState);
	}
	
	private void addIntentionalActionToHistory(OOMDPState oldState) {
		if(intentionsHistory.isEmpty())
			return;
		
		Condition oldCondition = Condition.evaluate(this.oomdp.getPfIndex(), oldState);
		Intention intentionalAction = intentionsHistory.get(intentionsHistory.size() - 1);
		List<Effect> effects = this.agentLearner.predict(oldState, intentionalAction.getAction());
		this.actionsHistory.addAction(intentionalAction, effects, oldCondition);
	}

	public Intention reasoning(OOMDPState currentState) {

		if (this.choosenGoal == null) {
			if (this.goals.size() == 0) {
				if (this.intentions.size() > 0) {
					return intentions.remove();
				}

				// primeiro verifica quais ações o agente ainda não sabe os efeitos
				Map<Action, List<Effect>> actionPrediction = this.agentLearner.predict(null, actionRepertoire);
				List<Action> unknown = new ArrayList<Action>();
				List<Action> withEffect = new ArrayList<Action>();
				for (Action action : actionPrediction.keySet()) {
					if (actionPrediction.get(action) == null)
						unknown.add(action);
					else if (actionPrediction.get(action).size() != 0)
						withEffect.add(action);
				}
				// escolhe uma ação da lista de ações com efeito desconhecido
				if (unknown.size() != 0) {
					int actionIndex = Configurations.random.nextInt(unknown.size());
					Action action = unknown.get(actionIndex);
					Goal goal = new Goal("intrinsicMotivation",
							"Pursuing unknown effect action. Unknown actions: " + unknown.size());
					Intention intention = new Intention(action, goal);
					return intention;
				}
				// TODO se todas as ações são conhecidas, escolha uma ação que resulte em um
				// estado ainda não explorado
				if (withEffect.size() != 0) {

					Goal goal = new Goal("intrinsicMotivation", null);
					List<Action> actionPlan = planner.planForGoal(goal, currentState, agentLearner);
					if (actionPlan == null) {
						return null;
					}
					goal.setMotivation("Planned actions: " + actionPlan.size() + " Plan: " + actionPlan);

					for (Action e : actionPlan) {
						this.intentions.add(new Intention(e, goal));
					}
					// Intention intention = new Intention(
					// withEffect.get(Configurations.random.nextInt(withEffect.size())), goal);
					return intentions.remove();
				} else
					return null; // TODO impossible situation?

			} else {
				// choose new goal
			}
		} else {
			Intention intention = new Intention(new Action("taxiMoveNorth"), null);
			return intention;
		}
		return null;
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
		builder.append("History:\n\n"+this.actionsHistory);
		return builder.toString();
	}

	/**
	 * TODO Remover na versão final
	 */
	public void saveHistory() {
		this.actionsHistory.toFile();
	}
}
