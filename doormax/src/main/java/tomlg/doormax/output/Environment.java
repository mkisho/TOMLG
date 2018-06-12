package tomlg.doormax.output;

import tomlg.doormax.environment.EnvironmentSimulator;
import tomlg.doormax.oomdpformalism.OOMDPState;

public class Environment {
	private OOMDPState state;
	private EnvironmentSimulator evs;

	public Environment(OOMDPState initialState, EnvironmentSimulator evs) {
		this.evs = evs;
		this.state = initialState;
	}

	public OOMDPState getState() {
		return this.state;
	}
	
	public EnvironmentSimulator getEnvironmnetSimulator() {
		return this.evs;
	}
	
	public void setState(OOMDPState newState) {
		this.state = newState;
	}
}
