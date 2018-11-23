package tomlg.goallearning;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import doormax.structures.Action;
import taxi.Configurations;
import tomlg.Goal;
import tomlg.tom.Observation;

/**
 * Implementa uma tabela de transição de Markov
 * 
 * @author chronius
 *
 */
public class MatrixCounter implements Serializable {
	private static final long serialVersionUID = 3343229024297965495L;

	private Map<Action, Integer> actionsIndex;
	private int numActions;
	private int[][] matrixCounter;
	private List<Action> actions;
	private int observedSequencesNum;

	public MatrixCounter(List<Action> actions) {
		actionsIndex = new HashMap<Action, Integer>();
		int index = 0;
		for (Action action : actions) {
			this.actionsIndex.put(action, index);
			index++;
		}
		this.actions = actions;
		this.numActions = actions.size();
		this.observedSequencesNum = 0;
		matrixCounter = new int[this.numActions][this.numActions];
	}

	public void incObservedSequenceCounter() {
		this.observedSequencesNum++;
	}

	public int getObservedSequenceCounter() {
		return this.observedSequencesNum;
	}

	public void addToCounter(Action from, Action to) {
		if (from == null || to == null) {
			return;// TODO contar a primeira ação apatir de um estado nulo
		}
		final int fromIndex = actionsIndex.get(from);
		final int toIndex = actionsIndex.get(to);
		this.matrixCounter[fromIndex][toIndex] += 1;
	}

	public void addToCounter(List<Observation> observationList) {
		for (int i = 1; i < observationList.size(); i++) {
			Action from = observationList.get(i - 1).getAction();
			Action to = observationList.get(i).getAction();
			this.addToCounter(from, to);
		}
	}

	public Action predictNextByMax(final Action lastAction) {
		if (lastAction == null) {
			return this.actions.get(Configurations.random.nextInt(this.numActions));
		}
		int indexFrom = this.actionsIndex.get(lastAction);
		int max = 0;

		for (int i = 0; i < this.numActions; i++) {
			if (this.matrixCounter[indexFrom][i] > this.matrixCounter[indexFrom][max]) {
				max = i;
			}
		}

		return this.actions.get(max);
	}

	public Goal predictGoalByMin(List<Goal> invalidGoals) {
		int minIndex = -1;
		int min = 1000000000;
		for (int i = 0; i < this.numActions; i++) {
			Action action = this.actions.get(i);

			boolean ignore = false;
			for (Goal goal : invalidGoals) {
				if (goal.getAction().getName().equals(action.getName())) {
					ignore = true;
					break;
				}
			}
			if (ignore)
				continue;

//			if (invalidGoals.stream().filter(p -> p.getAction().getName().equals(action.getName())).findAny().isPresent())
//				continue;
			int sum = 0;
			for (int j = 0; j < this.numActions; j++) {
				sum += this.matrixCounter[j][i];
			}
			if (sum < min) {
				minIndex = i;
				min = sum;
			}
		}
		if(minIndex == -1)
			return null;

		Goal goal = new Goal("LearnedGoal", "prediction");
		goal.setAction(this.actions.get(minIndex));
		return goal;
	}

}
