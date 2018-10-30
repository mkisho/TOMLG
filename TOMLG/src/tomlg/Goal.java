package tomlg;

import java.io.Serializable;
import java.util.List;

import doormax.structures.Action;
import doormax.structures.Effect;

public class Goal implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String motivation; // TODO FIX FIX FIX

	private Action action;
	private List<Effect> effects;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMotivation() {
		return motivation;
	}

	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Goal [name=");
		builder.append(name);
		builder.append(", motivation=");
		builder.append(motivation);
		builder.append("]");
		return builder.toString();
	}

	public Goal(String name, String motivation) {
		super();
		this.name = name;
		this.motivation = motivation;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public List<Effect> getEffects() {
		return effects;
	}

	public void setEffects(List<Effect> effects) {
		this.effects = effects;
	}
}
