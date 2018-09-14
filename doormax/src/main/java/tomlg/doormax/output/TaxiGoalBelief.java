package tomlg.doormax.output;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.OOMDPState;

public class TaxiGoalBelief extends Belief{	
	private List<Action> movActions;
	private Action pickupAction;
	private Action dropAction;
	
	public TaxiGoalBelief() {
		movActions = new ArrayList<Action>();
		movActions.add(new Action("taxiMoveNorth"));
		movActions.add(new Action("taxiMoveEast"));
		movActions.add(new Action("taxiMoveSouth"));
		movActions.add(new Action("taxiMoveWest"));
		movActions.add(new Action("taxiMoveNorth"));
		movActions.add(new Action("taxiMoveNorth"));
		 
		pickupAction = new Action("pickupPassenger");
		dropAction   = new Action("dropOffPassenger");
	}
	
	private String motivationForSelection;

	public String getMotivationForSelection() {
		return motivationForSelection;
	}

	public void setMotivationForSelection(String motivationForSelection) {
		this.motivationForSelection = motivationForSelection;
	}
	
	
	public Action policy(OOMDPState state, KnowledgeMemoryBase knowledge) {
		// primeiro o agente deve saber como funciona cada ação de movimento 
		List<Action> actionsUnkow = knowledge.whichActionsUnknow(movActions);
		if(actionsUnkow.size() > 0) {
			return actionsUnkow.get(ThreadLocalRandom.current().nextInt(actionsUnkow.size()) % actionsUnkow.size());
		}
		
		// se o agente não saber o resultado de alguma ação, então tenta-la
		
		for(PrevisionBelief p: knowledge.getPrevisionsBeliefs()) {
			if(p.getPredictions() == null || p.getPredictions().size() == 0) {
				return p.getAction();
			}
		}
		// já tem o conhecimento completo
		return null;
	}
}
