package tomlg;

import java.io.Serializable;

import doormax.structures.Action;

/**
 * representa uma intenção proximal de conteúdo acionário
 * 
 * @author chronius
 *
 */
public class Intention implements Serializable {
	private static final long serialVersionUID = -2421918046100900340L;
	private Action action;
	private Goal goal;



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((goal == null) ? 0 : goal.getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Intention other = (Intention) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		else if (!goal.getName().equals(other.goal.getName()))
			return false;// TODO FIX THIS
		return true;
	}

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Intention [action=");
		builder.append(action);
		builder.append(", goal=");
		builder.append(goal);
		builder.append("]");
		return builder.toString();
	}

}
