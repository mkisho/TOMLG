package tomlg.doormax.actionconditionlearners;

import tomlg.doormax.PropositionalFunction;
import tomlg.doormax.oomdpformalism.OOMDPState;

public class PerceptionConditionLearnerPF extends PropositionalFunction {

	PerceptionConditionLearner percCondLearner;

	public PerceptionConditionLearnerPF(String name, PerceptionConditionLearner percCondLearner) {
		super(name);
		this.percCondLearner = percCondLearner;
		this.percCondLearner.trainClassifier();
	}

	@Override
	public boolean evaluate(OOMDPState s) {
		return this.percCondLearner.predict(s);
	}

}
