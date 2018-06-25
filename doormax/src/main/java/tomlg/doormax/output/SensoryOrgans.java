package tomlg.doormax.output;

import tomlg.doormax.oomdpformalism.OOMDPState;

public class SensoryOrgans {
	private Environment ev;
	
	public SensoryOrgans(Environment ev) {
		this.ev = ev;
	}

	public OOMDPState environmentToPerception(){
		return ev.getState();
	}
}
