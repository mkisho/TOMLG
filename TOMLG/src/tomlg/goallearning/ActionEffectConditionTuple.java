package tomlg.goallearning;

import java.io.Serializable;
import java.util.List;
import doormax.structures.Condition;
import doormax.structures.Effect;
import tomlg.Intention;

public class ActionEffectConditionTuple implements Serializable {
	private static final long serialVersionUID = -65715788588682510L;
	private Intention intention;
	private List<Effect> effects;
	private Condition condition;
	private Double reward;

	public ActionEffectConditionTuple(Intention intention, List<Effect> effects, Condition condition) {
		super();
		this.intention = intention;
		this.effects = effects;
		this.condition = condition;
		this.reward = null;
	}

	public ActionEffectConditionTuple(Intention intention, List<Effect> effects, Condition condition, Double reward) {
		super();
		this.intention = intention;
		this.effects = effects;
		this.condition = condition;
		this.reward = reward;
	}

	public Intention getIntention() {
		return intention;
	}

	public List<Effect> getEffects() {
		return effects;
	}

	public Condition getCondition() {
		return condition;
	}

	public Double getReward() {
		return reward;
	}
	

	/**
	 * @param reward the reward to set
	 */
	public void setReward(Double reward) {
		this.reward = reward;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ActionEffectConditionTuple [intention=");
		builder.append(intention);
		builder.append(", effects=");
		builder.append(effects);
		builder.append(", condition=");
		builder.append(condition);
		builder.append("]\n");
		return builder.toString();
	}
}
