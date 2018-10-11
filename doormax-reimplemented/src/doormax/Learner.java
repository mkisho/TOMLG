package doormax;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import doormax.structures.Action;
import doormax.structures.Attribute;
import doormax.structures.Condition;

/**
 * Classe responsável por aprender todas as relações entre um atributo, de uma classe específica, e ações
 * @author chronius
 *
 */
public class Learner {
	private Attribute attribute;
	private ObjectClass objClass;
	private Map<Action, PFLearner> actionLearner;
	private List<Action> actionList;
	
	public Learner(Attribute attribute, ObjectClass objClass, List<Action> actionList) {
		super();
		this.attribute = attribute;
		this.objClass = objClass;
		this.actionList = actionList;
		
		this.actionLearner = new HashMap<Action, PFLearner>();
		for (Action action: this.actionList) {
			this.actionLearner.put(action, new PFLearner(objClass, attribute, action));
		}
	}

	public void learn(Attribute oldAtribute, Attribute newAttribute, Condition condition, Action action) {
		this.actionLearner.get(action).learn(oldAtribute, newAttribute, condition);
	}
	
	
	
	@Override
	public String toString() {
		return "Learner [attribute=" + attribute + ", objClass=" + objClass + ", actionLearner=" + actionLearner
				+ ", actionList=" + actionList + "]";
	}

	public void predict(OOMDPState state, Action action) {
		
	}
	
	public void toBeliefs() {
		
	}
	
}
