package tomlg.doormax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tomlg.doormax.effects.EffectType;
import tomlg.doormax.effects.NullEffect;
import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.OOMDP;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectClass;
import tomlg.doormax.oomdpformalism.ObjectInstance;
import tomlg.doormax.output.Belief;
import tomlg.doormax.output.BeliefAboutAction;
import tomlg.doormax.output.PrevisionBelief;
import tomlg.doormax.utils.DiscreteStateHashFactory;

public class PredictionsLearner {

	/**
	 * Class to learn all predictions for as documented in "An Object-Oriented
	 * Representation for Efficient Reinforcement Learning" by Diuk et al.
	 * 
	 * @author Dhershkowitz
	 *
	 */
	private HashMap<PaperAttributeActionTuple, List<Condition>> failureConditionsByActionOClassAtt;
	private HashMap<AttActionEffectTuple, List<Prediction>> predictionsByAttActionAndEffect;
	private EffectType[] effectsToUse;
	private OOMDP oomdp;
	private int k;
	private PropositionalFunction[] propFunsToUse;
	private String statePerceptionToUse;
	private List<Action> actionList;
	private String agentName;

	/**
	 * 
	 * @param oomdp
	 *            domain to use
	 * @param propFuns
	 *            relevant prop funs to plan over (using the groundings of in
	 *            initial state)
	 * @param effectsToUse
	 *            effect types by string to consider (documented as static elements
	 *            of EffectHelpers)
	 * @param actionsToUse
	 *            relevant actions to plan over (using the groundings in initial
	 *            state)
	 * @param initialState
	 *            the state to groundings from
	 * @param k
	 *            the max number of effects (of one type) that an action can have
	 */
	public PredictionsLearner(OOMDP oomdp, PropositionalFunction[] propFuns, EffectType[] effectsToUse,
			List<Action> actionsToUse, OOMDPState initialState, int k, String statePerceptionToUse, String agentName) {
		this.statePerceptionToUse = statePerceptionToUse;
		this.oomdp = oomdp;
		this.k = k;
		this.propFunsToUse = propFuns;
		this.actionList = new ArrayList<Action>();
		this.agentName = agentName;
		// for (Action a : actionsToUse) {
		this.actionList.addAll(actionsToUse);// a.getAllApplicableGroundedActions(initialState));
		// }
		this.effectsToUse = effectsToUse;

		// Set up HM for failure conditions
		this.failureConditionsByActionOClassAtt = new HashMap<PaperAttributeActionTuple, List<Condition>>();
		for (Action ga : this.actionList) {
			for (ObjectClass oClass : this.oomdp.getObjectClasses()) {
				for (ObjectAttribute att : oClass.attributes) {
					PaperAttributeActionTuple toHashBy = new PaperAttributeActionTuple(oClass, att, ga);
					this.failureConditionsByActionOClassAtt.put(toHashBy, new ArrayList<Condition>());
				}

			}

		}

		// Set up HM for predictions
		this.predictionsByAttActionAndEffect = new HashMap<AttActionEffectTuple, List<Prediction>>();
		for (Action a : this.actionList) {
			for (ObjectClass oClass : this.oomdp.getObjectClasses()) {
				for (ObjectAttribute att : oClass.attributes) {
					for (EffectType effectType : this.effectsToUse) {
						AttActionEffectTuple toHashBy = new AttActionEffectTuple(oClass, att, a, effectType);
						this.predictionsByAttActionAndEffect.put(toHashBy, new ArrayList<Prediction>());
					}
				}
			}
		}
	}

	public HashMap<AttActionEffectTuple, List<Prediction>> getPredictionsByAttActionAndEffect() {
		return this.predictionsByAttActionAndEffect;
	}

	/**
	 * 
	 * @param pred
	 *            to check for overlaps with
	 * @param relevantPredictions
	 *            a list of predicates to check if pred overlaps against
	 * @return a boolean of if pred's conditon overlaps with a condition in
	 *         relevantLearners (other than itself)
	 */
	private boolean predictionsOverlap(Prediction pred, List<Prediction> relevantPredictions) {
		// Filter out those that don't act on same object type

		for (Prediction otherPred : relevantPredictions) {
			if (relevantPredictions.indexOf(pred) != relevantPredictions.indexOf(otherPred)
					&& pred.overlapWithPrediction(otherPred)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param relevantPredictions
	 *            a list of the predictions that might predict observedEffect
	 * @param observedEffect
	 *            an effect to search for a prediction that predicts it
	 * @return the prediction that predicts observedEffect or null if none did
	 */
	private Prediction predThatPredictsThisEffect(List<Prediction> relevantPredictions, Effect observedEffect) {
		for (Prediction CELearner : relevantPredictions) {
			if (CELearner.effect.equals(observedEffect)) {
				return CELearner;
			}
		}
		return null;
	}

	public void learn(OOMDPState oldState, Action action, OOMDPState newState) {

		// TODO remove after
		List<ObjectClass> oclassList = this.oomdp.getObjectClasses();
		// List<ObjectClass> oclassList = new ArrayList<ObjectClass>();
		// oclassList.add(oldState.getObjectOfClass("taxi").objectClass);
		for (ObjectClass oClass : oclassList) {
			for (ObjectAttribute att : oClass.attributes) {
				learnForOClassAndAtt(oldState, action, newState, oClass, att);
			}
		}
	}

	private boolean classAndAttUnchanged(OOMDPState oldState, OOMDPState newState, ObjectClass oClass,
			ObjectAttribute att) {
		List<ObjectInstance> objectInstances = oldState.getObjectsOfClass(oClass.name);
		for (ObjectInstance o : objectInstances) {
			Double valBefore = o.getAttributeValByName(att.name).getNumericValForAttribute();

			try {
				Double valAfter = newState.objectsByName.get(o.getId()).getAttributeValByName(att.name)
						.getNumericValForAttribute();
				if (Math.abs(valBefore - valAfter) > 0.0000001)
					return false;
			} catch (Exception e) {
				return true;
				// e.printStackTrace();
			}
		}

		return true;
	}

	private void learnForOClassAndAtt(OOMDPState oldState, Action action, OOMDPState currentState, ObjectClass oClass,
			ObjectAttribute att) {
		List<Prediction> predictionsThatPredictEffectsForState = new ArrayList<Prediction>();

		// Found a failure condition for an action -- update as necessary
		if (classAndAttUnchanged(oldState, currentState, oClass, att)) {
			Condition failureHyp = oldState.toCondition();// new Condition(s, this.propFunsToUse);

			PaperAttributeActionTuple toHashBy = new PaperAttributeActionTuple(oClass, att, action);
			List<Condition> failureConditions = this.failureConditionsByActionOClassAtt.get(toHashBy);
			// Remove all conditions that are entailed by this states conditions
			List<Condition> hypsToRemove = new ArrayList<Condition>();
			for (Condition hyp : failureConditions) {
				if (failureHyp.match(hyp)) {
					hypsToRemove.add(hyp);
				}
			}
			for (Condition hyp : hypsToRemove) {
				failureConditions.remove(hyp);
			}

			// Add this condition to the set of failure conditions
			failureConditions.add(failureHyp);
		}
		// Wasn't a failure condition -- update learners
		else {
			// Get possible effects for this oClass and attribute
			List<Effect> possibleEffects = null;
			// try {
			// for (EffectType efftype : this.effectsToUse) {
			possibleEffects = Effect.possibleEffectsExplanation(oldState, currentState, oClass, att, this.effectsToUse);
			// }
			// } catch (EffectNotFoundException e) {
			// e.printStackTrace();
			// }
			//
			for (Effect observedEffect : possibleEffects) {
				AttActionEffectTuple toHashBy = new AttActionEffectTuple(oClass, att, action, observedEffect.type);
				List<Prediction> relevantPredictions = this.predictionsByAttActionAndEffect.get(toHashBy);

				// This effect was ruled out so continue
				if (relevantPredictions == null) {
					continue;
				}

				// If already a prediction for this, update the condition and verify that there
				// are no overlaps
				Prediction prediction = predThatPredictsThisEffect(relevantPredictions, observedEffect);
				if (prediction != null) {

					prediction.updateConditionLearners(oldState, currentState, true);

					// Verify that there are no overlaps
					if (this.predictionsOverlap(prediction, relevantPredictions)) {
						relevantPredictions = null;
					}
				} // If we observed an effect that we had no prediction for then add this
					// prediction and update the learner for it
				else {
					prediction = new Prediction(this.propFunsToUse, oClass, att, action, observedEffect, oldState,
							this.statePerceptionToUse);
					relevantPredictions.add(prediction);

					// Make sure no overlap with an existing prediction of this type
					if (this.predictionsOverlap(prediction, relevantPredictions)) {
						relevantPredictions = null;
					}
					// make sure there aren't more than K predictions
					else if (relevantPredictions.size() > this.k) {
						relevantPredictions = null;
					}
				}

				predictionsThatPredictEffectsForState.add(prediction);

				this.predictionsByAttActionAndEffect.put(toHashBy, relevantPredictions);
			}

		}
		// Lastly update false conditions for all other conditionlearners -- for classic
		// DOORMAX this will do nothing
		List<Prediction> allPredictions = this.getAllPredictionsByClassEtc(oClass, att, action);
		for (Prediction currPred : allPredictions) {
			if (!predictionsThatPredictEffectsForState.contains(currPred)) {
				currPred.updateConditionLearners(oldState, currentState, false);
			}
		}

	}

	public List<Prediction> getAllPredictions() {

		List<Prediction> allPredictions = new ArrayList<Prediction>();
		for (List<Prediction> preds : this.predictionsByAttActionAndEffect.values()) {
			if (preds != null) {
				allPredictions.addAll(preds);
			}
		}
		return allPredictions;
	}

	private List<Prediction> getAllPredictionsByClassEtc(ObjectClass oClass, ObjectAttribute att, Action action) {
		List<Prediction> allPredictions = new ArrayList<Prediction>();
		for (EffectType eTypeString : this.effectsToUse) {
			AttActionEffectTuple toHashBy = new AttActionEffectTuple(oClass, att, action, eTypeString);
			List<Prediction> relevantPredictions = this.predictionsByAttActionAndEffect.get(toHashBy);
			if (relevantPredictions != null) {
				allPredictions.addAll(relevantPredictions);
			}
		}
		return allPredictions;
	}

	private boolean effectIsIncompatible(List<Effect> effects, OOMDPState state, Effect eToTest) {
		DiscreteStateHashFactory hf = new DiscreteStateHashFactory();
		for (Effect e : effects) {
			// Act on same object
			if (e.sameClassAndAttribute(eToTest)) {
				// And would cause object to have different values
				if (!hf.hashState(e.applyEffect(state)).equals(hf.hashState(eToTest.applyEffect(state)))) {
					return true;
				}
			}
		}
		return false;

	}

	/**
	 * 
	 * @param s
	 *            state to check if a no op for
	 * @param a
	 *            action to check for no op for on s
	 * @return a boolean of if a will be a no op on s
	 */
	private boolean aNoOpCondition(OOMDPState s, Action a, ObjectClass oClass, ObjectAttribute att) {
		PaperAttributeActionTuple toHashBy = new PaperAttributeActionTuple(oClass, att, a);
		List<Condition> failureConditions = this.failureConditionsByActionOClassAtt.get(toHashBy);
		Condition currStateCondHyp = new Condition(s.toCondition());
		for (Condition currHyp : failureConditions) {
			if (currHyp.match(currStateCondHyp)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * @param s
	 *            state to predict on
	 * @param a
	 *            action whose result we want to predict on s
	 * @return the state predicted or null if not known
	 */
	public OOMDPState predict(OOMDPState s, Action a) {
		List<Effect> predEffects = predictEffects(s, a);
		if (predEffects == null)
			return null;
		return applyEffects(predEffects, s);
	}

	public List<Effect> predictEffectsOnAttAndOclass(OOMDPState s, Action a, ObjectClass oClass, ObjectAttribute att) {
		List<Effect> predictedEffects = new ArrayList<Effect>();
		// Check for no ops
		if (aNoOpCondition(s, a, oClass, att)) {
			return predictedEffects;
		}

		// It's not a known no op condition
		for (EffectType effectType : this.effectsToUse) {
			AttActionEffectTuple toHashBy = new AttActionEffectTuple(oClass, att, a, effectType);

			List<Prediction> relevantPredictions = this.predictionsByAttActionAndEffect.get(toHashBy);

			// Effect type ruled out so continue
			if (relevantPredictions == null)
				continue;

			// Add relevant predictions
			for (Prediction pred : relevantPredictions) {
				Effect predictedEffect = pred.predictResultingEffect(s);

				// If prediction doesn't know, return don't know
				if (predictedEffect == null)
					return null;

				// If prediction is non-null (i.e. condition learner predicted true)
				if (!(predictedEffect instanceof NullEffect)) {

					// If prediction is incompatible with one of the effects return don't know
					if (effectIsIncompatible(predictedEffects, s, predictedEffect)) {
						return null;
					}

					// If prediction's effect is redundant skip it
					if (effectIsRedundant(predictedEffects, predictedEffect)) {
						continue;
					}

					// Add predicted effect
					predictedEffects.add(predictedEffect);

				}
			}

		}
		// If no effects then return don't know
		if (predictedEffects.size() == 0)
			return null;

		return predictedEffects;
	}

	public List<Effect> predictEffects(OOMDPState s, Action ga) {
		List<Effect> toReturn = new ArrayList<Effect>();

		for (ObjectClass oClass : this.oomdp.getObjectClasses()) {
			for (ObjectAttribute att : oClass.attributes) {
				List<Effect> effects = predictEffectsOnAttAndOclass(s, ga, oClass, att);
				if (effects == null) {
					return null;
				}
				toReturn.addAll(effects);
			}
		}
		return toReturn;
	}

	/**
	 * Assumes none of the effects are incompatible
	 * 
	 * @param effects
	 * @param eToTest
	 * @return
	 */
	private boolean effectIsRedundant(List<Effect> effects, Effect eToTest) {

		for (Effect e : effects) {
			if (effects.indexOf(e) != effects.indexOf(eToTest) && e.sameClassAndAttribute(eToTest)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Note: assumes compatible effects
	 * 
	 * @param effects
	 *            the effects to apply
	 * @param s
	 *            state to apply effects to
	 * @return a state with each unique effect applied
	 */
	private OOMDPState applyEffects(List<Effect> effects, OOMDPState s) {
		OOMDPState toReturn = new OOMDPState(s);

		// Apply effects
		for (Effect e : effects) {
			toReturn = e.applyEffect(toReturn);
		}
		return toReturn;
	}

	public List<BeliefAboutAction> toBeliefs() {
		Map<PaperAttributeActionTuple, BeliefAboutAction> beliefs = new HashMap<PaperAttributeActionTuple, BeliefAboutAction>();

		// add predictions
		for (List<Prediction> preds : this.predictionsByAttActionAndEffect.values()) {
			if (preds != null) {
				for (Prediction pred : preds) {
					PaperAttributeActionTuple key = new PaperAttributeActionTuple(pred.associatedOClass,
							pred.relevantAtt, pred.action);
					if (beliefs.get(key) == null) {
						BeliefAboutAction belief = new BeliefAboutAction(pred.action, this.agentName, pred.relevantAtt, pred.associatedOClass);
						beliefs.put(key, belief);
					}

					beliefs.get(key).addPrediction(pred);
				}
			}
		}

		// Failure conditions
		for (Action a : this.actionList) {
			for (ObjectClass oClass : this.oomdp.getObjectClasses()) {
				for (ObjectAttribute att : oClass.attributes) {
					PaperAttributeActionTuple toHashBy = new PaperAttributeActionTuple(oClass, att, a);
					List<Condition> failureConditionsForAction = this.failureConditionsByActionOClassAtt.get(toHashBy);
					if (failureConditionsForAction.size() == 0)
						continue;

					PaperAttributeActionTuple key = new PaperAttributeActionTuple(oClass, att, a);
					if (beliefs.get(key) == null) {
						BeliefAboutAction belief = new BeliefAboutAction(a, this.agentName, att, oClass);
						beliefs.put(key, belief);
					}

					for (Condition cond : failureConditionsForAction) {
						beliefs.get(key).addFailureCondition(cond);
					}
				}
			}
		}

		return new ArrayList<BeliefAboutAction>(beliefs.values());
	}

	@Override
	public String toString() {
		// Predictions in effect
		StringBuilder toReturn = new StringBuilder("predictionsLearner:\n");

		for (List<Prediction> preds : this.predictionsByAttActionAndEffect.values()) {
			if (preds != null) {
				for (Prediction pred : preds) {
					toReturn.append(pred + "\n");
				}
			}
		}

		// Failure conditions
		for (Action a : this.actionList) {
			for (ObjectClass oClass : this.oomdp.getObjectClasses()) {
				for (ObjectAttribute att : oClass.attributes) {
					PaperAttributeActionTuple toHashBy = new PaperAttributeActionTuple(oClass, att, a);
					List<Condition> failureConditionsForAction = this.failureConditionsByActionOClassAtt.get(toHashBy);
					if (failureConditionsForAction.size() == 0)
						continue;
					toReturn.append("\tFailure condition for " + a + " on " + oClass.name + "'s " + att.name + ": ");

					for (Condition cond : failureConditionsForAction) {
						toReturn.append(cond);

					}
					toReturn.append("\n");
				}
			}

			toReturn.append("\n");

		}

		return toReturn.toString();
	}
	
	public List<PrevisionBelief> getAllPrevisions(OOMDPState state){
		List<PrevisionBelief> previsions = new ArrayList<PrevisionBelief>();
		
		for (Action action: actionList) {
			previsions.add(new PrevisionBelief(predictEffects(state, action), action));
		}
		
		return previsions;
	}

}
