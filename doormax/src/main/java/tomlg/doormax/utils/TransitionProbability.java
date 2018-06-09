package tomlg.doormax.utils;

import tomlg.doormax.oomdpformalism.OOMDPState;

/**
 * Represents the probability of transition to a given state.
 * @author James MacGlashan
 *
 */
public class TransitionProbability {

	/**
	 * The state to which the agent may transition.
	 */
	public OOMDPState		s;
	
	/**
	 * the probability of transitioning to state s
	 */
	public double		p;
	
	public TransitionProbability(OOMDPState s, double p){
		this.s = s;
		this.p = p;
	}
	
}
