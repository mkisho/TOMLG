package doormax.structures;

import java.util.Arrays;

import doormax.OOMDPState;
import doormax.PropositionalFunction;

public class Condition {

	private PropositionalFunction[] pfIndex;
	private char[] eval;

	public Condition(PropositionalFunction[] pfIndex) {
		this.pfIndex = pfIndex;
	}

	public PropositionalFunction[] getPfIndex() {
		return pfIndex;
	}

	public void setPfIndex(PropositionalFunction[] pfIndex) {
		this.pfIndex = pfIndex;
	}

	public char[] getEval() {
		return eval;
	}

	public static Condition evaluate(PropositionalFunction[] pfIndex, OOMDPState state) {
		char eval[] = new char[pfIndex.length];

		for (int i = 0; i < eval.length; i++) {
			eval[i] = pfIndex[i].evaluate(state) ? '1' : '0';
		}

		Condition cond = new Condition(pfIndex);
		cond.eval = eval;
		return cond;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(eval);
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
		Condition other = (Condition) obj;
		if (!Arrays.equals(eval, other.eval))
			return false;
		return true;
	}

	public Condition bitwise(Condition cond) {
		char[] newEval = new char[pfIndex.length];

		for (int i = 0; i < newEval.length; i++) {
			char bit;
			if (this.eval[i] == cond.eval[i]) {
				bit = this.eval[i];
			} else
				bit = '*';
			newEval[i] = bit;
		}

		Condition updated = new Condition(pfIndex);
		updated.eval = newEval;
		return updated;
	}

	public boolean matches(Condition condition) {
		for (int i = 0; i < this.eval.length; i++) {
//			if for every index either both conditions
//			have the same bit or the first condition has a * at that index.
			if (this.eval[i] == condition.eval[i])
				continue;
			else if (this.eval[i] == '*')
				continue;
			else
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(Arrays.toString(eval));
//		builder.append("  PFS: ");
		
//		builder.append(Arrays.toString(this.pfIndex));
		return builder.toString();
	}

	public Boolean getEvalOfPropositionalFunction(PropositionalFunction f) {
		for (int i = 0; i < this.pfIndex.length; i++) {
			if (this.pfIndex[i].equals(f)) {
				return (this.eval[i] == '1' ? true : false);
			}
		}

		return null;
	}

}
