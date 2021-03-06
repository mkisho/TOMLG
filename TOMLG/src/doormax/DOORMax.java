package doormax;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import doormax.structures.Action;
import doormax.structures.Condition;
import doormax.structures.Effect;
import doormax.structures.attribute.Attribute;

class HashLearnerKey implements Serializable {
	private static final long serialVersionUID = 291202025579285782L;
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

public class DOORMax implements Serializable {
	private static final long serialVersionUID = 7759131257535126841L;
	private transient OOMDPState oldState;
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
		assert(this.actionList.contains(action)==true);

		Condition condition = Condition.evaluate(oomdp.getPfIndex(), oldState);

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
		this.oldState = currentState;
	}

	public List<Effect> predict(OOMDPState state0, Action action) {
		if (action == null)
			return null;
		if (state0 == null)
			state0 = this.oldState;

		List<Effect> predicted = new ArrayList<Effect>();

		final Condition cond = Condition.evaluate(oomdp.getPfIndex(), state0);
		List<Effect> totalPool = new ArrayList<Effect>();
		for (ObjectClass objClass : state0.getOomdp().getObjectClasses()) {
			List<Effect> currentPool = getAllEffectPredictionForObjectClass(action, objClass, cond, state0);
			if (currentPool == null) {
				totalPool = currentPool;
				break;
			} else
				totalPool.addAll(currentPool);
		}
		if (totalPool == null) {
			return null;
		}
		predicted.addAll(totalPool);
		return predicted;
	}

	/**
	 * TODO Refatorar o método para usar o método de cima
	 * 
	 * @param state0
	 * @param actions
	 * @return
	 */
	public Map<Action, List<Effect>> predict(OOMDPState state0, List<Action> actions) {
		if (state0 == null)
			state0 = this.oldState;

		Map<Action, List<Effect>> predicted = new HashMap<Action, List<Effect>>();

		final Condition cond = Condition.evaluate(oomdp.getPfIndex(), state0);
		for (Action action : actions) {
			List<Effect> totalPool = new ArrayList<Effect>();
			for (ObjectClass objClass : state0.getOomdp().getObjectClasses()) {
				List<Effect> currentPool = getAllEffectPredictionForObjectClass(action, objClass, cond, state0);
				if (currentPool == null) {
					totalPool = currentPool;
					break;
				} else
					totalPool.addAll(currentPool);
			}
			predicted.put(action, totalPool);
		}

		return predicted;
	}

	/**
	 * Faz uma cópia de state e retorna a cópia com os effeitos da lista apliados
	 * 
	 * @param state
	 * @param effects
	 * @return
	 */
	private OOMDPState applyAllEffectsInState(final OOMDPState state, final List<Effect> effects) {
		assert (effects != null);
		assert (state != null);
		OOMDPState resultState = state.copy();

		for (Effect effect : effects) {
			effect.apply(resultState);
		}

		return resultState;
	}

	/**
	 * Retorna OOMDPStates preditos aplicando actions a state0.
	 * 
	 * @param state0
	 * @param actions
	 * @return
	 */
	public Map<Action, OOMDPState> predictOOMDPStates(OOMDPState state0, List<Action> actions) {
		Map<Action, List<Effect>> resultEffects = this.predict(state0, actions);
		Map<Action, OOMDPState> resultStates = new HashMap<Action, OOMDPState>();

		for (Action action : resultEffects.keySet()) {
			if (resultEffects.get(action) == null)
				resultStates.put(action, null);
			else {
				resultStates.put(action, this.applyAllEffectsInState(state0.copy(), resultEffects.get(action)));
			}
		}

		return resultStates;
	}

	private List<Effect> getAllEffectPredictionForObjectClass(Action action, ObjectClass objClass, Condition cond,
			OOMDPState state) {
		List<Effect> currentPool = new ArrayList<Effect>();

		HashLearnerKey key = new HashLearnerKey(objClass, null);
		for (Attribute att : objClass.getAttributes()) {
			key.setAttribute(att);
			List<Effect> effects = this.learners.get(key).predict(cond, state, action);
			if (effects == null)
				return null;
			else
				currentPool.addAll(effects);
		}
		return currentPool;
	}
	
	/**
	 * Somente usado para a saída lógica
	 * @return
	 */
	public Collection<Learner> getLearners() {
		return this.learners.values();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DOORMax [learners=");
		for (Learner l : learners.values())
			builder.append(">>" + l + "\n\n");
		builder.append("]");
		return builder.toString();
	}

	/**
	 * The last state visited by the algorithm
	 * 
	 * @return
	 */
	public OOMDPState getOldState() {
		return oldState;
	}

	public void combine(DOORMax agentLearning) {
		for(Map.Entry<HashLearnerKey, Learner> k : agentLearning.learners.entrySet()) {
			this.learners.put(k.getKey(), k.getValue());
			// TODO em uma futura versão o conhecimento deve ser efetivamente combinado. 
			// No experimento 03 não fará diferença no final, mas para uma utilização mais complexa 
			// seria necessário usar um update e não um put
		}
	}
}
