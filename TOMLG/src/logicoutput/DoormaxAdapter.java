package logicoutput;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import doormax.DOORMax;
import doormax.Learner;
import doormax.PFLearner;
import doormax.PropositionalFunction;
import doormax.structures.Condition;
import doormax.structures.Prediction;

public class DoormaxAdapter extends TypeAdapter<DOORMax> {

	@Override
	public void write(JsonWriter writer, DOORMax doormax) throws IOException {
		writer.beginObject();
		writer.name("Effects");
		writer.beginArray();

		for (final Learner learner : doormax.getLearners()) {
			for (final PFLearner pfLearner : learner.getPFLearners()) {
				///////////////////////////// positive effects
				for (final Prediction pred : pfLearner.getPredictions()) {
					final String effect = MessageFormat.format("({0})", pred.getEffect().toLogicFormat());
					Condition condition = pred.getCondition();
					List<String> condEvaluated = new ArrayList<String>(condition.getPfIndex().length);

					for (final PropositionalFunction pf : condition.getPfIndex()) {
						if (condition.getEvalOfPropositionalFunctionComplete(pf) == '*')
							continue;
						String negation = (condition.getEvalOfPropositionalFunction(pf) ? "" : " \\neg ");
						condEvaluated.add(negation + pf.name);
					}
					String preCondition = String.join(" \\land ", condEvaluated);

					String text = MessageFormat.format("\\gamma^+({0}, {1}) \\to {2}", pred.getAction().getName(),
							effect, preCondition);

					writer.value(text);
				}
				//////////////////////// negative effects
				List<String> negativePreconditions = new ArrayList<String>(pfLearner.getFailureConditions().size());
				for (final Condition condition : pfLearner.getFailureConditions()) {

					List<String> condEvaluated = new ArrayList<String>(condition.getPfIndex().length);
					for (final PropositionalFunction pf : condition.getPfIndex()) {
						String negation = (condition.getEvalOfPropositionalFunction(pf) ? "" : "\\neg ");
						condEvaluated.add(negation + pf.name);
					}
					String preCondition = String.join(", ", condEvaluated);
					negativePreconditions.add(preCondition);
				}
				
				String text = MessageFormat.format("\\gamma^-({0}, {1}) \\to {2}", pfLearner.getAction().getName(),
						"( " + pfLearner.getObjClass().getName() + ", " + pfLearner.getAttribute().getName() + ")",
						String.join("\\lor", negativePreconditions));

//				writer.value(text);

				/// contradictory effects
			}
		}
		writer.endArray();
		writer.endObject();
	}

	@Override
	public DOORMax read(JsonReader in) throws IOException {
		// só é necessário implementar o write nesse projeto
		return null;
	}

}
