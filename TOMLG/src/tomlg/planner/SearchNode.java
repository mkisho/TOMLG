package tomlg.planner;

import java.util.List;

/**
 * FROM https://sites.fas.harvard.edu/~cscie119/lectures/search.pdf
 * 
 * @author chronius
 *
 */
public class SearchNode {
	private Object state;
	private SearchNode predecessor;
	private String operator;
	private int numSteps;
	private double costFromStart;
	private double costToGoal;

	public Object getState() {
		return state;
	}

	public void setState(Object state) {
		this.state = state;
	}

	public SearchNode getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(SearchNode predecessor) {
		this.predecessor = predecessor;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getNumSteps() {
		return numSteps;
	}

	public void setNumSteps(int numSteps) {
		this.numSteps = numSteps;
	}

	public double getCostFromStart() {
		return costFromStart;
	}

	public void setCostFromStart(double costFromStart) {
		this.costFromStart = costFromStart;
	}

	public double getCostToGoal() {
		return costToGoal;
	}

	public void setCostToGoal(double costToGoal) {
		this.costToGoal = costToGoal;
	}

	public SearchNode(Object state, SearchNode predecessor, String operator) {
		super();
		this.state = state;
		this.predecessor = predecessor;
		this.operator = operator;
	}

	public List getChildrens() {
		// TODO Auto-generated method stub
		return null;
	}

}