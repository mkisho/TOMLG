package tomlg.planner;

import java.util.List;
import java.util.Queue;

public class BreadthFirstSearcher extends Searcher {
	private Queue<SearchNode> nodeQueue;

	@Override
	public void addNode(SearchNode node) {
		nodeQueue.add(node);
	}

	@Override
	public void addNodes(List nodes) {
		this.nodeQueue.addAll(nodes);
	}

	@Override
	public boolean hasMoreNodes() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SearchNode nextNode() {
		return nodeQueue.remove();
	}

	private boolean isGoal(SearchNode node) {
		return node.getState() == null;
	}

	SearchNode findSolution(Searcher searcher,
			SearchNode initialNode, int maxDepth) {
		int numNodesVisited = 0;
		int maxDepthReached = 0;
		
		searcher.addNode(initialNode);
		while (searcher.hasMoreNodes()) {
			SearchNode node = searcher.nextNode();
			if (isGoal(node))
				return node;
			if (!searcher.depthLimitReached(node))
				searcher.addNodes(node.getChildrens());
		}
		return null;
	}

}
