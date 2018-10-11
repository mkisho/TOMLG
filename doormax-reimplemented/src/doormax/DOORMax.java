package doormax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import doormax.structures.Action;
import doormax.structures.Condition;
import doormax.structures.attribute.Attribute;

class HashLearnerKey {
	private ObjectClass objClass;
	private Attribute attribute;

	public ObjectClass getObjClass() {
		return objClass;
	}

	public void setObjClass(ObjectClass objClass) {
		this.objClass = objClass;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + ((objClass == null) ? 0 : objClass.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HashLearnerKey other = (HashLearnerKey) obj;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		if (objClass == null) {
			if (other.objClass != null)
				return false;
		} else if (!objClass.equals(other.objClass))
			return false;
		return true;
	}

	public HashLearnerKey(ObjectClass objClass, Attribute attribute) {
		super();
		this.objClass = objClass;
		this.attribute = attribute;
	}

}

public class DOORMax {
	private OOMDPState oldState;
	private OOMDP oomdp;
	private List<Action> actionList;

	private Map<HashLearnerKey, Learner> learners;

	public DOORMax(OOMDP oomdp) {
		this.oomdp = oomdp;
		this.actionList = this.oomdp.getActions();

		initialize();
	}

	private void initialize() {
		oldState = null;
		learners = new HashMap<HashLearnerKey, Learner>();
		for (ObjectClass objClass : oomdp.getObjectClasses()) {
			for (Attribute attribute : objClass.getAttributes()) {
				learners.put(new HashLearnerKey(objClass, attribute),
						new Learner(attribute, objClass, this.actionList));
			}
		}
	}

	public void learn(OOMDPState currentState, Action action) {
		if (action == null) {
			oldState = currentState;
			return;
		}
		Condition condition = Condition.evaluate(oomdp.getPfIndex(), currentState);

		List<ObjectInstance> oldInstances = this.oldState.getObjects();
		List<ObjectInstance> newInstances = currentState.getObjects();

		if (oldInstances.size() != newInstances.size()) {
			System.out.println("Error in OOMDPState: " + oldInstances.size() + " != " + newInstances.size());
			System.exit(-1);
		}

		for (int i = 0; i < oldInstances.size(); i++) {
			ObjectInstance oldObj = oldInstances.get(i);
			ObjectInstance newObj = newInstances.get(i);

			assert (oldObj.getId().equals(newObj.getId()));

			List<Attribute> oldAttributes = oldObj.getAttributes();
			List<Attribute> newAttributes = newObj.getAttributes();

			assert (oldAttributes.size() == newAttributes.size());

			for (int index = 0; index < oldAttributes.size(); index++) {
				Attribute oldAtt = oldAttributes.get(index);
				Attribute newAtt = newAttributes.get(index);
				assert (oldAtt.getName().equals(newAtt.getName()));

				HashLearnerKey key = new HashLearnerKey(oldObj.getOclass(), oldAtt);

				assert (this.learners.get(key) != null);

				this.learners.get(key).learn(oldAtt, newAtt, condition, action);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DOORMax [learners=");
		builder.append(learners);
		builder.append("]");
		return builder.toString();
	}

}
