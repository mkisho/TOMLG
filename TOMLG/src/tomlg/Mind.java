package tomlg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import doormax.DOORMax;
import doormax.OOMDP;
import doormax.OOMDPState;
import doormax.structures.Action;

public class Mind {
	private DOORMax agentLearner;
	private String agentName;

	private List<Goal> goals;
	private Goal choosenGoal;
	private List<Intention> intentions;
	private OOMDP oomdp;
	private List<Action> actionRepertoire;
	
	private Random random;//TODO remove
	
	public Mind(String agentName, OOMDP oomdp) {
		this.oomdp = oomdp;
		this.agentName = agentName;
		this.agentLearner = new DOORMax(oomdp);
		this.actionRepertoire = this.oomdp.getActions();
		this.goals = new ArrayList<Goal>();
		this.choosenGoal = null;
		this.intentions = new ArrayList<Intention>();
		
		this.random = new Random();
	}

	public void learn(OOMDPState currentState) {
		this.agentLearner.learn(currentState, (intentions.size() == 0 ? null : intentions.get(0).getAction()));
	}

	public Intention reasoning() {
		System.out.println("Predictions>>>> \n");
		System.out.println(this.agentLearner.predict(null, actionRepertoire));
		System.out.println("END Predictions<<<< \n");
		
		
		if (this.choosenGoal == null) {
			// try new things
			Action action = this.actionRepertoire.get(this.random.nextInt(this.actionRepertoire.size()));
			Intention intention = new Intention(action, null);
			this.intentions.add(0, intention);
			return intention;
		} else {
			Intention intention = new Intention(new Action("taxiMoveNorth"), null);
			this.intentions.add(0, intention);
			return intention;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Mind [agentLearner=");
		builder.append(agentLearner);
		builder.append(", agentName=");
		builder.append(agentName);
		builder.append("]");
		return builder.toString();
	}
}
