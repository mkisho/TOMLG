package tomlg.doormax;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import tomlg.doormax.actionconditionlearners.OOMDPActionConditionLearner;
import tomlg.doormax.effects.EffectType;
import tomlg.doormax.environment.EnvironmentSimulator;
import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.OOMDP;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectClass;
import tomlg.doormax.oomdpformalism.ObjectInstance;
import tomlg.doormax.utils.TransitionProbability;

import java.util.ArrayList;
import java.util.List;

/**
 * A model to represent the model presented in "An Object-Oriented
 * Representation for Efficient Reinforcement Learning" by Diuk et al.
 * 
 * @author Dhershkowitz
 *
 */
public class Doormax {

	private PredictionsLearner pLearner;

	private OOMDP d;
	private int k;
	private List<PropositionalFunction> relevantPropFuns;
	private List<EffectType> effectsToUse;
	private final OOMDPState initialState;
	private String statePerceptionToUse;

	/**
	 * 
	 * @param d
	 *            domain to use
	 * @param rf
	 *            reward function to use
	 * @param tf
	 *            terminal function to use
	 * @param relevantPropFuns
	 *            propositional functions to plan over
	 * @param effectsToUse
	 *            list of strings of effects to plan over (documented as static
	 *            Strings in Effects.EffectHelpers)
	 * @param initialState
	 *            the initialState to get grounded actions from
	 * @param k
	 * @param statePerceptionToUse
	 *            string for how to featurize state for condition learner as gotten
	 *            from StatePerceptions. If null will run classic DOORMAX with PFs.
	 */
	public Doormax(OOMDP d, List<PropositionalFunction> relevantPropFuns, List<EffectType> effectsToUse,
			OOMDPState initialState, int k, String statePerceptionToUse) {
		this.d = d;
		this.k = k;
		this.statePerceptionToUse = statePerceptionToUse;
		this.initialState = initialState;
		this.relevantPropFuns = relevantPropFuns;
		this.pLearner = new PredictionsLearner(d, relevantPropFuns, effectsToUse, d.actions, this.initialState, this.k,
				this.statePerceptionToUse);
		this.effectsToUse = effectsToUse;
	}

	public PredictionsLearner getPredictionsLearner() {
		return this.pLearner;
	}

	public boolean transitionIsModeled(OOMDPState s, Action ga) {
		OOMDPState predictedState = this.pLearner.predict(s, ga);
		return (predictedState != null);
	}

	public boolean stateTransitionsAreModeled(OOMDPState s) {
		return true;
		// List<AbstractGroundedAction> unmodeledActions =
		// this.getUnmodeledActionsForState(s);
		// return (unmodeledActions.size() == 0);
	}

	public List<Action> getUnmodeledActionsForState(OOMDPState s) {
		List<Action> toReturn = new ArrayList<Action>();
		List<Action> actions = d.actions;
		for (Action a : actions) {
			if (!this.transitionIsModeled(s, a)) {
				toReturn.add(a);
			}
		}
		return toReturn;
	}
/*
	public OOMDPState sampleModelHelper(OOMDPState s, Action ga) {
		return this.sampleTransitionFromTransitionProbabilities(s, ga);
	}
*/
	public List<TransitionProbability> getTransitionProbabilities(OOMDPState s, Action ga) {
		List<TransitionProbability> toReturn = new ArrayList<TransitionProbability>();
		OOMDPState resultingState = this.pLearner.predict(s, ga);
		// Do know
		if (resultingState != null) {
			TransitionProbability TP = new TransitionProbability(resultingState, 1.0);
			toReturn.add(TP);
		}
		// If don't know just transition to original state
		else {
			TransitionProbability TP = new TransitionProbability(s, 1.0);
			toReturn.add(TP);
		}

		return toReturn;
	}

	public void updateModel(OOMDPState s, Action ga, OOMDPState sprime, double r, boolean sprimeIsTerminal) {
		this.pLearner.learn(s, ga, sprime);
	}

	public void resetModel() {
		this.pLearner = new PredictionsLearner(d, relevantPropFuns, effectsToUse, d.actions, this.initialState,
				this.k, this.statePerceptionToUse);
	}

	public OOMDPState getInitialState() {
		return this.initialState;
	}
}
