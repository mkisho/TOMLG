package doormax;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import doormax.structures.Action;
import doormax.structures.Attribute;
import doormax.structures.Condition;
import doormax.structures.Effect;
import doormax.structures.Prediction;

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
						// verificar se já existe um efeito do mesmo tipo em w!
					if (checkSameTypeExists(this.contraditions, hypEffect)) {
						// se não existe um efeito do mesmo tipo, então criar um novo.
						Prediction prediction = new Prediction(this.action, condition, hypEffect);
						this.predictions.add(prediction);

						// Rule out predictions if more than k related predictions
						List<Prediction> relatedPrediction = getRelatedPredictions(hypEffect);
						if (relatedPrediction.size() > K) {
							this.contraditions.add(prediction);
							this.predictions.removeAll(relatedPrediction);
						}
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
		return "PFLearner [objClass=" + objClass + ", attribute=" + attribute + ", action=" + action
				+ ", failureConditions=" + failureConditions + ", predictions=" + predictions + ", contraditions="
				+ contraditions + "]";
	}
	
	

}
