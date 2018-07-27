package tomlg.doormax;

import tomlg.doormax.effects.EffectType;
import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.OOMDP;
import tomlg.doormax.oomdpformalism.OOMDPState;
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
	 

	private OOMDP oomdp;
	private int k;
	private PropositionalFunction[] relevantPropFuns;
	private EffectType[] effectsToUse;
	private final OOMDPState initialState;
	private String statePerceptionToUse;
	private String agentName;


	/**
	 * 
	 * @param oomdp
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
	public Doormax(OOMDP oomdp, PropositionalFunction[] relevantPropFuns, EffectType[] effectsToUse,
			OOMDPState initialState, int k, String statePerceptionToUse, String agentName) {
		this.oomdp = oomdp;
		this.k = k;
		this.statePerceptionToUse = statePerceptionToUse;
		this.initialState = initialState;
		this.relevantPropFuns = relevantPropFuns;
		this.pLearner = new PredictionsLearner(oomdp, relevantPropFuns, effectsToUse, oomdp.actions, this.initialState, this.k,
				this.statePerceptionToUse, agentName);
		this.effectsToUse = effectsToUse;
		this.agentName = agentName;
	}

	public PredictionsLearner getPredictionsLearner() {
		return this.pLearner;
	}

	public boolean transitionIsModeled(OOMDPState state, Action action) {
		OOMDPState predictedState = this.pLearner.predict(state, action);
		return (predictedState != null);
	}

	public boolean stateTransitionsAreModeled(OOMDPState state) {
		 List<Action> unmodeledActions =
		 this.getUnmodeledActionsForState(state);
		 return (unmodeledActions.size() == 0);
	}

	public List<Action> getUnmodeledActionsForState(OOMDPState state) {
		List<Action> toReturn = new ArrayList<Action>();
		List<Action> actions = oomdp.actions;
		for (Action a : actions) {
			if (!this.transitionIsModeled(state, a)) {
				toReturn.add(a);
			}
		}
		return toReturn;
	}


	public void updateModel(OOMDPState oldState, Action action, OOMDPState newState, double reward, boolean newStateIsTerminal) {
		this.pLearner.learn(oldState, action, newState);
	}

	public void resetModel() {
		this.pLearner = new PredictionsLearner(oomdp, relevantPropFuns, effectsToUse, oomdp.actions, this.initialState,
				this.k, this.statePerceptionToUse, this.agentName);
	}

	public OOMDPState getInitialState() {
		return this.initialState;
	}
}
