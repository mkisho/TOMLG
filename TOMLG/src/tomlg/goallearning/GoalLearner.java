package tomlg.goallearning;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import doormax.structures.Action;
import doormax.structures.Effect;
import taxi.Configurations;
import tomlg.Goal;
import tomlg.Intention;
import tomlg.Mind;

class ActionEffectKey {
	private Action action;
	private List<Effect> effects;
	private int count;
	private Double accumulatedReward;

	public ActionEffectKey(Action action, List<Effect> effects) {
		super();
		this.action = action;
		this.effects = effects;
		this.count = 0;
		this.accumulatedReward = 0.0;
	}

	public static List<ActionEffectKey> buildOrderedListByScore(ActionsEpisodeHistory history) {
		List<ActionEffectKey> list = new ArrayList<>();

		for (ActionEffectConditionTuple tuple : history.getActionHistory()) {
			ActionEffectKey key = new ActionEffectKey(tuple.getIntention().getAction(), tuple.getEffects());
			if (!list.contains(key)) {
				list.add(key);
			}
			if (key.getAction().getName().equals(Configurations.PICK)) {//TODO remove
				tuple.setReward(0.75);
			} else if (key.getAction().getName().equals(Configurations.DROP)) {
				tuple.setReward(0.99);
			} else
				tuple.setReward(0.1);

			list.get(list.indexOf(key)).countOne(tuple.getReward());

		}
		final int lengthHistory = history.getActionHistory().size();
		list.sort((key1, key2) -> {
			return Double.compare(key1.score(lengthHistory), key2.score(lengthHistory));
		});
		return list;
	}

	public void countOne(Double reward) {
		reward = reward == null ? 0.0 : reward;
		this.count += 1;
		this.accumulatedReward += reward;
	}

	public double score(int actionHistorySize) {
		if (Double.compare(this.accumulatedReward, 0.0) == 0) {
			return this.count / (0.0 + actionHistorySize);
		}
		return this.accumulatedReward / (this.count + actionHistorySize);
	}

	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 * @return the effects
	 */
	public List<Effect> getEffects() {
		return effects;
	}

	/**
	 * @param effects the effects to set
	 */
	public void setEffects(List<Effect> effects) {
		this.effects = effects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((effects == null) ? 0 : effects.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActionEffectKey other = (ActionEffectKey) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (effects == null) {
			if (other.effects != null)
				return false;
		} else if (!effects.equals(other.effects))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ActionEffectKey [action=");
		builder.append(action);
		builder.append(", effects=");
		builder.append(effects);
		builder.append(", count=");
		builder.append(count);
		builder.append(", accumulatedReward=");
		builder.append(accumulatedReward);
		builder.append("]");
		return builder.toString();
	}

}

public class GoalLearner {
	/**
	 * The GoalLear algorithm will analyze the History of Intentional Action and
	 * will try to identify a possible subgoal/goal that the agent could use to
	 * guide its actions to fulfill the same "objectives" as in its history.
	 */
	public static List<Goal> reasoning(ActionsEpisodeHistory actionHistory, int maxGoals) {
		/**
		 * As ações que realmente importam serem analizadas, são aquelas que tiveram
		 * sucesso, ou seja, produziram um determinado efeito.
		 * 
		 * As ações sem efeito(realizadas durante o ciclo de aprendizagem), não precisam
		 * ser consideradas a formação de objetivos, pois somente contribuiram para a
		 * formação de pares de ação/efeito/condição
		 */

		ActionsEpisodeHistory historyWithEffects = actionHistory.getActionsWithEffectsAndInstrisicMotivationOnly();
		
		/**
		 * TODO Todas as ações com uma motivação diferente da intrinsica, em teoria não
		 * precisam ser realmente analisadas?
		 */

		List<ActionEffectKey> actionEffectsCounter = ActionEffectKey.buildOrderedListByScore(historyWithEffects);

		for (ActionEffectKey e : actionEffectsCounter) {
			double score = e.score(historyWithEffects.getActionHistory().size());
			System.out.println(score + " - " + e.getAction() + " " + e.getEffects());
		}

		List<Goal> generatedGoals = new ArrayList<Goal>();
		for (int i = 1; i <= maxGoals; i++) {
			ActionEffectKey key = actionEffectsCounter.get(actionEffectsCounter.size() - i);

			String effectsRepresentation = "";
			for (Effect eff : key.getEffects())
				effectsRepresentation += eff.getObjClass().getName() + "->" + eff.getValue() + ", ";

			String goalName = key.getAction().getName() + "."
					+ effectsRepresentation.substring(0, effectsRepresentation.length() - 2);

			Goal goal = new Goal(goalName, "LearnedGoal");
			goal.setAction(key.getAction());
			goal.setEffects(key.getEffects());

			generatedGoals.add(goal);
		}

		return generatedGoals;
//		System.out.println(actionEffectsCounter);
	}

}
