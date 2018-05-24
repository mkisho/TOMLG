package tomlg.doormax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tomlg.doormax.effects.EffectType;
import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectClass;
import tomlg.doormax.utils.Log;
import tomlg.doormax.utils.Quadruple;

/**
 * body of predictions
 * 
 * @author chronius
 *
 */
public class AlphaPredictionsSet {
	private Map<Quadruple<Action, EffectType, ObjectAttribute, ObjectClass>, Map<Effect, Prediction>> α;
	
	
	
	public AlphaPredictionsSet() {
		super();
		Log.info("Creating α Set");
		this.α = new HashMap<Quadruple<Action, EffectType, ObjectAttribute, ObjectClass>, Map<Effect, Prediction>>();
		Log.info("α Set created");
	}


	public void add(final Action action, final Effect effect, final ObjectAttribute att, final ObjectClass objClass,
			Prediction pred) {
		Log.info("Adding prediction to α Set("+pred+")");
		Map<Effect, Prediction> temp = new HashMap<Effect, Prediction>();
		temp.put(effect, pred);
		this.α.put(new Quadruple<Action, EffectType, ObjectAttribute, ObjectClass>(action, effect.type, att, objClass),
				temp);
	}
	
	
	public Set<Prediction> related(final Prediction pred){
		Log.info("Searching for related prediction on α Set("+pred+")");

		final Map<Effect, Prediction> preds = this.α.get(
				new Quadruple<Action, EffectType, ObjectAttribute, ObjectClass>(pred.action, pred.effect.type, pred.effect.attribute, 
						pred.effect.objectClass));
		final Set<Prediction> result = new HashSet<Prediction>();
		for (Prediction p : preds.values()) {
			result.add(p);
		}
		return result;
	}
	
	public Map<Effect, Prediction> checkExistingPrediction(Effect eff, Action a) {
		Log.info("Checking for existing prediction on α Set("+eff+")");

		final Map<Effect, Prediction> preds = this.α.get(
				new Quadruple<Action, EffectType, ObjectAttribute, ObjectClass>(a, eff.type, eff.attribute, 
							eff.objectClass));
		return preds;
	}
	
	public void remove(Set<Prediction> predset) {
		Log.info("Removing Set of related Prediction from α Set("+predset+")");

		for (Prediction pred : predset){
			final Map<Effect, Prediction> preds = this.α.get(
					new Quadruple<Action, EffectType, ObjectAttribute, ObjectClass>(pred.action, pred.effect.type, pred.effect.attribute, 
							pred.effect.objectClass));
			
			List<Effect> toRemove = new ArrayList<Effect>();
			for (Effect eff: preds.keySet()) {
				if(preds.get(eff).equals(pred)) {
					toRemove.add(eff);
				}
			}
			for (Effect eff: toRemove)
				preds.remove(eff);
		}
	}

	public Map<Effect, Prediction> relatedOverlap(EffectType eType, ObjectClass objectClass,
			ObjectAttribute att, Condition condition) {
		// TODO Auto-generated method stub
		return null;
	}
}
