package tomlg;

public class Agent {
	private Mind mind;
	
	public Agent( Sentidos, Atuador)
	
	public void step() {
		//perceive()
		OOMDPState currentState = perceive();
		
		this.mind.reasoning();
		//learn
		//foloow
	}
}
