package tomlg.doormax;

import tomlg.doormax.oomdpformalism.Action;

/**
 * Defines a Prediction as in the OO-MDP formalism
 * 
 * 
 Predictions are uniquely defined by an action, an effect and a condition. They are the unit of DOORMAX
 which summarizes the condition under which an effect is thought to take place when a particular action is
taken
 */
final public class Prediction {
	public final Action action;
	public final Effect effect;
	public final Condition condition;
	
	public Prediction(Action action, Effect effect, Condition condition) {
		super();
		this.action = action;
		this.effect = effect;
		this.condition = condition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((effect == null) ? 0 : effect.hashCode());
		return result;
	}
	
	public Prediction bitwise(Prediction pred) {
		return new Prediction(this.action, this.effect, this.condition.bitwise(pred.condition));
	}

	public Prediction bitwise(Condition cond) {
		return new Prediction(this.action, this.effect, this.condition.bitwise(cond));
	}


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
		if (effect == null) {
			if (other.effect != null)
				return false;
		} else if (!effect.equals(other.effect))
			return false;
		return true;
	}
}
