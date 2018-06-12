package tomlg.doormax.output;

import tomlg.doormax.oomdpformalism.OOMDPState;

public class SensoryOrgaons {
	private Environment ev;
	
	public SensoryOrgaons(Environment ev) {
		this.ev = ev;
	}

	public OOMDPState environmentToPerception(){
		return ev.getState();
	}
}
