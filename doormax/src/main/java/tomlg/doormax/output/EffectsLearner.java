package tomlg.doormax.output;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tomlg.doormax.Doormax;
import tomlg.doormax.PropositionalFunction;
import tomlg.doormax.effects.EffectType;
import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.OOMDP;
import tomlg.doormax.oomdpformalism.OOMDPState;

/**
 * Cria e controla instâncias do Doormax utilizado no aprendizado de pares
 * ação/efeito
 * 
 * @author chronius
 *
 */
public class EffectsLearner {
	private Doormax mindLearner;

	// this.doormaxInstance = new Doormax(this.oomdp, pfs, effectsToUse,
	// this.currentState, 100, null);

	public EffectsLearner(OOMDP oomdp, OOMDPState initialState, EffectType[] effectsToUse,
			PropositionalFunction[] pfss) {
		this.mindLearner = new Doormax(oomdp, pfss, effectsToUse, initialState, 1000, null, "Taxi");
	}

	// public void updateActionEffect(Action action, ) {
	// if (!this.doormaxInstance.transitionIsModeled(this.currentState, action)) {
	// this.doormaxInstance.updateModel(this.currentState, action, nextState, -1,
	// false);
	// if (this.doormaxInstance.transitionIsModeled(this.currentState, action)) {
	// // doormax.modelPlanner.modelChanged(curState);
	// // policy = new DomainMappedPolicy(domain,
	// // this.modelPlanner.modelPlannedPolicy());
	// // policy = this.createDomainMappedPolicy();
	// }
	// }
	// }

	public boolean isPredictionModeled(OOMDPState state, Action action) {
		return this.mindLearner.transitionIsModeled(state, action);
	}

	public void learnPrediction(OOMDPState previousState, OOMDPState currentState, Action action) {
		this.mindLearner.updateModel(previousState, action, currentState, -1, false);
	}

	public List<BeliefAboutAction> getModeledBeliefs() {
		// List<Belief> beliefs = new ArrayList<Belief>();
		return this.mindLearner.getPredictionsLearner().toBeliefs();
		// return beliefs;
	}

	public List<PrevisionBelief> getPredictionBeliefs(OOMDPState state) {
		return this.mindLearner.getPredictionsLearner().getAllPrevisions(state);
	}
}
