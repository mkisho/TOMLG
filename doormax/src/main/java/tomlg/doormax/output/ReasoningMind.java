package tomlg.doormax.output;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import org.simpleframework.xml.Root;
import tomlg.doormax.Effect;
import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.OOMDPState;

public class ReasoningMind {

	private String name;
	private Action[] actionRepertoire;
	private KnowledgeMemoryBase knowledge;
	private SensoryOrgans sensories;
	private EnvironmentActuation bodyActuators;
	private ReasoningXMLWriter outputWriter;
	private EffectsLearner effectsLearner;
	private int step;

	public ReasoningMind(String name, Action[] actionRepertoire, Environment environment, String outputFile) {
		this.name = name;
		this.actionRepertoire = actionRepertoire;
		System.out.println("Initializing agent: " + name);
		this.sensories = new SensoryOrgans(environment);
		this.bodyActuators = new EnvironmentActuation(environment);
		this.step = 0;
		this.initializeMind();
		this.outputWriter = new ReasoningXMLWriter(outputFile);
	}

	private void initializeMind() {
		// initialize Beliefs
		this.knowledge = new KnowledgeMemoryBase(this.name);

		OOMDPState worldState = this.sensories.environmentToPerception();
		this.effectsLearner = new EffectsLearner(worldState.oomdp, worldState, Effect.γ,
				worldState.oomdp.propositionsList());
		this.perceive();
		this.knowledge.updateBeliefsAboutAction(this.effectsLearner.getModeledBeliefs());
		this.addGoalBelief(new TaxiGoalBelief());
	}

	public void addGoalBelief(TaxiGoalBelief goal) {
		System.out.println("Agent " + this.name + " has a new goal");
		this.knowledge.addBelief(goal);
	}

	public void perceive() {
		System.out.println("Agent " + this.name + " is perceiving the environment");
		OOMDPState worldState = this.sensories.environmentToPerception();

		this.knowledge.updateWorldStateBelief(worldState);
		// update Beliefs about the state of the world
	}

	public void updateBeliefs() {

	}

	public void tick() {
		// update what I know about the state
		Action action = reasoning();

		if (action == null) {
			outputWriter.writeReasoningStep(this);
			outputWriter.endExperiment(this);
			System.exit(0);
		}
		System.out.println("Agent Selected Action " + action.name);
		if (action != null)
			this.bodyActuators.doActionOnEnvironment(action);
		this.perceive();

		if (!this.effectsLearner.isPredictionModeled(this.knowledge.getPreviousState(), action)) {
			// learn new things
			this.effectsLearner.learnPrediction(this.knowledge.getPreviousState(), this.knowledge.getCurrentState(),
					action);
		}
		this.knowledge.updateBeliefsAboutAction(this.effectsLearner.getModeledBeliefs());
		this.knowledge
				.updatePredictionBeliefs(this.effectsLearner.getPredictionBeliefs(this.knowledge.getCurrentState()));

		outputWriter.writeReasoningStep(this);
		this.step += 1;
	}

	// get next action of agent
	public Action reasoning() {
		System.out.println("Agent " + this.name + " is reasoning");

		// if there is no choosen goal, then chosee one, if there's none left, end
		if (!this.knowledge.hasChoosenGoal()) {
			List<TaxiGoalBelief> goals = this.knowledge.getGoals();
			if (goals.size() == 0) {
				System.out.println("Agent " + this.name + " has no goals! " + "No action selected");
				System.out.println("Experiment Ended.");
				return null;
			}
			this.knowledge.setChoosenGoal(goals.get(0), "Random goal selection");
		}
		// if do not know what to do, then I will try some random action that the agent
		// don't know the outcome
		return this.knowledge.getChoosenGoal().policy(this.knowledge.getCurrentState(), this.knowledge);
	}

	public KnowledgeMemoryBase getKnowlodgeMemoryBase() {
		return this.knowledge;
	}

	public int getStep() {
		return step;
	}

	public void output() {
		this.outputWriter.writeReasoningStep(this);
	}

	public String getAgentName() {
		return this.name;
	}

	public void end() {
		this.outputWriter.endExperiment(this);
	}
}
