package tomlg.doormax.output;

import tomlg.doormax.oomdpformalism.Action;

public class EnvironmentActuation {
	private Environment ev;
	
	public EnvironmentActuation(Environment ev) {
		this.ev = ev;
	}
	
	public void doActionOnEnvironment(Action action) {
		this.ev.setState(this.ev.getEnvironmnetSimulator().simulateAction(ev.getState(), action));
	}

}
