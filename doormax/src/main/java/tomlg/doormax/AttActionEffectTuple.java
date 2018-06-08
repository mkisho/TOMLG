package tomlg.doormax;

import tomlg.doormax.effects.EffectType;
import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectClass;

/**
 * Tupla para ser usada como chave
 * @author chronius
 *
 */
public class AttActionEffectTuple {
	public final ObjectClass oClass;
	public final ObjectAttribute attribute;
	public final Action action;
	public final EffectType effectType;
	
	
	public AttActionEffectTuple(ObjectClass oClass, ObjectAttribute attribute, Action action, EffectType effectType) {
		super();
		this.oClass = oClass;
		this.attribute = attribute;
		this.action = action;
		this.effectType = effectType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AttActionEffectTuple [oClass=" + oClass + ", attribute=" + attribute + ", action=" + action
				+ ", effectType=" + effectType + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + ((effectType == null) ? 0 : effectType.hashCode());
		result = prime * result + ((oClass == null) ? 0 : oClass.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttActionEffectTuple other = (AttActionEffectTuple) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		if (effectType == null) {
			if (other.effectType != null)
				return false;
		} else if (!effectType.equals(other.effectType))
			return false;
		if (oClass == null) {
			if (other.oClass != null)
				return false;
		} else if (!oClass.equals(other.oClass))
			return false;
		return true;
	}

	
}
