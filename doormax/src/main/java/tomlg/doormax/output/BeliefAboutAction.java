package tomlg.doormax.output;

import java.util.ArrayList;
import java.util.List;

import tomlg.doormax.Condition;
import tomlg.doormax.Prediction;
import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectClass;

public class BeliefAboutAction extends Belief{
	private Action action;
	private String agent;
	
	private ObjectAttribute attribute;
	private ObjectClass oClass;
	private List<Prediction> predictions;
	private List<Condition> failureConditions;

	public BeliefAboutAction(Action action, String agent, ObjectAttribute attribute, ObjectClass oClass) {
		this.action = action;
		this.agent = agent;
		this.attribute = attribute;
		this.predictions = new ArrayList<Prediction>();
		this.failureConditions = new ArrayList<Condition>();
	}
	
	public void addPrediction(Prediction pred) {
		this.predictions.add(pred);
	}
	
	public void addFailureCondition(Condition cond) {
		this.failureConditions.add(cond);
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public ObjectAttribute getAttribute() {
		return attribute;
	}

	public void setAttribute(ObjectAttribute attribute) {
		this.attribute = attribute;
	}

	public List<Prediction> getPredictions() {
		return predictions;
	}

	public void setPredictions(List<Prediction> predictions) {
		this.predictions = predictions;
	}

	public List<Condition> getFailureConditions() {
		return failureConditions;
	}

	public void setFailureConditions(List<Condition> failureConditions) {
		this.failureConditions = failureConditions;
	}

	public ObjectClass getoClass() {
		return oClass;
	}

	public void setoClass(ObjectClass oClass) {
		this.oClass = oClass;
	}
	
	
}
