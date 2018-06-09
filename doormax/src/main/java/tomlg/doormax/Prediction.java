package tomlg.doormax;

import tomlg.doormax.actionconditionlearners.OOMDPActionConditionLearner;
import tomlg.doormax.effects.EffectType;
import tomlg.doormax.effects.NullEffect;
import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectClass;

/**
 * Defines a Prediction as in the OO-MDP formalism
 * 
 * 
 Predictions are uniquely defined by an action, an effect and a condition. They are the unit of DOORMAX
 which summarizes the condition under which an effect is thought to take place when a particular action is
taken
 */
final public class Prediction {
	private OOMDPActionConditionLearner CL;
	public final Effect effect;
	public final Action action;
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
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
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
	


	
	public OOMDPActionConditionLearner getConditionLearner() {
		return this.CL;
	}


	/**
	 * 
	 * @param otherCEL the CELearner to compare against
	 * @return a boolean of whether or not the conditions of the two CELearner's condition learners overlap
	 */
	public boolean conditionsOverlap(Prediction otherCEL) {
		return (this.CL.conditionsOverlap(otherCEL.CL));
	}

	public boolean predictionOn(ObjectClass oClass, ObjectAttribute att, Action ga, EffectType effectType) {
		return this.effect.objectClass.equals(oClass) && 
				this.effect.attribute.equals(att) && 
				this.action.equals(ga)
				&& this.effect.type.equals(effectType);
	}

	/**
	 * 
	 * @param s the state to predict on
	 * @return null if the condition learner predicts false, the relevant effect otherwise
	 */
	public Effect predictResultingEffect(OOMDPState s) {

		Boolean CLPrediction = this.CL.predict(s);

		//CL doesn't know so don't know:
		if (CLPrediction == null) {
//			System.out.println("CLearner doesnt know: " + this.associatedAction);
			return null;
		}

		//CL predicts false -- so it's a no op
		if (!CLPrediction) {
//			System.out.println("CL predicted false for " + this.associatedAction);
			return new NullEffect(null, null, null, null);
		}
		
		//CL predicts true
//		System.out.println("returning effect");
		return this.effect;
	}

	/**
	 * 
	 * @param s the initial state
	 * @param sPrime the resulting state where the effect we are learning for was observed on the relevant object class, attribute and after taking
	 * the relevant action
	 */
	public void updateConditionLearners(OOMDPState s, OOMDPState sPrime, boolean wasTrueInState) {
		this.CL.learn(s, wasTrueInState);
	}


	/**
	 * 
	 * @param CELearner the CELearner against which to compare
	 * @return a boolean as to whether or not the two CELearners are learning for the same effect
	 */
	public boolean learningSameEffect(Prediction CELearner) {
		return this.effect.equals(CELearner.effect);
	}

	/**
	 * 
	 * @param pred other prediction to compare against
	 * @return a boolean of if the two predictions's conditions overlap and they act on the same object types
	 */
	public boolean overlapWithPrediction(Prediction pred) {
		return this.effect.sameClassAndAttribute(pred.effect)
				&& this.conditionsOverlap(pred);
	}
 
	
	@Override
	public String toString() {
		return "\tPrediction for " + this.action.name + "'s effect on " + this.effect.attribute.name + 
				" of " + this.effect.objectClass.name+ 
				"\n\t\tcondition:" + this.CL +
				"\n\t\teffectLearner: " + this.effect;
	}
}
