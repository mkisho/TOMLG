package tomlg;

import doormax.structures.Action;

public class AgentActuator {
	private Environment ev;
	
	public AgentActuator(Environment ev) {
		this.ev = ev;
	}
	
	public void doActionOnEnvironment(Action action) {
		this.ev.setState(this.ev.getEnvironmnetSimulator().simulateAction(ev.getState(), action));
	}

}
