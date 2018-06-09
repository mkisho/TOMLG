package tomlg.doormax.actionconditionlearners;

import java.util.List;

import tomlg.doormax.Condition;
import tomlg.doormax.PropositionalFunction;
import tomlg.doormax.oomdpformalism.OOMDPState;

/**
 * Aprende os pares (ação, efeito, condição) de acordo com o Doormax clássico
 * @
 * @author chronius,  
 * 
 *  Baseado no trabalho de @author Dhershkowitz
 *
 */
public class PFConditionLearner extends OOMDPActionConditionLearner{

	List<PropositionalFunction> propFuns;
	private Condition condition;

	/**
	 * 
	 */
	public PFConditionLearner(List<PropositionalFunction> propFuns){
		this.condition = null;
		this.propFuns = propFuns;
	}

	/**
	 * 
	 * @return the CH that predicts true for this CL
	 */
	private Condition getCondition() {
		return this.condition;
	}

	/**
	 * 
	 * @param otherCL
	 * @return
	 */
	private boolean conditionsOverlap(PFConditionLearner otherCL) {
		return this.condition.match(otherCL.getCondition()) || otherCL.condition.match(this.getCondition());
	}

	/**
	 * 
	 * @param currStatePreds state to test the truth of the condition in
	 * @return a boolean of if the condition entails the state
	 */
	private boolean conditionTrueInState(Condition c){
		if (this.condition == null) return false;
		return this.condition.match(c);
	}

	/**
	 * 
	 * @param statePreds s state as a series of truth bits for propositional functions in which the condition was true
	 */
	private void updateVersionSpace(Condition c) {

		//New HSubT
		if (condition == null) {
			condition = c;
		}
		//HSubT already instantiated
		else {
			condition = condition.bitwise(c);
		}
	}


	@Override
	public String toString() {
		if (this.condition != null) {
			return this.condition.toString();
		}

		return null;
	}

	@Override
	public void learn(OOMDPState s, boolean trueInState) {
		Condition c = s.toCondition();
		if (trueInState) {
			this.updateVersionSpace(c);
		}
	}

	@Override
	public Boolean predict(OOMDPState s) {
		Condition c = s.toCondition();
		return this.conditionTrueInState(c);
	}

	@Override
	public boolean conditionsOverlap(OOMDPActionConditionLearner otherLearner) {
		return this.condition.match(((PFConditionLearner) otherLearner).getCondition()) ||
				(((PFConditionLearner) otherLearner).getCondition()).match(this.condition);
	}
}
