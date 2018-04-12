package tomlg.doormax;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.OOMDP;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectClass;
import tomlg.doormax.oomdpformalism.ObjectInstance;

class DoormaxState {
	// conditions under which that action has been observed to not effect that
	// attribute of that object instance.
	// Each of these sets is essentially a tabular set of failure conditions for
	// when a does not change
	// att of any instances of oClass
	public Map<FailureConditions, Set<Condition>> f;

	// body of predictions
	public List<Prediction> α;

	// for the contradictory (effect types, object class, attribute, action) tuples
	public Set<Prediction> ω;

}

public class Doormax {
	public final OOMDP oomdp;
	private OOMDPState actualState;
	private final int maxEffects;
	private DoormaxState doormaxState;
	public final double epsilon;

	public Doormax(OOMDP oomdp, OOMDPState initialState, int maxEffects, double epsilon) {
		super();
		this.oomdp = oomdp;
		this.actualState = initialState;
		this.maxEffects = maxEffects;
		this.epsilon = epsilon;
		this.doormaxState = new DoormaxState();
		initialize();
	}

	/// TODO output effects space

	/**
	 * Initialize: - failure conditions F - prediction set α and ruled out
	 * prediction set ω
	 */
	private void initialize() {
		this.doormaxState.f = new HashMap<FailureConditions, Set<Condition>>();
		for (Action action : this.oomdp.actions) {
			for (ObjectClass objClass : oomdp.objectClasses) {
				for (ObjectAttribute attribute : objClass.attributes) {
					this.doormaxState.f.put(new FailureConditions(action, attribute, objClass),
							new HashSet<Condition>());
				}
			}
		}

//		this.doormaxState.α = new LinkedHashSet<Prediction>();
		this.doormaxState.ω = new HashSet<Prediction>();
	}

	public void step() {
		// Doormax Prediction

		// upates pi/planner

		// nextAction← π(currentState)

		// newState ← nextAction(currentState)
		// DOORMAXLearn(currentState, nextAction, newState)
		// currentState ← newState
	}

	/**
	 *  
	 * @param s0 estado inicial
	 * @param a ação tomada
	 * @param sf espaço de estados quando a ação a foi tomada no estado s0
	 */
	private void learn(OOMDPState s0, Action action, OOMDPState sf) {
		
		//laço objetoi s x objetoi s'
		for(ObjectInstance[] objInstance : s0.matchObjects(sf)){
			//para todos os attributos da classe do objeto_id
			for(ObjectAttribute att :objInstance[0].objectClass.attributes) {
				//Se os valores dos atributos de um objeto não mudaram nos 
				//dois estados, então DOORMAX adiciona a representação de 
				//condição de s a lista de condições falhas (action, objClass, att(objClass))
				
				// TODO colocar tratamento de exceção
				// a única forma de dar exceção é se um objeto foi criado
				//ou desapareceu em relação ao estado antigo
				if(objInstance[0].attributesVal.get(att) == objInstance[0].attributesVal.get(1)) {
					FailureConditions key = new FailureConditions(action, att, objInstance[0].objectClass);
					this.doormaxState.f.get(key).add(s0.toCondition());
				}//else 
					//se não for uma condição de falha, então atualizar as condições para as predições que predizem os efeitos apropriados
					//através da operação bitwise entre a condição do espaço s'.
					//Se não existirem predições, então inicializar uma nova predição com o efeito correspondente e a representação da condição de s
					//para o novo preditor.

//					for(Effect hypEffect : Effect.possibleEffectsExplanation(objInstance[0], objInstance[1], att){
						//Check for existing predictions to update
						//TODO mudar a estruta de dados de alfa
//						if(this.doormaxState.α.contains(predition)) {
						//	UPdate prediction contition
//							this.doormaxState.α.get(hypEffect).condition.bitwise(s0.toCondition());
							
							
							
//						}
						
						//Lastly, we rule out contradictory
//						object class, attribute, action, effect type tuples: namely those for which there are more than k predictions
//						and those which have predictions with overlapping conditions
//					}
				}
			
		}
	}

	private void predict() {

	}
}

class FailureConditions {
	public final Action action;
	public final ObjectAttribute attribute;
	public final ObjectClass objClass;

	public FailureConditions(Action action, ObjectAttribute attribute, ObjectClass objClass) {
		super();
		this.action = action;
		this.attribute = attribute;
		this.objClass = objClass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + ((objClass == null) ? 0 : objClass.hashCode());
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
		FailureConditions other = (FailureConditions) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		if (objClass == null) {
			if (other.objClass != null)
				return false;
		} else if (!objClass.equals(other.objClass))
			return false;
		return true;
	}
}
