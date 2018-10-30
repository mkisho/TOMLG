package tomlg.planner;

import java.util.List;

/**
 * https://sites.fas.harvard.edu/~cscie119/lectures/search.pdf
 * @param node
 * @return
 */
public abstract class Searcher {
	private int depthLimit;

	public abstract void addNode(SearchNode node);

	public abstract void addNodes(List nodes);

	public abstract boolean hasMoreNodes();

	public abstract SearchNode nextNode();

	public void setDepthLimit(int limit) {
		depthLimit = limit;
	}

	public boolean depthLimitReached(SearchNode node) { 
		return node.getCostFromStart() > depthLimit;
		
	}
}
