package tomlg.tom;

import java.io.Serializable;

import doormax.OOMDPState;
import doormax.structures.Action;

public class Observation implements Serializable{
	private static final long serialVersionUID = 548650881693211172L;

	private Action action;
	private OOMDPState state;
	
	public Observation(Action action, OOMDPState state) {
		super();
		this.action = action;
		this.state = state;
	}
	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(Action action) {
		this.action = action;
	}
	/**
	 * @return the effects
	 */
	/**
	 * @return the state
	 */
	public OOMDPState getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(OOMDPState state) {
		this.state = state;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		Observation other = (Observation) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	
}