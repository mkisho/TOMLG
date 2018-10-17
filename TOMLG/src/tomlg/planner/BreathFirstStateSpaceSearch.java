package tomlg.planner;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import doormax.DOORMax;
import doormax.OOMDPState;
import doormax.structures.Action;
import tomlg.Goal;

class Node {
	private OOMDPState value;
	private Map<Action, Node> childrens;

	public Node(OOMDPState value, List<Action> actions) {
		this.value = value;
		this.childrens = new HashMap<Action, Node>();
		for (Action action : actions) {
			this.childrens.put(action, null);
		}
	}

	public List<Action> getUnkownActions() {
		List<Action> result = new ArrayList<Action>();
		for (Action action : this.childrens.keySet()) {
			if (this.childrens.get(action) == null)
				result.add(action);
		}
		return result;
	}

	public void putChildren(Action action, Node newNode) {
		this.childrens.put(action, newNode);
	}

	public OOMDPState getValue() {
		return value;
	}

	public Map<Action, Node> getChildrens() {
		return childrens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}

public class BreathFirstStateSpaceSearch implements Planner {
	private Map<OOMDPState, Node> nodes;
	private final List<Action> actions;

	public BreathFirstStateSpaceSearch(final List<Action> actions) {
		this.actions = actions;
		this.nodes = new HashMap<>();
	}

//	private void addNewNode(OOMDPState state) {
//		Node node = new Node(state, this.actions);
//		nodes.put(state, node);

//		System.out.println("Planner\nNew State Added");
//	}

	/*
	 * expande todos os nós possíveis. chamar antes de realizar uma busca
	 */
	private void expandNodes(final DOORMax doormax) {
		System.out.println("Planner\nExpanding Nodes");
		int countExpanded = 0;
		int countTotalExpanded = 0;
		int countUnkown = 0;
		Queue<Node> nodesToExpand = new LinkedList<>();
		nodesToExpand.addAll(this.nodes.values());
		do {
			countExpanded = 0;
			System.out.println("Expanding nodes>> total: "+countTotalExpanded);

			while(nodesToExpand.size() > 0) {
				Node node = nodesToExpand.poll();
				
				Map<Action, OOMDPState> predictions = doormax.predictOOMDPStates(node.getValue(),
						node.getUnkownActions());
				for (Action action : predictions.keySet()) {
					OOMDPState newState = predictions.get(action);

					if (newState == null) {
						countUnkown++;
					} else {
						if (this.nodes.get(newState) == null && !node.getValue().equals(newState)) {
							countExpanded++;
							Node newNode = new Node(newState, this.actions);
							this.nodes.put(newState, newNode);
							node.putChildren(action, newNode);
							nodesToExpand.add(newNode);
						} else {
							node.putChildren(action, node);
							if(node.getValue().equals(newState))
								node.getChildrens().remove(action);
						}
					}
				}
			}
			countTotalExpanded += countExpanded;
		} while (countExpanded != 0);
		System.out.println(">> " + countTotalExpanded + " nodes expanded " + " nodes unkown: " + countUnkown);
	}

	private List<Action> search(OOMDPState initState, final Goal goal) {
		Path initPath = new Path();
		initPath.currentState = initState;

		Queue<Path> openPaths = new LinkedList<>();
		openPaths.add(initPath);

		while (!openPaths.isEmpty()) {
			System.out.println(openPaths);
			final Path path = openPaths.poll();
			assert (path != null);
			Action currentAction = path.actions.peek();

			if (currentAction != null && currentAction.equals(goal.getAction())) { // path is a goal) {
				ArrayList<Action> result = new ArrayList<Action>();
				result.addAll(path.getActions());
				return result;
			} else {
				for (Map.Entry<Action, Node> key : this.nodes.get(path.currentState).getChildrens().entrySet()) {
					Node node = key.getValue();
					if (node == null) {
						if (goal.getAction() == null) {
							path.actions.add(key.getKey());
							ArrayList<Action> result = new ArrayList<Action>();
							result.addAll(path.getActions());
							return result;
						} else
							continue;
					}
					Path currentPath = path.copy();
					if (currentPath.alreadyVisited(node.getValue())) {// i,pede loops
						System.out.println("Node already visited for path. Ceifando caminho");
						openPaths.add(path);
						continue;
					}

					currentPath.addNewState(node.getValue());
					currentPath.actions.add(key.getKey());

					// check if state is already in some path
					boolean addPath = true;
					for (Path checkPath : openPaths) {
						if (checkPath.alreadyVisited(node.getValue())) {
							if (checkPath.length() < currentPath.length()) {
								openPaths.remove(checkPath);
								break;
							} else if (checkPath.length() > currentPath.length()) {
								addPath = false;
								System.out.println(
										"Path: " + currentPath + " node added because\n" + checkPath + " is smaller");
							}else if(checkPath.length() == currentPath.length())
								addPath = false;
						}
					}
					if (addPath)
						openPaths.add(currentPath);
				}
			}
		}
		return null;
	}

	public List<Action> planForGoal(final Goal goal, final OOMDPState initState, final DOORMax doormax) {
		System.out.println("Initing Planning");
		assert (initState != null);
		assert (doormax != null);
		assert (goal != null);

		if (this.nodes.get(initState) == null) {
			this.nodes.put(initState, new Node(initState, this.actions));
		}

		this.expandNodes(doormax);
		
		return this.search(initState, goal);
	}
}

class Path {
	Queue<Action> actions;
	OOMDPState currentState;
	List<OOMDPState> states;

	public int length() {
		return this.actions.size();
	}

	public boolean alreadyVisited(OOMDPState state) {
		return this.states.contains(state);
	}

	public Path() {
		this.actions = new ArrayDeque<>();
		this.currentState = null;
		this.states = new ArrayList<OOMDPState>();
	}

	public void addNewState(OOMDPState state) {
		this.currentState = state;
		this.states.add(state);
	}

	public Path copy() {
		Path path = new Path();
		path.actions = new ArrayDeque<>();
		path.actions.addAll(this.actions);

		path.states = new ArrayList<>();
		path.states.addAll(this.states);
		path.currentState = this.currentState;

		return path;
	}

	public OOMDPState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(OOMDPState currentState) {
		this.currentState = currentState;
	}

	public Queue<Action> getActions() {
		return actions;
	}

	public List<OOMDPState> getStates() {
		return states;
	}

	@Override
	public String toString() {
		return "Path [actions=" + actions + "]";
	}

}