package tomlg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import doormax.DOORMax;
import doormax.OOMDP;
import doormax.OOMDPState;
import doormax.structures.Action;
import doormax.structures.Effect;
import taxi.Configurations;

public class Mind {
	private DOORMax agentLearner;
	private String agentName;

	private List<Goal> goals;
	private Goal choosenGoal;
	private List<Intention> intentions;
	private OOMDP oomdp;
	private List<Action> actionRepertoire;
	private List<Intention> intentionsHistory;

	public Mind(String agentName, OOMDP oomdp) {
		this.oomdp = oomdp;
		this.agentName = agentName;
		this.agentLearner = new DOORMax(oomdp);
		this.actionRepertoire = this.oomdp.getActions();
		this.goals = new ArrayList<Goal>();
		this.choosenGoal = null;
		this.intentions = new ArrayList<Intention>();
		this.intentionsHistory = new ArrayList<Intention>();
	}

	public void learn(OOMDPState currentState) {
		this.agentLearner.learn(currentState, (intentionsHistory.size() == 0 ? null
				: intentionsHistory.get(intentionsHistory.size() - 1).getAction()));
	}

	public Intention reasoning() {

		if (this.choosenGoal == null) {
			if (this.goals.size() == 0) {

				// primeiro verifica quais ações o agente ainda não sabe os efeitos
				Map<Action, List<Effect>> actionPrediction = this.agentLearner.predict(null, actionRepertoire);
				List<Action> unkown = new ArrayList<Action>();
				for (Action action : actionPrediction.keySet()) {
					if (actionPrediction.get(action) == null)
						unkown.add(action);
				}
				// escolhe uma ação da lista de ações com efeito desconhecido
				if (unkown.size() != 0) {
					int actionIndex = Configurations.random.nextInt(unkown.size());
					Action action = unkown.get(actionIndex);
					Goal goal = new Goal("intrinsicMotivation",
							"Pursuing unkown effect action. Unkow actions: " + unkown.size());
					Intention intention = new Intention(action, goal);
					return intention;
				}
				// TODO se todas as ações são conhecidas, escolha uma ação que resulte em um
				// estado
				// ainda não explorado
				Goal goal = new Goal("intrinsicMotivation",
						"Random action. Unkow actions: " + unkown.size());
				Intention intention = new Intention(
						this.actionRepertoire.get(Configurations.random.nextInt(this.actionRepertoire.size())), goal);
				return intention;

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
		builder.append(", agentName=");
		builder.append(agentName);
		builder.append("]");
		return builder.toString();
	}
}
