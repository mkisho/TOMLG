package tomlg.planner;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

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

	// private void addNewNode(OOMDPState state) {
	// Node node = new Node(state, this.actions);
	// nodes.put(state, node);

	// System.out.println("Planner\nNew State Added");
	// }

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
			System.out.println("Expanding nodes>> total: " + countTotalExpanded);

			while (nodesToExpand.size() > 0) {
				Node node = nodesToExpand.poll();

				Map<Action, OOMDPState> predictions = doormax.predictOOMDPStates(node.getValue(),
						node.getUnkownActions());
				for (Action action : predictions.keySet()) {
					OOMDPState newState = predictions.get(action);

					if (newState == null) {
						countUnkown++;
					} else {
						if (!this.nodes.containsKey(newState) && !node.getValue().equals(newState)) {
							countExpanded++;
							Node newNode = new Node(newState, this.actions);
							this.nodes.put(newState, newNode);
							node.putChildren(action, newNode);
							nodesToExpand.add(newNode);
						} else {
							node.putChildren(action, node);
<<<<<<< HEAD
							// if(node.getValue().equals(newState))
							// node.getChildrens().remove(action);
=======
							if (!node.getValue().equals(newState))
								node.getChildrens().remove(action);
>>>>>>> branch 'master' of https://github.com/mkisho/TOMLG
						}
					}
				}
			}
			countTotalExpanded += countExpanded;
		} while (countExpanded != 0);
		System.out.println(">> " + countTotalExpanded + " nodes expanded " + " nodes unkown: " + countUnkown);
	}

	/**private void UniformCostSearch(Path source, Goal goal) {

//		source.pathCost = 0;
		PriorityQueue<Path> queue = new PriorityQueue<Path>(20, new Comparator<Path>() {
			// override compare method
			public int compare(Path i, Path j) {
				if (i.pathCost() > j.pathCost()) {
					return 1;
				} else if (i.pathCost() < j.pathCost()) {
					return -1;
				} else {
					return 0;
				}
			}
		});

		queue.add(source);
		Set<Node> explored = new HashSet<Node>();
		boolean found = false;

		// while frontier is not empty
		do {

			Path current = queue.poll();
			explored.add(current);

			if (current.getCurrentState() == null) {
				found = true;
			}

			for (Node e : this.nodes.get(current.currentState).getChildrens()) {
				Node child = e.target;
				double cost = e.cost;
				child.pathCost = current.pathCost + cost;

				if (!explored.contains(child) && !queue.contains(child)) {

					child.parent = current;
					queue.add(child);

					System.out.println(child);
					System.out.println(queue);
					System.out.println();

				} else if ((queue.contains(child)) && (child.pathCost > current.pathCost)) {
					child.parent = current;

					// the next two calls decrease the key of the node in the queue
					queue.remove(child);
					queue.add(child);
				}

			}
		} while (!queue.isEmpty());
	}*/

	private List<Action> search(OOMDPState initState, final Goal goal) {
		Path initPath = new Path();
		initPath.currentState = initState;
		initPath.addNewState(initState);

		Queue<Path> openPaths = new LinkedList<>();
		openPaths.add(initPath);
		List<Path> possiblePath = new ArrayList<Path>();

		System.out.println("State>> " + initState);

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
					System.out.println(">>>Visiting Node " + node + " with action" + key.getKey());

					if (node == null) {
						if (goal.getAction() == null) {
							path.actions.add(key.getKey());
							ArrayList<Action> result = new ArrayList<Action>();
							result.addAll(path.getActions());
							return result;
						} else
							continue;
					}
					System.out.println(">>>Childrens Nodes" + node.getChildrens() + " with action" + key.getKey());

					Path currentPath = path.copy();
					if (currentPath.alreadyVisited(node.getValue())) {// i,pede loops
<<<<<<< HEAD
						System.out.println("Node " + node.getValue() + " already visited for path" + currentPath
								+ " Ceifando caminho");
						System.out.println(key.getKey());

						// openPaths.add(path);
=======
						System.out.println("Node already visited for path. Ceifando caminho");
//						openPaths.add(path);
						possiblePath.add(path);
>>>>>>> branch 'master' of https://github.com/mkisho/TOMLG
						continue;
					}

					currentPath.addNewState(node.getValue());
					currentPath.actions.add(key.getKey());

					// check if state is already in some path
					boolean addPath = true;
					for (Path checkPath : openPaths) {
						if (checkPath.alreadyVisited(node.getValue())) {
							if (checkPath.length() > currentPath.length()) {
								openPaths.remove(checkPath);
								break;
							} else if (checkPath.length() < currentPath.length()) {
								addPath = false;
<<<<<<< HEAD
								System.out.println("Path: " + currentPath + " not node added because\n" + checkPath
										+ " is smaller");
=======
								System.out.println(
										"Path: " + currentPath + " node added because\n" + checkPath + " is smaller");
>>>>>>> branch 'master' of https://github.com/mkisho/TOMLG
							} else if (checkPath.length() == currentPath.length())
								addPath = false;
						}
					}
					if (addPath)
						openPaths.add(currentPath);
				}
			}
		}
		List<Action> result = new ArrayList<Action>();
		result.addAll(possiblePath.get(0).getActions());
		return result;
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

	public int pathCost() {
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