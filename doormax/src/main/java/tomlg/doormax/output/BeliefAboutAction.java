package tomlg.doormax.output;

import tomlg.doormax.oomdpformalism.Action;

public class BeliefAboutAction extends Belief{
	private Action action;
	private String agent;
	
	private String precondition;
	private String effects;
	
	public BeliefAboutAction(Action action, String agent, String precondition, 
			String effects) {
		this.action = action;
		this.agent = agent;
		this.precondition = precondition;
		this.effects = effects;
	}
}
