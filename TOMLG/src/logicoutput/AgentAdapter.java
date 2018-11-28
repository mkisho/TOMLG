package logicoutput;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.Configuration;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import doormax.DOORMax;
import doormax.ObjectInstance;
import doormax.PropositionalFunction;
import doormax.structures.Condition;
import doormax.structures.attribute.Attribute;
import doormax.structures.attribute.AttributeBoolean;
import doormax.structures.attribute.AttributeInteger;
import taxi.Configurations;
import tomlg.Agent;
import tomlg.Intention;

public class AgentAdapter extends TypeAdapter<Agent> {

	@Override
	public void write(JsonWriter writer, Agent agent) throws IOException {
		writer.beginObject();

		///////////// crenças perceptuais do agente
		final Condition condition = agent.getPerceptionsLowLevel();
		writer.name("PerceptionsLowLevel");
		writer.beginArray();
		for (final PropositionalFunction pf : condition.getPfIndex()) {
			String negation = (condition.getEvalOfPropositionalFunction(pf) ? "" : "\\neg ");
			writer.value(negation + pf.name);
		}
		writer.endArray();

		writer.name("PerceptionsHighLevel"); // Para as crenças de posição dos objetos na tela

		writer.beginArray();
		for (final ObjectInstance objInstance : agent.getPercetionHighLevel().getObjects()) {
			final String objClassName = objInstance.getOclass().getName().equals(Configurations.TAXI_CLASS_NAME)
					? "Myself"
					: objInstance.getOclass().getName();

			List<String> attributes = new ArrayList<String>(objInstance.getAttributes().size());
			for (final Attribute att : objInstance.getAttributes()) {
				if (att instanceof AttributeInteger) {
					attributes.add(att.getName() + ":" + att.getDoubleValue().intValue());
				} else if (att instanceof AttributeBoolean) {
					if (att.getDoubleValue().intValue() > 0) {
						attributes.add(att.getName());
					} else
						attributes.add("\\neg " + att.getName());
				} else
					System.exit(-1);

			}
			writer.value(MessageFormat.format("See({0}({1}))", objClassName, String.join(", ", attributes)));
		}
		writer.endArray();
		//////////// Crenças de Efeitos
		writer.name("Effects");
		new DoormaxAdapter().write(writer, agent.getMind().getAgentLearning());
		/////////// Crenças de Goals
		writer.name("goals");
		new MindAdapter().write(writer, agent.getMind());
		
		/////////// Crenças de Planning

		////////// Crenças de Execução
		
		writer.endObject();
	}

	@Override
	public Agent read(JsonReader in) throws IOException {
		// só é necessário implementar o write nesse projeto
		return null;
	}

}
