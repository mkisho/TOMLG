package tomlg;

import java.util.ArrayList;
import java.util.List;

import doormax.DOORMax;
import doormax.OOMDP;
import doormax.OOMDPState;
import doormax.structures.Action;

public class Mind {
	private DOORMax agentLearner;
	private String agentName;
	
	private List<Goal> goals;
	private List<Goal> choosenGoal;
	private List<Intention> intentions;
	
	public Mind(String agentName, OOMDP oomdp) {
		this.agentName = agentName;
		this.agentLearner = new DOORMax(oomdp);
		
		this.goals = new ArrayList<Goal>();
		this.choosenGoal = new ArrayList<Goal>();
		this.intentions = new ArrayList<Intention>();
	}
	
	
	public void learn(OOMDPState currentState) {
		this.agentLearner.learn(currentState, intentions.get(0).getAction());
	}
	
	public Intention reasoning() {
		Intention intention = new Intention(new Action("taxiMoveNorth"), null);
		this.intentions.add(0, intention);
		return intention;
	}

}
