package tomlg.doormax.oomdpformalism;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import tomlg.doormax.Condition;
import tomlg.doormax.PropositionalFunction;

/**
 * Defines an OO-MDP state as in the OO-MDP formalism
 * 
 * @author chronius
 *
 */
public class OOMDPState {
	/// Deixar a lista de objetos ordenados por id
	public final Set<ObjectInstance> objects;
	public final Map<String, ObjectInstance> objectsByName;

	public final OOMDP oomdp;

	public OOMDPState(OOMDP oomdp) {
		this.oomdp = oomdp;
		this.objectsByName = new HashMap<String, ObjectInstance>();
		this.objects = new HashSet<ObjectInstance>();
	}

	public void addObjectInstance(ObjectInstance o) {
		this.objectsByName.put(o.getId(), o);
		this.objects.add(o);
	}

	public OOMDPState(OOMDPState oomdpstate) {
		this(oomdpstate.oomdp);// this.oomdp = oomdpstate.oomdp;

		for (ObjectInstance o : oomdpstate.objects) {
			this.addObjectInstance(o.copy());
		}
	}

	// TODO essa é uma das principais funções que podem ser otimizadas para melhorar
	// o desempenho
	public ObjectInstance[][] matchObjects(OOMDPState s1) {
		// set of objects identificators that are in the two states
		Set<String> intersection = new HashSet<String>(this.objectsByName.keySet()); // use the copy constructor
		intersection.retainAll(s1.objectsByName.keySet());
		// set of objects identificators that are only in state0
		Set<String> onlyS0 = new HashSet<String>(this.objectsByName.keySet()); // use the copy constructor
		onlyS0.removeAll(s1.objectsByName.keySet());
		// set of objects identificators that are only in state1
		Set<String> onlyS1 = new HashSet<String>(s1.objectsByName.keySet()); // use the copy constructor
		onlyS1.removeAll(this.objectsByName.keySet());

		final int length = intersection.size() + onlyS0.size() + onlyS1.size();
		ObjectInstance[][] matchList = new ObjectInstance[length][2];

		int index = 0;
		Iterator<String> iterator = intersection.iterator();
		while (iterator.hasNext()) {
			String val = iterator.next();
			matchList[index][0] = this.objectsByName.get(val);
			matchList[index][1] = s1.objectsByName.get(val);
			index++;
		}

		iterator = onlyS0.iterator();
		while (iterator.hasNext()) {
			String val = iterator.next();
			matchList[index][0] = this.objectsByName.get(val);
			index++;
		}

		iterator = onlyS1.iterator();
		while (iterator.hasNext()) {
			String val = iterator.next();
			matchList[index][1] = s1.objectsByName.get(val);
			index++;
		}

		return matchList;
	}

	public Condition toCondition() {
		String result = "";

		for (PropositionalFunction p : this.oomdp.propositionsList()) {
			result += (p.evaluate(this) ? 1 : 0);
		}
		return new Condition(this.oomdp.propositionsList(), result);
	}

	@Override
	public String toString() {
		return "OOMDPState [" + objects + "]";
	}

	// TODO pode ser otimizado com uma tabela de lookup
	public ObjectInstance getObjectOfClass(String name) {
		for (ObjectInstance o : this.objects) {
			if (o.objectClass.name.equals(name)) return o;
		}
		return null;
	}

	// TODO pode ser otimizado com uma tabela de lookup
	public List<ObjectInstance> getObjectsOfClass(String name) {
		List<ObjectInstance> objs = new ArrayList<ObjectInstance>();
		for (ObjectInstance o : this.objects) {
			if (o.objectClass.name.equals(name)) objs.add(o);
		}
		return objs;
	}

}