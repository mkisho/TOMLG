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

	public final FailureConditionsSet f;

	// body of predictions
	public final AlphaPredictionsSet α;

	// for the contradictory (effect types, object class, attribute, action) tuples
	public final ContradictoryWSet ω;

	public DoormaxState(final OOMDP oomdp) {
		this.f = new FailureConditionsSet(oomdp);
		this.α = new AlphaPredictionsSet();
		this.ω = new ContradictoryWSet();
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
					this.doormaxState.f.add(action, att, objInstance[0].objectClass, s0.toCondition());
				} else
					// se não for uma condição de falha, então atualizar as condições para as
					// predições que predizem os efeitos apropriados
					// através da operação bitwise entre a condição do espaço s'.
					// Se não existirem predições, então inicializar uma nova predição com o efeito
					// correspondente e a representação da condição de s
					// para o novo preditor.

					for (Effect hypEffect : Effect.possibleEffectsExplanation(objInstance[0], objInstance[1], att)) {
						// Check for existing predictions to update
						Map<Effect, Prediction> preds = this.doormaxState.α.checkExistingPrediction(hypEffect, action);
						Prediction existingPrediction = (Prediction) preds.get(hypEffect);
						if (existingPrediction != null) {
							// update conditions
							Prediction updatePrediction = existingPrediction.bitwise(s0.toCondition());

							preds.remove(hypEffect);

							for (Effect effect : preds.keySet()) {
								// prediction overlaps
								if (effect.type.equals(hypEffect.type)
										&& preds.get(effect).condition.match(updatePrediction.condition)) {
									this.doormaxState.ω.add(action, hypEffect, preds.get(effect));
									preds.remove(effect);
								}
							}
							preds.put(hypEffect, updatePrediction);
						} else {
							// We observed an effect for which we had
							// no prediction. If its condition does not overlap
							// an existing condition, then add this new
							// prediction.

							Map<Effect, Prediction> ωpreds = this.doormaxState.ω.search(action, hypEffect.type, att,
									objInstance[0].objectClass);
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

	/**
	 * Para cada ação a, faz uma previsão do estado s' da aplicação de a em s
	 */
	private void predict(OOMDPState s0) {
		PrevisionModel T = new PrevisionModel();
		for (Action a: oomdp.actions) {
			Map<Effect, Prediction> totalPool = new HashMap<Effect, Prediction>();
			for(ObjectClass objectClass: oomdp.objectClasses) {
				Map<Effect, Prediction> currentPool = new HashMap<Effect, Prediction>();
				Set<Effect> currentPoolSet = new HashSet<Effect>();
				for(EffectType eType: Effect.γ) {
					for (ObjectAttribute att: objectClass.attributes) {
						Map<Effect, Prediction> t = this.doormaxState.α.relatedOverlap(
								eType, objectClass, att, s0.toCondition());
						for (Effect e1: t.keySet()) {
							for(Effect e2: t.keySet())
								if(e1.contradicts(e2)) {
									T.add(s0, a, null, 1);
									break endFor;
								}
						}
						
						for (Effect e1: t.keySet()) {
							if(e1.contradicts(currentPoolSet)) {
								T.add(s0, a, null, 1);
								break endFor;
							}
						}
						
						currentPool.putAll(t);
						currentPoolSet.addAll(t.keySet());
						//check if is correct
						if (currentPool.isEmpty() && 
								this.doormaxState.ω.search(a, eType, att, objectClass)!= null){
							T.add(s0, a, null, 1);
							break endFor;
						}
					}
					totalPool.putAll(currentPool);
				}
			/*	
				*/
			}
			
			//Apply all predicted effects
			/*			resultingState ← s
						for all effect ∈ totalPool do
						resultingState ← effect(resultingState)
						end for
						T (s, a, resultingState) = 1
				*/		
			endFor:
				System.out.println("Erle");
		}
		
		
	}
}