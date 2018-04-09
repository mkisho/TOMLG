package tomlg.doormax;

import java.util.Arrays;

import tomlg.doormax.oomdpformalism.OOMDPState;

/**
 * Defines a Condition as in the OO-MDP formalism
 * 
 * A condition is a bit string where each bit represents the truth value of one
 * of the propositional functions in the OO-MDP. The propositional functions are
 * always evaluated in the same order to produce the condition. From Leveraging
 * and Learning Propositional Functions for Large State Spaces in Planning and
 * Reinforcement Learning, pg 28
 * 
 * @author chronius
 *
 */
public class Condition {
	public final PropositionalFunction[] bitStringPropositionsIndex;
	public final String conditionEvaluated;
	public final char[] conditionBitArray;

	public Condition(PropositionalFunction[] bitStringPropositionsIndex, String conditionEvaluated) {
		super();
		this.bitStringPropositionsIndex = bitStringPropositionsIndex;
		this.conditionEvaluated = conditionEvaluated;
		this.conditionBitArray = this.conditionEvaluated.toCharArray();
	}

	@Override
	public String toString() {
		return "Condition [bitStringPropositionsIndex=" + Arrays.toString(bitStringPropositionsIndex)
				+ ", conditionEvaluated=" + conditionEvaluated + "]";
	}

	/**
	 * One condition matches another condition, denoted cond 1 |= cond 2 , if for
	 * every index either both conditions have the same bit or the first condition
	 * has a * at that index. Note that this means that matching is not a symmetric
	 * operator
	 * 
	 */
	public boolean match(Condition condition) {
		if (condition.conditionEvaluated.length() != conditionEvaluated.length()) {
			/// TODO gerar uma exceção. Sobre hipotese alguma esse caso deve ocorrer
			/// se ocorrer indica problemas no algoritmo
			return false;
		}

		for (int index = 0; index < conditionEvaluated.length(); index++) {
			if (this.conditionBitArray[index] == condition.conditionBitArray[index]) {
				continue;
			} else if (this.conditionBitArray[index] == '*')
				continue;
			else
				return false;
		}
		return true;
	}

	/**
	 * Realiza a operação de bitwise entre string de bits de condições
	 * 
	 * @param condition
	 * @return
	 */
	public String bitwise(Condition condition) {
		if (condition.conditionEvaluated.length() != conditionEvaluated.length()) {
			/// TODO gerar uma exceção. Sobre hipotese alguma esse caso deve ocorrer
			/// se ocorrer indica problemas no algoritmo
			return null;
		}

		final char[] resultString = new char[conditionEvaluated.length()];

		for (int index = 0; index < conditionEvaluated.length(); index++) {
			if (this.conditionBitArray[index] == condition.conditionBitArray[index]) {
				resultString[index] = this.conditionBitArray[index];
			} else
				resultString[index] = '*';
		}
		return resultString.toString();
	}

	public boolean overlaps(Condition condition) {
		if (match(condition)) {
			return true;
		} else if (condition.match(this))
			return true;
		else
			return false;
	}
}
