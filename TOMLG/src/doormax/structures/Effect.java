package doormax.structures;

import java.util.ArrayList;
import java.util.List;

import doormax.OOMDPState;
import doormax.ObjectClass;
import doormax.ObjectInstance;
import doormax.structures.attribute.Attribute;

public class Effect {
	public static final EffectType[] γ = { new ArithmeticEffect(), new AssignmentEffect() };

	private EffectType type;
	private Attribute attribute;
	private ObjectClass objClass;
	private Double value;

	public Effect(EffectType type, Attribute attribute, ObjectClass objClass, Double value) {
		super();
		this.type = type;
		this.attribute = attribute;
		this.objClass = objClass;
		this.value = value;
	}

	public static List<Effect> possibleEffectsExplanation(Attribute oldAtt, Attribute newAtt, ObjectClass objClass) {
		List<Effect> hypothesisEffects = new ArrayList<Effect>();

		for (EffectType e : γ) {
			Effect hyptothesis = e.possibleEffectsExplanation(oldAtt, newAtt, objClass);
			if (hyptothesis != null)
				hypothesisEffects.add(hyptothesis);
		}
		return hypothesisEffects;
	}

	public EffectType getType() {
		return type;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public ObjectClass getObjClass() {
		return objClass;
	}

	public void setObjClass(ObjectClass objClass) {
		this.objClass = objClass;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + ((objClass == null) ? 0 : objClass.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Effect other = (Effect) obj;
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
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Effect [type=" + type.name() + ", attribute=" + attribute.getName() + ", objClass=" + objClass.getName()
				+ ", value=" + value + "]";
	}

	/*
	 * Checks if this contradicts e for the given state
	 * 
	 * return true if this contradicts e TODO deve ter uma forma melhor de fazer
	 * isso, corrigir depois
	 */
	public boolean contradicts(Effect e, OOMDPState state) {
		if (!(this.objClass.equals(e.getObjClass().getName()) && this.attribute.equals(e.getAttribute())))
			return false;

		if (this.type.equals(e.type)) {// do mesmo tipo mas com valores diferentes
			if (this.getValue() == e.getValue())
				return false;
			else
				return true;
		}

		List<ObjectInstance> instances = state.getObjectsOfClass(this.objClass.getName());
		assert (instances != null && instances.size() > 0);
		for (ObjectInstance instance : instances) {
			ObjectInstance instance1 = instance.copy();
			ObjectInstance instance2 = instance.copy();
			this.apply(instance1);
			e.apply(instance2);

			if (instance1.getAttributeValByName(this.attribute.getName()).getDoubleValue() != instance2
					.getAttributeValByName(this.attribute.getName()).getDoubleValue()) {
				return true;
			}

		}
		return false;
	}

	public void apply(ObjectInstance objInstance) {
		this.type.apply(objInstance.getAttributeValByName(this.attribute.getName()), this.value);
	}
}
