package tomlg.doormax;

import java.util.List;

public class Intention {
	public List<Effect> effects;
	int lifeTime;
	float fitness;
	//List<boolean> performance;
	public Intention() {
//		super();
		this.effects = null;
		this.lifeTime = 0;
		this.fitness = 0;
	}
	public int getLifeTime() {
		return lifeTime;
	}
	public void incLifeTime() {
		
		if(lifeTime<10) {
			lifeTime++;
		}
		
	}
	public float getFitness() {
		return fitness;
	}
	public void setFitness(float fitness) {
		this.fitness = fitness;
	}
	@Override
	public boolean equals(Object arg0) {
		Intention a = (Intention) arg0;
		return this.effects.equals(a.effects);
	}
	public void add(Effect effect) {
		this.effects.add(effect);
		
		
	}

	
}
