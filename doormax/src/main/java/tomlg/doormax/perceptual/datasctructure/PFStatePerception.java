package tomlg.doormax.perceptual.datasctructure;

import java.util.ArrayList;
import java.util.List;

import tomlg.doormax.Condition;
import tomlg.doormax.PropositionalFunction;
import tomlg.doormax.oomdpformalism.OOMDPState;

public class PFStatePerception extends StatePerception {

	PropositionalFunction[] propFuns;
	String dataString;
	Condition condition;
	
	public PFStatePerception(OOMDPState state, PropositionalFunction[] propFuns, Boolean tag) {
		this.propFuns = propFuns;
		this.condition = state.toCondition();
		StringBuilder sb = new StringBuilder();

		
		String prefix = "";
		for (PropositionalFunction pf : propFuns) {
				sb.append(prefix + pf.evaluate(state));//TODO checar isso pf.somePFGroundingIsTrue(state));
			prefix = ",";
		} 
		sb.append(",");
		if (tag != null) {
			sb.append(tag);
		}
		else {
			sb.append("?");
		}

		sb.append("\n");
		
		this.dataString = sb.toString();

	}
	
	@Override
	public String getArffValueString(boolean labeled) {
		return this.dataString;
	}

	@Override
	public List<String> getattributeNames() {
		List<String> toReturn = new ArrayList<String>();
		for (PropositionalFunction pf : this.propFuns) {
			toReturn.add(pf.name + " {true,false}\n");
		}
		return toReturn;
	}

	@Override
	public Condition getCondition() {
		return this.condition;
	}

}
