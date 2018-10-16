package tomlg.planner;

import java.util.List;

import doormax.DOORMax;
import doormax.OOMDPState;
import doormax.structures.Action;
import tomlg.Goal;

public interface Planner {
	/**
	 * Deve retornar um plano para partindo de initState, conseguir completar o goal, ou retornar null quando não for possível
	 * Doormax serve para expansão dos nós de busca.
	 * @param goal
	 * @param initState
	 * @param doormax
	 * @return
	 */
	public List<Action> planForGoal(final Goal goal, final OOMDPState initState, final DOORMax doormax);

}
