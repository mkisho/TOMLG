package tomlg;

import doormax.OOMDPState;

public class SensoryMonitor {
	private Environment ev;
	
	public SensoryMonitor(Environment ev) {
		this.ev = ev;
	}

	public OOMDPState environmentToPerception(){
		return ev.getState();
	}
}
