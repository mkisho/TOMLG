package doormax.structures;

import java.io.Serializable;

public class Prediction implements Serializable{
	private static final long serialVersionUID = 2372934731105068411L;
	private Action action;
	private Condition condition;
	private Effect effect;

	public void updateCondition(Condition cond) {
		this.condition = this.condition.bitwise(cond);
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public Effect getEffect() {
		return effect;
	}

	public void setEffect(Effect effect) {
		this.effect = effect;
	}

	public boolean matches(Prediction toUpdatePrediction) {
		return this.condition.matches(toUpdatePrediction.condition);
	}

	public boolean matches(Condition condition) {
		return this.condition.matches(condition);
	}
	
	public Prediction(Action action, Condition condition, Effect effect) {
		super();
		this.action = action;
		this.condition = condition;
		this.effect = effect;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((effect == null) ? 0 : effect.hashCode());
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
		Prediction other = (Prediction) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (effect == null) {
			if (other.effect != null)
				return false;
		} else if (!effect.equals(other.effect))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Prediction [action=" + action + ", condition=" + condition + ", effect=" + effect + "]";
	}
	
	

}
