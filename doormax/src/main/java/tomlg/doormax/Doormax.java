package tomlg.doormax;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import tomlg.doormax.effects.EffectType;
import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.OOMDP;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectClass;
import tomlg.doormax.oomdpformalism.ObjectInstance;
import tomlg.doormax.utils.Quadruple;

class DoormaxState {
	// conditions under which that action has been observed to not effect that
	// attribute of that object instance.
	// Each of these sets is essentially a tabular set of failure conditions for
	// when a does not change
	// att of any instances of oClass
	public final Map<FailureConditions, Set<Condition>> f;

	// body of predictions
	public final Map<Quadruple<Action, EffectType, ObjectAttribute, ObjectClass>, Map<Effect, Prediction>> α;

	// for the contradictory (effect types, object class, attribute, action) tuples
	public final Map<Quadruple<Action, EffectType, ObjectAttribute, ObjectClass>, Map<Effect, Prediction>> ω;

	public DoormaxState(final OOMDP oomdp) {
		// initializes f
		this.f = new HashMap<FailureConditions, Set<Condition>>();
		for (Action action : oomdp.actions) {
			for (ObjectClass objClass : oomdp.objectClasses) {
				for (ObjectAttribute attribute : objClass.attributes) {
					this.f.put(new FailureConditions(action, attribute, objClass), new HashSet<Condition>());
				}
			}
		}

		this.α = new HashMap<>();
		this.ω = new HashMap<>();
	}

	public void addPredictionToα(Action action, EffectType effectType, ObjectAttribute att, ObjectClass objClass,
			Effect effect, Prediction pred) {
		Map<Effect, Prediction> temp = new HashMap<>();
		this.α.put(new Quadruple<Action, EffectType, ObjectAttribute, ObjectClass>(action, effectType, att, objClass),
				temp);
	}

	public void addPredictionToω(Action action, EffectType effectType, ObjectAttribute att, ObjectClass objClass,
			Effect effect, Prediction pred) {
		Map<Effect, Prediction> temp = new HashMap<>();
		this.ω.put(new Quadruple<Action, EffectType, ObjectAttribute, ObjectClass>(action, effectType, att, objClass),
				temp);
	}

	public Map<Effect, Prediction> αPredictionSearch(Action action, EffectType effectType, ObjectAttribute att,
			ObjectClass objClass) {
		return this.α.get(
				new Quadruple<Action, EffectType, ObjectAttribute, ObjectClass>(action, effectType, att, objClass));
	}

	public Map<Effect, Prediction> ωPredictionSearch(Action action, EffectType effectType, ObjectAttribute att,
			ObjectClass objClass) {
		return this.ω.get(
				new Quadruple<Action, EffectType, ObjectAttribute, ObjectClass>(action, effectType, att, objClass));
	}
}

public class Doormax {
	public final OOMDP oomdp;
	private OOMDPState actualState;
	private final int maxEffects;
	private DoormaxState doormaxState;
	public final double epsilon;

	public Doormax(OOMDP oomdp, OOMDPState initialState, int maxEffects, double epsilon) {
		super();
		this.oomdp = oomdp;
		this.actualState = initialState;
		this.maxEffects = maxEffects;
		this.epsilon = epsilon;
		this.doormaxState = null;
		initialize();
	}

	/// TODO output effects space

	/**
	 * Initialize: - failure conditions F - prediction set α and ruled out
	 * prediction set ω
	 */
	private void initialize() {
		this.doormaxState = new DoormaxState(this.oomdp);
	}

	public void step() {
		// Doormax Prediction

		// upates pi/planner

		// nextAction← π(currentState)

		// newState ← nextAction(currentState)
		// DOORMAXLearn(currentState, nextAction, newState)
		// currentState ← newState
	}

	/**
	 * 
	 * @param s0
	 *            estado inicial
	 * @param a
	 *            ação tomada
	 * @param sf
	 *            espaço de estados quando a ação a foi tomada no estado s0
	 */
	private void learn(OOMDPState s0, Action action, OOMDPState sf) {

		// laço objetoi s x objetoi s'
		for (ObjectInstance[] objInstance : s0.matchObjects(sf)) {
			// para todos os attributos da classe do objeto_id
			for (ObjectAttribute att : objInstance[0].objectClass.attributes) {
				// Se os valores dos atributos de um objeto não mudaram nos
				// dois estados, então DOORMAX adiciona a representação de
				// condição de s a lista de condições falhas (action, objClass, att(objClass))

				// TODO colocar tratamento de exceção
				// a única forma de dar exceção é se um objeto foi criado
				// ou desapareceu em relação ao estado antigo
				if (objInstance[0].attributesVal.get(att) == objInstance[0].attributesVal.get(1)) {
					FailureConditions key = new FailureConditions(action, att, objInstance[0].objectClass);
					this.doormaxState.f.get(key).add(s0.toCondition());
				} else
					// se não for uma condição de falha, então atualizar as condições para as
					// predições que predizem os efeitos apropriados
					// através da operação bitwise entre a condição do espaço s'.
					// Se não existirem predições, então inicializar uma nova predição com o efeito
					// correspondente e a representação da condição de s
					// para o novo preditor.

					for (Effect hypEffect : Effect.possibleEffectsExplanation(objInstance[0], objInstance[1], att)) {
						// Check for existing predictions to update
						Map<Effect, Prediction> preds = this.doormaxState.αPredictionSearch(action, hypEffect.type,
								hypEffect.attribute, objInstance[0].objectClass);
						Prediction existingPrediction = (Prediction) preds.get(hypEffect);
						if (existingPrediction != null) {
							// update conditions
							Prediction updatePrediction = existingPrediction.bitwise(s0.toCondition());

							preds.remove(hypEffect);

							for (Effect effect : preds.keySet()) {
								// prediction overlaps
								if (effect.type.equals(hypEffect.type)
										&& preds.get(effect).condition.match(updatePrediction.condition)) {
									this.doormaxState.addPredictionToω(action, hypEffect.type, hypEffect.attribute,
											objInstance[0].objectClass, effect, preds.get(effect));
									preds.remove(effect);
								}
							}
							preds.put(hypEffect, updatePrediction);
						} else {
							// We observed an effect for which we had
							// no prediction. If its condition does not overlap
							// an existing condition, then add this new
							// prediction.

							Map<Effect, Prediction> ωpreds = this.doormaxState.ωPredictionSearch(action, hypEffect.type,
									att, objInstance[0].objectClass);
							boolean flag = false;
							for (Effect effect : ωpreds.keySet()) {
								final Condition c = ωpreds.get(effect).condition;
								if (c.match(s0.toCondition()) || s0.toCondition().match(c)) {
									ωpreds.remove(effect);
								}
							}

							if (!flag) {
								Prediction newPrediction = new Prediction(action, hypEffect, s0.toCondition());
								ωpreds.put(hypEffect, newPrediction);

								if (ωpreds.size() > this.maxEffects) {
									ωpreds.remove(newPrediction);
								}
							}
						}
					}
			}
		}
	}

	private void predict() {

	}
}

class FailureConditions {
	public final Action action;
	public final ObjectAttribute attribute;
	public final ObjectClass objClass;

	public FailureConditions(Action action, ObjectAttribute attribute, ObjectClass objClass) {
		super();
		this.action = action;
		this.attribute = attribute;
		this.objClass = objClass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + ((objClass == null) ? 0 : objClass.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FailureConditions other = (FailureConditions) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		if (objClass == null) {
			if (other.objClass != null)
				return false;
		} else if (!objClass.equals(other.objClass))
			return false;
		return true;
	}
}
