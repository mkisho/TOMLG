package tomlg.doormax.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tomlg.doormax.Effect;
import tomlg.doormax.Intention;
//import tomlg.doormax.FailureConditions;
import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import java.util.concurrent.ThreadLocalRandom;

public class IntentionLearner  {
	int intention = 0;
	int id=0;
	int totalNumberOfEffects=0;
	int gameNumberOfEffects=0;
	int MAX=10;
	List<Intention> validIntentions=new ArrayList<Intention>();
	List<Intention> hypothesis=new ArrayList<Intention>();
	
	
	List<Effect> intentionEffectList=new ArrayList<Effect>();
	List<Effect> gameEffectList=new ArrayList<Effect>();
	
	Map<Effect, Integer> effectRatio = new HashMap<Effect,Integer>();
	Map<Effect, Integer> gameEffectRatio = new HashMap<Effect,Integer>();
	
	
	public IntentionLearner() {
	}
	
	
	void addNewMoveToList(int game_id, Action a, Effect e, ObjectAttribute att) {
		if(game_id==id) {
				gameEffectList.add(e);
		}
		else {
			id=game_id;
			gameEffectList.clear();
			gameEffectList.add(e);
		}
	}
	
	
	void createNewIntentions() {
		//Create random hypothesis from valid intention
		if(hypothesis.size()<MAX){ //limits the number of hypothesis
			Intention newIntention=new Intention();
			if(!validIntentions.isEmpty()) {
				//Choose random validIntention
				int rand = ThreadLocalRandom.current().nextInt(0, validIntentions.size());
				Intention chosenIntention;
				chosenIntention = validIntentions.get(rand);
				//choose random if remove or add new effect
				//if remove, take off randomly
				if(ThreadLocalRandom.current().nextBoolean()) {
					rand = ThreadLocalRandom.current().nextInt(0, chosenIntention.effects.size());
					chosenIntention.effects.remove(rand);
					addHypothesis(chosenIntention);
					
				}
				//if add, Choose random if from a weighted effectRatio or crossover with other valid/hypothesis 
				else {
					if(ThreadLocalRandom.current().nextBoolean()) {
						//Create a weighted list, with the effects that appeared less having more entries
						List<Effect> weightedList=new ArrayList<Effect>();
						for (Map.Entry<Effect, Integer> entry : effectRatio.entrySet()){
							float a=totalNumberOfEffects/entry.getValue();
							//supposing a<1;
							for(int i=0; i<a; i++) {
								weightedList.add(entry.getKey());
							}
						}
						//choose a random effect
						rand = ThreadLocalRandom.current().nextInt(0, weightedList.size());
						chosenIntention.add(weightedList.get(rand));
						addHypothesis(chosenIntention);
						
					}
					else {
						//CROSS-OVER
						List<Intention> tmpList=new ArrayList<Intention>();
						for(Intention tmpInt :validIntentions) {
							if(!tmpInt.equals(chosenIntention)) {
								tmpList.add(tmpInt);
							}
						}
						for(Intention tmpInt :hypothesis) {
							tmpList.add(tmpInt);
						}
						rand = ThreadLocalRandom.current().nextInt(0, tmpList.size());
						int size1 = tmpList.get(rand).effects.size();
						int size2 = chosenIntention.effects.size();
						int size = (size1+size2)/2;
						
						for(int i=0; i<size; i++) {
							if(ThreadLocalRandom.current().nextBoolean() && i<size1) {
								newIntention.effects.add(tmpList.get(rand).effects.get(i));
							}
							else if(i<size2){
								newIntention.effects.add(chosenIntention.effects.get(i));
							}
						}
						addHypothesis(newIntention); 
					}
				}

	
			}
			//Create random hypothesis from hypothesis
			if(!hypothesis.isEmpty()) {
			
				//Choose random validIntention
				int rand = ThreadLocalRandom.current().nextInt(0, hypothesis.size());
				Intention chosenIntention;
				chosenIntention = hypothesis.get(rand);
				//choose random if remove or add new effect
				//if remove, take off randomly
				if(ThreadLocalRandom.current().nextBoolean()) {
					rand = ThreadLocalRandom.current().nextInt(0, chosenIntention.effects.size());
					chosenIntention.effects.remove(rand);
					addHypothesis(chosenIntention);
					
				}
				//if add, Choose random if from a weighted effectRatio or crossover with other valid/hypothesis 
				else {
					if(ThreadLocalRandom.current().nextBoolean()) {
						//Create a weighted list, with the effects that appeared less having more entries
						List<Effect> weightedList=new ArrayList<Effect>();
						for (Map.Entry<Effect, Integer> entry : effectRatio.entrySet()){
							float a=totalNumberOfEffects/entry.getValue();
							//supposing a<1;
							for(int i=0; i<a; i++) {
								weightedList.add(entry.getKey());
							}
						}
						//choose a random effect
						rand = ThreadLocalRandom.current().nextInt(0, weightedList.size());
						chosenIntention.add(weightedList.get(rand));
						addHypothesis(chosenIntention);
						
					}
					else {
						//CROSS-OVER
						List<Intention> tmpList=new ArrayList<Intention>();
						for(Intention tmpInt :validIntentions) {
							if(!tmpInt.equals(chosenIntention)) {
								tmpList.add(tmpInt);
							}
						}
						for(Intention tmpInt :hypothesis) {
							tmpList.add(tmpInt);
						}
						rand = ThreadLocalRandom.current().nextInt(0, tmpList.size());
						int size1 = tmpList.get(rand).effects.size();
						int size2 = chosenIntention.effects.size();
						int size = (size1+size2)/2;
						
						for(int i=0; i<size; i++) {
							if(ThreadLocalRandom.current().nextBoolean() && i<size1) {
								newIntention.effects.add(tmpList.get(rand).effects.get(i));
							}
							else if(i<size2){
								newIntention.effects.add(chosenIntention.effects.get(i));
							}
						}
						addHypothesis(newIntention); 
					}
				}
			}
			//Choose a random game intention gameEffectList
			int size = ThreadLocalRandom.current().nextInt(0, gameEffectList.size());
			List<Effect> weightedList=new ArrayList<Effect>();
			newIntention.effects.clear();
			for (Map.Entry<Effect, Integer> entry : gameEffectRatio.entrySet()){
				float a=gameNumberOfEffects/entry.getValue();
				//supposing a<1;
				for(int j=0; j<a; j++) {
					weightedList.add(entry.getKey());
				}
			}
			for(int i=0; i<size; i++) {
				int rand = ThreadLocalRandom.current().nextInt(0, weightedList.size());
				newIntention.add(weightedList.get(rand));
				addHypothesis(newIntention);
			}
			
			
			
			
			
			//Choose a random allGamesEffectList
			size = ThreadLocalRandom.current().nextInt(0, effectRatio.keySet().size());
			newIntention.effects.clear();
			weightedList = new ArrayList<Effect>();
			for (Map.Entry<Effect, Integer> entry : effectRatio.entrySet()){
				float a=totalNumberOfEffects/entry.getValue();
				//supposing a<1;
				for(int j=0; j<a; j++) {
					weightedList.add(entry.getKey());
				}
			}
			for(int i=0; i<size; i++) {
				int rand = ThreadLocalRandom.current().nextInt(0, weightedList.size());
				newIntention.add(weightedList.get(rand));
				addHypothesis(newIntention);
			}
		}
		
	}
	
	float evaluate (OOMDPState s0, Intention i) {
		float overallFitness = i.getFitness();
		float fitness = 0;
		float life = i.getLifeTime();
		//TODO dar nota para o fitness atual;
		// fitness = plannerReward(s0, i.effects){
		//}
		overallFitness =(overallFitness*(life-1)+fitness)/life;
		return overallFitness ;
	}
	
	
	
	void validateIntentions(OOMDPState s0){
		//Validates valid intentions 
		for(int i=0; i<validIntentions.size();i++) {
			validIntentions.get(i).incLifeTime();
			float fitness;
			fitness = evaluate(s0, validIntentions.get(i));
			validIntentions.get(i).setFitness(fitness);
		}
		for(int i=0; i<hypothesis.size();i++) {
			hypothesis.get(i).incLifeTime();
			float fitness;
			fitness = evaluate(s0, hypothesis.get(i));
			hypothesis.get(i).setFitness(fitness);
		}
		
		
		
		
		if(validIntentions.isEmpty()) {
			for(Intention tmpInt :validIntentions) {
				//Se ficar ruim -> rebaixa pra hipotese
				if(tmpInt.getFitness()<0.5){
					validIntentions.remove(tmpInt);
					hypothesis.add(tmpInt);
				}
				
			}
		}
		//validate hypothesis
		if(hypothesis.isEmpty()) {
			for(Intention tempHypo :hypothesis) {
				//Se muito boa -> valid intention
				if(tempHypo.getFitness()>0.5 && tempHypo.getLifeTime() >5){
					hypothesis.remove(tempHypo);
					validIntentions.add(tempHypo);
				}
				//Se muito ruim/muito tempo -> remove
				if(tempHypo.getFitness()<0.3 && tempHypo.getLifeTime() >3 || 
						tempHypo.getFitness()<0.5 && tempHypo.getLifeTime() >10){
				hypothesis.remove(tempHypo);
				}
			}
		}
		

	}
	
	void addHypothesis(Intention chosenIntention) {
		if (!chosenIntention.effects.isEmpty()) {
			boolean checkIfNew=true;
			for(Intention tmp :hypothesis) {
				if(tmp.equals(chosenIntention)) {
					checkIfNew=false;
					break;
				}
			}
			for(Intention tmp :validIntentions) {
				if(tmp.equals(chosenIntention)) {
					checkIfNew=false;
					break;
				}
			}
			if(checkIfNew) {
				hypothesis.add(chosenIntention); 
			}
		}
	}
	
	
	void updateWeights() {
		gameNumberOfEffects=0;
		
		for(Effect e : gameEffectRatio.keySet()) {
			gameEffectRatio.put(e, 0);
		}
		for(Effect e : gameEffectList) {
			gameNumberOfEffects++;
			totalNumberOfEffects++;
			Integer tmp=effectRatio.get(e);
			if (tmp == null) {
				effectRatio.put(e, 1);
			}
			else {
				effectRatio.put(e, tmp + 1);
			}
			
			tmp=gameEffectRatio.get(e);
			if (tmp == null) {
				gameEffectRatio.put(e, 1);
			}
			else {
				gameEffectRatio.put(e, tmp+1);
			}
			
			
		}
		
	}
}
