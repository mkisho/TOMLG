package tomlg.doormax.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tomlg.doormax.Effect;
import tomlg.doormax.Intention;
//import tomlg.doormax.FailureConditions;
import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import java.util.concurrent.ThreadLocalRandom;

public class IntentionLearner  {
	int intention = 0;
	int id=0;
	int totalNumberOfEffects=0;
	int MAX=10;
	List<Intention> validIntentions=new ArrayList<Intention>();
	List<Intention> hypothesis=new ArrayList<Intention>();
	
	
	List<Effect> intentionEffectList=new ArrayList<Effect>();
	List<Effect> gameEffectList=new ArrayList<Effect>();
	
	Map<Effect, Integer> effectRatio = new HashMap<Effect,Integer>();
	
	
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
					if (!chosenIntention.effects.isEmpty()) {
						boolean checkIfNew;
						for(Intention tmp :hypothesis) {
							
						}
						
						
						
						
						
						//hypothesis.add(tmpInt); //TODO Check if it's not there already
					}
					
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
						//TODO add weightedList.get(rand);
						
						
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
						//hypothesis.add(newIntention); //TODO Check if it's not there already
					}
				}

				//if add, Choose random if from weighted gameEffectList or crossover with other valid/hypothesis 
				//Add random effect
	
			}
			//Create random hypothesis from hypothesis
			if(!hypothesis.isEmpty()) {
			//same as valid
			}
			//Choose a random game intention gameEffectList
			//rand(gameEffectList.size())
			//
			//Choose a random allGamesEffectList
			//
		}
		
	}
	
	
	void validateIntentions(){
		//Validates valid intentions 
		for(int i=0; i<validIntentions.size();i++) {
			validIntentions.get(i).incLifeTime();
			//TODO update evaluation
			//validIntentions.setFitness();
		}
		for(int i=0; i<hypothesis.size();i++) {
			hypothesis.get(i).incLifeTime();
			//TODO update evaluation
			//hypothesis.setFitness();
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
	
	void updateWeights() {
//		int count=0;
		for(Effect e : gameEffectList) {
//			count++;
			totalNumberOfEffects++;
			Integer tmp=effectRatio.get(e);
			if (tmp == null) {
				effectRatio.put(e, 1);
			}
			else {
				effectRatio.put(e, tmp + 1);
			}
		}
//		for(Effect e : effectRatio.keySet()) {
//			float tmp=effectRatio.get(e);
//			    effectRatio.put(e, 1-(tmp/count));
//		}
	}
}
