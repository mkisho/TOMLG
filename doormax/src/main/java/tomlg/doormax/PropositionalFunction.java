package tomlg.doormax;

import tomlg.doormax.oomdpformalism.OOMDPState;

/**
 * Defines an propositional function as in the OO-MDP formalism
 * @author chronius
 *
 */
public abstract class PropositionalFunction {
	public final String name;
	
	
	public PropositionalFunction(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return "PropositionalFunction [name=" + name + "]";
	}

	public abstract boolean evaluate(OOMDPState state);

}
