package tomlg;

import java.io.Serializable;
import java.util.List;

import doormax.structures.Action;
import doormax.structures.Effect;

public class Goal implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String motivation; // TODO FIX FIX FIX

	private Action action;
	private List<Effect> effects;

	private boolean trySuccessfully=false;
	
	public void setTrySucessfully(boolean trySuccessfully) {
		this.trySuccessfully = trySuccessfully;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getMotivation() {
		return motivation;
	}

	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Goal [name=");
		builder.append(name);
		builder.append(", motivation=");
		builder.append(motivation);
		builder.append(", action=");
		builder.append(action);
		builder.append(", effects=");
		builder.append(effects);
		builder.append("]");
		return builder.toString();
	}

	public Goal(String name, String motivation) {
		super();
		this.name = name;
		this.motivation = motivation;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public List<Effect> getEffects() {
		return effects;
	}

	public void setEffects(List<Effect> effects) {
		this.effects = effects;
	}

	public boolean checkObjective(Action action, List<Effect> effects) {
		if (this.action == null) {
			if (action != null)
				return false;
		} else if (!this.action.equals(action))
			return false;
		if(this.trySuccessfully)
			return true;
		if (this.effects == null) {
			if (effects != null)
				return false;
		} else if (!this.effects.equals(effects))
			return false;
		return true;
	}

	/* (non-Javadoc)
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

	/* (non-Javadoc)
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
		Goal other = (Goal) obj;
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
	
	
}
