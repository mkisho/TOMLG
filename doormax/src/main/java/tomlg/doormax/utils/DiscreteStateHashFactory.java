package tomlg.doormax.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.ObjectInstance;

import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectClass;

/**
 * This hash factory will producing hash codes that are unique for discrete
 * OO-MDP domains. It should *not* be used with continuous domains nor
 * relational domains. If the default constructor is called and no other methods
 * are specified, then the state hash code will be computed with respect to all
 * attributes of every object instance. It is not uncommon for tasks in certain
 * domains to make the values for certain objects constant. For instance,
 * consider a goal location which will always be the in same location for all
 * states of a task. In such situations, the attributes of constant objects
 * provide no information about the specific state and computing a hash code
 * with respect to its values is wasted computation time. To make hash code
 * computation more efficient, the user can also specify which attributes for
 * which class to use in computing the has code. Note that once any method to
 * specify an attribute for a class is made, only the attributes specified (from
 * that first call and any subsequent method calls) will be used for hashing.
 * 
 * @author James MacGlashan
 *
 */
public class DiscreteStateHashFactory implements StateHashFactory {

	protected Map<String, List<ObjectAttribute>> attributesForHashCode;

	/**
	 * Initializes this hashing factory to compute hash codes with all attributes of
	 * all object classes.
	 */
	public DiscreteStateHashFactory() {
		attributesForHashCode = null;
	}

	/**
	 * Initializes this hashing factory to hash on only the attributes for the
	 * specified classes in the provided map
	 * 
	 * @param attributesForHashCode
	 *            a map from object class names to the attributes that should be
	 *            used in the hash calculation for those object classes.
	 */
	public DiscreteStateHashFactory(Map<String, List<ObjectAttribute>> attributesForHashCode) {
		this.attributesForHashCode = attributesForHashCode;
	}

	/**
	 * Sets this hashing factory to hash on only the attributes for the specified
	 * classes in the provided map
	 * 
	 * @param attributesForHashCode
	 *            a map from object class names to the attributes that should be
	 *            used in the hash calculation for those object classes.
	 */
	public void setAttributesForHashCode(Map<String, List<ObjectAttribute>> attributesForHashCode) {
		this.attributesForHashCode = attributesForHashCode;
	}

	/**
	 * Sets which attributes to use in the hash calculation for the given class. If
	 * this method has not be called before and the class was initialized with the
	 * default constructor, then only these class attributes, and those specified by
	 * subsequent calls to this method or those specified by a subsequent call of
	 * the other attribute setting methods will be used for computing hash codes
	 * 
	 * @param classname
	 *            the name of the class
	 * @param atts
	 *            the attributes whose values in object instances should be used to
	 *            compute hash codes
	 */
	public void setAttributesForClass(String classname, List<ObjectAttribute> atts) {
		if (attributesForHashCode == null) {
			attributesForHashCode = new HashMap<String, List<ObjectAttribute>>();
		}
		attributesForHashCode.put(classname, new ArrayList<ObjectAttribute>(atts));
	}

	/**
	 * Specifies that an additional attribute of the specified class should be used
	 * for computing hash codes. If this method has not be called before and the
	 * class was initialized with the default constructor, then only these class
	 * attributes, and those specified by subsequent calls to this method or those
	 * specified by a subsequent call of the other attribute setting methods will be
	 * used for computing hash codes
	 * 
	 * @param classname
	 *            the name of the class
	 * @param att
	 *            the attribute whose values will be included in the computation of
	 *            hash codes
	 */
	public void addAttributeForClass(String classname, ObjectAttribute att) {
		if (attributesForHashCode == null) {
			attributesForHashCode = new HashMap<String, List<ObjectAttribute>>();
		}
		List<ObjectAttribute> atts = attributesForHashCode.get(classname);
		if (atts == null) {
			atts = new ArrayList<ObjectAttribute>();
			attributesForHashCode.put(classname, atts);
		}
		// check if already there or not
		for (ObjectAttribute attInList : atts) {
			if (attInList.name.equals(att.name)) {
				return;
			}
		}
		// if reached here then this att is not already added
		atts.add(att);
	}

	@Override
	public StateHashTuple hashState(OOMDPState s) {
		return new DiscreteStateHashTuple(s);
	}

	public class DiscreteStateHashTuple extends StateHashTuple {

		public DiscreteStateHashTuple(OOMDPState s) {
			super(s);
		}

		@Override
		public void computeHashCode() {

			List<ObjectClass> objectClasses = this.getOrderedClasses();
			int totalVol = 1;
			hashCode = 0;
			for (ObjectClass oclass : s.oomdp.objectClasses) {
				List<tomlg.doormax.oomdpformalism.ObjectInstance> obs = s.getObjectsOfClass(oclass.name);
				ObjectClass oc = obs.get(0).getObjectClass();
				int vol = this.computeVolumeForClass(oc);

				// too ensure object order invariance, the hash values must first be sorted by
				// their object-wise hashcode
				int[] obHashCodes = new int[obs.size()];
				for (int i = 0; i < obs.size(); i++) {
					obHashCodes[i] = this.getIndexValue(s.objectsByName.get(obs.get(0).getId()), oc);
				}
				Arrays.sort(obHashCodes);

				// multiply in reverse (for smaller total hash codes)
				for (int i = obHashCodes.length - 1; i >= 0; i--) {
					hashCode += obHashCodes[i] * totalVol;
					totalVol *= vol;
				}

			}

			needToRecomputeHashCode = false;

		}

		// this method will assume that attributes are all discrete
		private int getIndexValue(tomlg.doormax.oomdpformalism.ObjectInstance objectInstance, ObjectClass oc) {

			List<ObjectAttribute> attributes = this.getAttributesForClass(oc);
			int index = 0;
			int vol = 1;
			for (ObjectAttribute att : attributes) {
				/*
				 * if (att.type == AttributeType.STRING) { index +=
				 * objectInstance.getStringValForAttribute(att.name).hashCode() * vol; } else if
				 * (att.type == AttributeType.INTARRAY) { index +=
				 * this.intArrayCode(objectInstance.getIntArrayValue(att.name)) * vol; } else {
				 * index += objectInstance.getDiscValForAttribute(att.name) * vol; } if
				 * (att.type == AttributeType.DISC || att.type == AttributeType.BOOLEAN) { vol
				 * *= att.discValues.size(); } else if (att.type == AttributeType.INT ||
				 * att.type == AttributeType.STRING || att.type == AttributeType.INTARRAY) {
				 */
				vol *= 				objectInstance.getAttributeVal(att).hashCode() * 31;
				/*
				 * } else { throw new RuntimeException(
				 * "DiscreteStateHashFactory cannot compute hash for non discrete (discrete, boolean, string, or int) values"
				 * ); }
				 */

			}

			return index;

		}

		// this method will assume that attributes are all discrete
		private int computeVolumeForClass(ObjectClass oclass) {

			List<ObjectAttribute> attributes = this.getAttributesForClass(oclass);
			int vol = 1;
			for (ObjectAttribute att : attributes) {
				// if (att.type == AttributeType.DISC || att.type == AttributeType.BOOLEAN) {
				// vol *= att.discValues.size();
				// } else if (att.type == AttributeType.INT || att.type == AttributeType.STRING
				// || att.type == AttributeType.INTARRAY) {
				vol *= 31;
				// } else {
				// throw new RuntimeException(
				// "DiscreteStateHashFactory cannot compute hash for non discrete (discrete,
				// boolean, string, or int) values");
				// }
			}

			return vol;
		}

		private List<ObjectAttribute> getAttributesForClass(ObjectClass oc) {
			if (DiscreteStateHashFactory.this.attributesForHashCode != null) {
				List<ObjectAttribute> selectedAtts = DiscreteStateHashFactory.this.attributesForHashCode.get(oc.name);
				if (selectedAtts == null) {
					// no definition at all for this class, so return empty list
					return new ArrayList<ObjectAttribute>();
				}
				return selectedAtts;
			}

			// then default to using all attributes for all object classes
			return oc.attributes;
		}

		private List<ObjectClass> getOrderedClasses() {
			List objectClasses = new ArrayList<ObjectClass>(s.oomdp.objectClasses);
			Collections.sort(objectClasses);
			return objectClasses;
		}

		/**
		 * Returns an int value for an int array
		 * 
		 * @param array
		 *            the int array
		 * @return an int value for it
		 */
		protected int intArrayCode(int[] array) {
			int sum = 0;
			for (int i : array) {
				sum *= 31;
				sum += i;
			}
			return sum;
		}

	}

}
