package tomlg;

import doormax.OOMDPState;
import doormax.structures.Action;

/**
 * Classe genérica para definir a simulação do ambiente
 * O algoritmo do Doormax irá utilizar essa classe para realizar o aprendizado 
 * 
 * As classes que implementam um EnvironmentSimulator devem ser definidas separadamente
 * @author chronius
 *
 */
public abstract class EnvironmentSimulator {
	public final String name;

	public EnvironmentSimulator(String name) {
		super();
		this.name = name;
	}
	
	public abstract OOMDPState simulateAction(OOMDPState state0, Action action);

}
