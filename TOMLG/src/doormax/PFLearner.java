package doormax;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import doormax.structures.Action;
import doormax.structures.Condition;
import doormax.structures.Effect;
import doormax.structures.Prediction;
import doormax.structures.attribute.Attribute;

/**
 * Aprende para uma classe específica e 1 attributo específico e uma ação
 * específica
 * 
 * @author chronius
 *
 */
public class PFLearner {
	private static int K = 100;

	private ObjectClass objClass;
	private Attribute attribute;
	private Action action;

	// Em que condições a ação não possui efeito sobre o atributo
	private Set<Condition> failureConditions;

	private Set<Prediction> predictions;

	private Set<Prediction> contraditions;

	public PFLearner(ObjectClass objClass, Attribute attribute, Action action) {
		super();
		this.objClass = objClass;
		this.attribute = attribute;
		this.action = action;

		this.failureConditions = new HashSet<>();
		this.predictions = new HashSet<>();
		this.contraditions = new HashSet<>();
	}

	public void learn(Attribute attOld, Attribute attNew, Condition condition) {
		if (this.action.getName().equals("taxiMoveNorth") && this.attribute.getName().equals("yLocation")
				&& this.objClass.getName().equals("taxi"))
			System.out.println("waith");

		if (attOld.sameValue(attNew)) {
			// o valor não mudou, então adicione nas condições de falha
			failureConditions.add(condition);
			// TODO não é possível combinar as condições nesse caso?
		} else {
			List<Effect> hypotesis = Effect.possibleEffectsExplanation(attOld, attNew, this.objClass);

			for (Effect hypEffect : hypotesis) {
				Prediction toUpdatePrediction = getSameEffectPrediction(hypEffect);

				if (toUpdatePrediction != null) {
					// nesse caso já existe uma predição com o mesmo efeito, então atualiza-la
					toUpdatePrediction.updateCondition(condition);

					List<Prediction> relatedPrediction = getRelatedPredictions(hypEffect);
					relatedPrediction.remove(toUpdatePrediction);

					boolean remove = false;
					for (Prediction pred : relatedPrediction) {
						assert (!pred.equals(toUpdatePrediction));
						if (pred.matches(toUpdatePrediction)) {
							this.predictions.remove(pred);
							this.contraditions.add(pred);
							remove = true;
						}
					}
					if (remove)
						this.predictions.remove(toUpdatePrediction);
				} else {// nesse caso tentar criar uma nova predição
						// verificar se já existe um efeito do mesmo tipo em predictions!
					if (checkSameTypeExists(this.predictions, hypEffect)) {
//						Prediction prediction = new Prediction(this.action, condition, hypEffect);
//						this.contraditions.add(prediction);
						// Rule out predictions if more than k related prediction
						List<Prediction> relatedPrediction = getRelatedPredictions(hypEffect);
						assert(relatedPrediction.size() > 0);
						this.predictions.removeAll(relatedPrediction);
						
						for(Prediction pred:relatedPrediction) {
							System.out.println("Ading pred to >>>>>"+pred);

							this.contraditions.add(pred);
						
						}
						assert(this.contraditions.size() > 0);
//						System.exit(-1);
						if (relatedPrediction.size() > K) {
							this.contraditions.clear();
							System.out.println("Estouro");
							assert (true == false);

						}
					} else if (checkSameTypeExists(this.contraditions, hypEffect)) {//TODO checar se esse if precisa de alguma operação
					} else {
						// se não existe um efeito do mesmo tipo nas contradições, então criar um novo.
						Prediction prediction = new Prediction(this.action, condition, hypEffect);
						this.predictions.add(prediction);
					}
				}
			}
		}
	}

	private boolean checkSameTypeExists(Set<Prediction> setPred, Effect hypEffect) {
		for (Prediction pred : setPred) {
			if (pred.getEffect().getType().equals(hypEffect.getType()))
				return true;
		}
		return false;
	}

	private Prediction getSameEffectPrediction(Effect effect) {
		for (Prediction pred : this.predictions) {
			if (pred.getEffect().equals(effect))
				return pred;
		}
		return null;
	}

	private List<Prediction> getRelatedPredictions(Effect e) {
		List<Prediction> related = new ArrayList<>();
		for (Prediction pred : this.predictions) {
			if (pred.getEffect().getType().equals(e.getType())) {
				related.add(pred);
			}
		}
		return related;
	}

	@Override
	public String toString() {
		return "PFLearner [" + objClass.getName() + ", attribute=" + attribute.getName() + ", action=" + action
				+ "\n>>>>>>>>>>>> predictions=" + predictions + ", failureConditions=" + failureConditions + ", contraditions="
				+ contraditions + "]";
	}

	private boolean checkForContradictoryEffects(Effect effect, List<Effect> maching, OOMDPState state) {
		for (Effect e : maching) {
			if (effect.contradicts(e, state)) {
				return true;
			}
		}
		return false;
	}

	private List<Effect> getMatchingNonContradictoryPredictions(Condition condition, OOMDPState state, Set<Prediction> setPpred) {
		List<Effect> maching = new ArrayList<Effect>();
		for (Prediction pred : setPpred) {
			if (pred.matches(condition)) {
				if (checkForContradictoryEffects(pred.getEffect(), maching, state)) {
					System.out.println("Incompatible effects. Can not predict result yet");
					return null;
				} else {
//					System.out.println("effect " + pred.getEffect() + " added to pool");
					maching.add(pred.getEffect());
				}
			}
		}
		return maching;
	}

	public List<Effect> predict(Condition condition, OOMDPState state, Action action) {
		// check for failure condition
		for (Condition cond : this.failureConditions) {
			if (condition.equals(cond)) { // TODO check if can combine failure conditions
				return new ArrayList<Effect>();
			}
		}

		List<Effect> effects = getMatchingNonContradictoryPredictions(condition, state, this.predictions);

		if (effects == null) {
			return null;
		} else if (!effects.isEmpty()) {
			return effects;
		} else {
			return null;
		}
	}

}
