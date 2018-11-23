package tomlg.tom;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import doormax.OOMDP;
import doormax.OOMDPState;
import doormax.structures.Action;
import doormax.structures.Condition;
import tomlg.Environment;
import tomlg.Intention;

public class AgentTOM implements Serializable {
	private static final long serialVersionUID = -95505017414567460L;

	private MindTOM mind;

	private String name;
//	private transient SensoryMonitor sensories;
//	private transient AgentActuator bodyActuators;
	private transient OOMDP oomdp;

	public AgentTOM(String name, Environment environment, OOMDP oomdp) {
		this.name = name;
		System.out.println("Initializing agent: " + name);
//		this.sensories = new SensoryMonitor(environment);
//		this.bodyActuators = new AgentActuator(environment);
		this.oomdp = oomdp;
		this.initializeMind();
	}

	private void initializeMind() {
		this.mind = new MindTOM(this.name, this.oomdp);
	}

	public List<Intention> calculateTOM(List<Observation> observations) {
		assert (observations.get(0).getAction() == null);
		
		List<Intention> previsions = new ArrayList<Intention>();
//		previsions.add(new Intention(null, null));
		for (Observation observation : observations) {
			OOMDPState currentState = observation.getState();
			Condition condition = Condition.evaluate(oomdp.getPfIndex(), currentState);
			this.mind.learn(currentState, observation.getAction());

			Intention intention = this.mind.reasoning(currentState);
			assert (intention != null);
			
			System.out.println(
					"Reasoning >> " + intention + " for " + observation.getAction() + " cond: " + condition.toString());
			previsions.add(intention);
		}
		this.mind.endReasoning();
		
		
		observations.remove(0);
		Integer hits = 0;
		Integer total = observations.size();
		for (int i = 0; i < observations.size(); i++) {
			if(previsions.get(i).getAction().equals(observations.get(i).getAction())){
				hits += 1;
			}else {
				System.out.println("not hit: "+previsions.get(i).getAction()+":"+observations.get(i).getAction());
			}
		}
		if(hits != total)
			this.mind.inferFDI();
		return previsions;
	}

	public void saveMindToFile(String fileName) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(this.mind);
			objectOutputStream.flush();
			objectOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void loadMindFromFile(String fileName) {
		try {
			FileInputStream fileInputStream = new FileInputStream(fileName);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			this.mind = (MindTOM) objectInputStream.readObject();
			this.mind.initializeMindWhenLoadedFromFile();
			objectInputStream.close();
		} catch (Exception e) {
			System.out.println("Could not open mind file");
			e.printStackTrace();
//			System.exit(-1);
		}
	}

	public MindTOM getMind() {
		return this.mind;
	}
}
