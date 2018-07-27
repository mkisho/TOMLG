package tomlg.doormax.output;

import java.util.List;

import tomlg.doormax.Effect;
import tomlg.doormax.Prediction;
import tomlg.doormax.oomdpformalism.Action;

public class PrevisionBelief extends Belief {
	private List<Effect> predictions;
	private Action action;

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public List<Effect> getPredictions() {
		return predictions;
	}

	public void setPredictions(List<Effect> predictions) {
		this.predictions = predictions;
	}

	public PrevisionBelief(List<Effect> list, Action action) {
		super();
		this.predictions = list;
		this.action = action;
	}
}
