package logicoutput;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import doormax.structures.Effect;
import tomlg.Goal;
import tomlg.Intention;
import tomlg.Mind;

public class MindAdapter extends TypeAdapter<Mind> {
	private String intentionGen(String type, Goal goal) {
		List<String> effects = new ArrayList<String>(goal.getEffects().size());
		for (Effect e : goal.getEffects()) {
			effects.add(e.toLogicFormat());
		}

		String str = type + "_{me}<me:" + goal.getAction().getName() + ">(" + String.join("\\land ", effects) + ")";
		str += " \\ddagger" + goal.getMotivation() + " \\ddager";
		return str;
	}

	@Override
	public void write(JsonWriter writer, Mind mind) throws IOException {
		writer.beginObject();
		///////////////////// Achievement Goals
		if (!mind.getAchievementGoals().isEmpty()) {
			writer.name("AchivementGoals");
			writer.beginArray();
			for (Goal goal : mind.getAchievementGoals()) {
				writer.value(intentionGen("AGoal", goal));
			}
			writer.endArray();
		}

		///////////////////////////// FDI
		if (mind.getChoosenFDI() != null) {
			writer.name("FDI");
			writer.value(intentionGen("FDI", mind.getChoosenFDI()));
		}

		// goal -> present directed intention
		Intention intention = mind.getLastIntention();
		if (intention != null) {
			writer.name("intention");
			intention.getAction();
			writer.value("Goal_{me}<<me:" + intention.getAction() + ">>\\top" + " \\ddagger"
					+ intention.getGoal().getName() + " - " + intention.getGoal().getMotivation() + " \\ddager");
		}

		writer.endObject();
	}

	@Override
	public Mind read(JsonReader in) throws IOException {
		// não necessário implementar
		return null;
	}

}
