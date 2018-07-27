package tomlg.doormax.output;

import java.io.FileWriter;
import java.util.Map;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import tomlg.doormax.Condition;
import tomlg.doormax.Effect;
import tomlg.doormax.Prediction;
import tomlg.doormax.actionconditionlearners.PFConditionLearner;

/**
 * Classe que realiza a saída da execução do modelo para um arquivo.
 * 
 * @author chronius
 */
public class ReasoningXMLWriter {
	//
	private final String file;
	private XMLStreamWriter writer;

	public ReasoningXMLWriter(String file) {
		this.file = file;

		XMLOutputFactory factory = XMLOutputFactory.newInstance();

		try {
			this.writer = factory.createXMLStreamWriter(new FileWriter(this.file));

			writer.writeStartDocument();
			writer.writeStartElement("MindHistory");
		} catch (Exception e) {
			System.out.println("Falha ao criar arquivo mind history. File:\"" + this.file + "\"");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void writeReasoningStep(ReasoningMind mind) {
		final KnowledgeMemoryBase knowlodge = mind.getKnowlodgeMemoryBase();

		try {
			writer.writeStartElement("Mind");
			writer.writeAttribute("step", mind.getStep() + "");
			writer.writeAttribute("agent", mind.getAgentName());

			writer.writeStartElement("Beliefs");

			////////////////////////////////////////////////////// EnvironmentBeliefs
			////////////////////////////////////////////////////// /////////////////////////////////////////
			writer.writeStartElement("EnvironmentBeliefs");

			for (WorldBelief belief : knowlodge.getWorldBeliefs()) {
				if (belief.getType().equals("OBJECT")) {
					writer.writeStartElement("Obj");
					writer.writeAttribute("class", belief.getName());
					writer.writeAttribute("id", belief.getParams().get("id"));

					writer.writeStartElement("Attributes");
					for (Map.Entry<String, String> entry : belief.getParams().entrySet()) {
						writer.writeEmptyElement("Att");
						writer.writeAttribute("name", entry.getKey());
						writer.writeAttribute("value", entry.getValue());

						// writer.writeEndElement(); // close Att tag
					}
					writer.writeEndElement(); // close Attributes tag

					writer.writeEndElement(); // close Obj tag
				} else if (belief.getType().equals("PF")) {
					writer.writeEmptyElement("PF");
					writer.writeAttribute("name", belief.getName());
					writer.writeAttribute("value", belief.getParams().get("value"));
					// writer.writeEndElement(); // close PF tag
				}
			}
			writer.writeEndElement(); // close EnvironmentBeliefs tag

			////////////////////////////////////////////////////// EffectBelief
			////////////////////////////////////////////////////// /////////////////////////////////////////
			writer.writeStartElement("EffectsBelief");
			for (BeliefAboutAction beliefEffect : knowlodge.getActionBeliefs()) {
				writer.writeStartElement("ActionEffect");
				writer.writeAttribute("action", beliefEffect.getAction().name);
				writer.writeAttribute("agent", beliefEffect.getAgent());
				writer.writeAttribute("oclass",
						(beliefEffect.getoClass() != null ? beliefEffect.getoClass().name : ""));
				writer.writeAttribute("attribute", beliefEffect.getAttribute().name);

				writer.writeStartElement("Predictions");
				for (Prediction pred : beliefEffect.getPredictions()) {
					writer.writeEmptyElement("Effect");
					writer.writeAttribute("type", pred.effect.type.name());
					writer.writeAttribute("newvalue", pred.effect.value + "");

					if (((PFConditionLearner) pred.getConditionLearner()).getCondition() == null) {
						// System.out.println();
					} else
						writer.writeAttribute("condition",
								((PFConditionLearner) pred.getConditionLearner()).getCondition().conditionEvaluated);

				}
				writer.writeEndElement(); // close Predictions tag

				writer.writeStartElement("FailureConditions");
				for (Condition cond : beliefEffect.getFailureConditions()) {
					writer.writeEmptyElement("Condition");
					writer.writeAttribute("condition", cond.conditionEvaluated);

				}
				writer.writeEndElement(); // close FailureConditions tag

				writer.writeEndElement(); // close ActionEffect tag
			}

			writer.writeEndElement(); // close EffectsBelief tag

			////////////////////////////////////////////////////// PredictionBelief
			writer.writeStartElement("Previsions");

			for (PrevisionBelief prev : knowlodge.getPrevisionsBeliefs()) {
				writer.writeStartElement("Prevision");
				writer.writeAttribute("action", prev.getAction().name);

				if (prev.getPredictions() != null)
					for (Effect effect : prev.getPredictions()) {
						writer.writeEmptyElement("Effect");
						writer.writeAttribute("type", effect.type.name());
						writer.writeAttribute("newvalue", effect.value + "");

					}
				writer.writeEndElement();// close Prevision tag

			}
			writer.writeEndElement(); // close Previsions tag

			////////////////////////////////////////////////////// ///////////////////////////////////////
			writer.writeEndElement(); // close beliefs tag
			writer.writeEndElement(); // close mind tag

			writer.flush();
		} catch (Exception e) {
			System.out.println("Falha na escrita do mind history. File:\"" + this.file + "\"");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void finish() {
		try {
			writer.writeEndElement();
			writer.writeEndDocument();
			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.out.println("Falha na escrita do mind history. File:\"" + this.file + "\"");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void endExperiment(ReasoningMind reasoningMind) {
		this.writeReasoningStep(reasoningMind);
		finish();
	}
}
