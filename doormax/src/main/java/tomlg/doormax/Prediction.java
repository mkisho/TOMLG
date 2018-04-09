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
public class Prediction {
	public final Action action;
	public final Effect effect;
	public final Condition condition;
	
	public Prediction(Action action, Effect effect, Condition condition) {
		super();
		this.action = action;
		this.effect = effect;
		this.condition = condition;
	}
}
