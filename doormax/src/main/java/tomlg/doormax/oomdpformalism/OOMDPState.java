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

/**
 * Defines an OO-MDP state as in the OO-MDP formalism
 * 
 * @author chronius
 *
 */
public class OOMDPState {
	/// Deixar a lista de objetos ordenados por id
	public final Map<String, ObjectInstance> objects;

	public OOMDPState() {
		this.objects = new HashMap<String, ObjectInstance>();
	}

	//TODO essa é uma das principais funções que podem ser otimizadas para melhorar o desempenho
	public ObjectInstance[][] matchObjects(OOMDPState s1) {
		// set of objects identificators that are in the two states
		Set<String> intersection = new HashSet<String>(objects.keySet()); // use the copy constructor
		intersection.retainAll(s1.objects.keySet());
		// set of objects identificators that are only in state0
		Set<String> onlyS0 = new HashSet<String>(objects.keySet()); // use the copy constructor
		onlyS0.removeAll(s1.objects.keySet());
		// set of objects identificators that are only in state1
		Set<String> onlyS1 = new HashSet<String>(s1.objects.keySet()); // use the copy constructor
		onlyS1.removeAll(this.objects.keySet());
		
		final int length = intersection.size() + onlyS0.size() + onlyS1.size();
		ObjectInstance[][] matchList = new ObjectInstance[length][2];
		
		int index = 0;
		Iterator<String> iterator = intersection.iterator();
		while(iterator.hasNext()) {
			String val = iterator.next();
			matchList[index][0] = this.objects.get(val);
			matchList[index][1] = s1.objects.get(val);
			index++;
		}
		
		iterator = onlyS0.iterator();
		while(iterator.hasNext()) {
			String val = iterator.next();
			matchList[index][0] = this.objects.get(val);
			index++;
		}
		
		iterator = onlyS1.iterator();
		while(iterator.hasNext()) {
			String val = iterator.next();
			matchList[index][1] = s1.objects.get(val);
			index++;
		}
		
		return matchList;
	}
	
	public Condition toCondition() {
		return null; //TODO
	}
	
	@Override
	public String toString() {
		return "OOMDPState [" + objects + "]";
	}

}