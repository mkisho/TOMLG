package tomlg.doormax.actionconditionlearners;

import tomlg.doormax.oomdpformalism.OOMDPState;

public abstract class OOMDPActionConditionLearner {
	public abstract void learn(OOMDPState s, boolean trueInState);

	public abstract Boolean predict(OOMDPState s);

	public abstract boolean conditionsOverlap(OOMDPActionConditionLearner otherLearner);
}
