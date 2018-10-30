package tomlg.goallearning;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import doormax.structures.Condition;
import doormax.structures.Effect;
import tomlg.Intention;

public class ActionsEpisodeHistory implements Serializable {
	private static final long serialVersionUID = 4493674631535417103L;

	private List<ActionEffectConditionTuple> actionHistory;

	public ActionsEpisodeHistory() {
		this.actionHistory = new ArrayList<ActionEffectConditionTuple>();
	}

	public void addAction(Intention intention, List<Effect> effects, Condition condition) {
		this.actionHistory.add(new ActionEffectConditionTuple(intention, effects, condition));
	}

	public List<ActionEffectConditionTuple> getActionHistory() {
		return actionHistory;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ActionsEpisodeHistory [actionHistory=");
		builder.append(actionHistory);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * TODO para propósitos de teste. ELIMINAR na versão final
	 */
	public void toFile() {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("actionHistory.txt");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(this);
			objectOutputStream.flush();
			objectOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Retorna a lista, preservando sua ordem, mas revendo as ações que não geraram
	 * nenhum tipo de efeito.
	 * 
	 * @return
	 */
	public ActionsEpisodeHistory getActionsWithEffectsOnly() {
		ActionsEpisodeHistory withEffects = new ActionsEpisodeHistory();
		for (ActionEffectConditionTuple tuple : this.actionHistory) {
			if (!tuple.getEffects().isEmpty())
				withEffects.actionHistory.add(tuple);
		}

		return withEffects;
	}
	
	

}
