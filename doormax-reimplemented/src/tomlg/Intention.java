package tomlg;

import doormax.structures.Action;

/**
 * representa uma intenção proximal de conteúdo acionário
 * 
 * @author chronius
 *
 */
public class Intention {
	private Action action;
	private Goal goal;

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Goal getGoal() {
		return goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	public Intention(Action action, Goal goal) {
		super();
		this.action = action;
		this.goal = goal;
	}
}
