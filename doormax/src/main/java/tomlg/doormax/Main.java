package tomlg.doormax;

import java.io.FileNotFoundException;
import java.io.IOException;

import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.OOMDP;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.utils.OOMDPReaderFromFile;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		OOMDPReaderFromFile a = new OOMDPReaderFromFile();
		
		OOMDP oomdp = a.leitura();
		OOMDPState oomdpState = a.stateReader(oomdp);
		TaxiEnvironment tmp = new TaxiEnvironment("Taxi");
		Action a1 = new Action("taxiMoveNorth");
		tmp.simulateAction(oomdpState, a1);
		a1 = new Action("taxiMoveWest");
		tmp.simulateAction(oomdpState, a1);
		a1 = new Action("taxiMoveEast");
		tmp.simulateAction(oomdpState, a1);
		a1 = new Action("taxiMoveSouth");
		tmp.simulateAction(oomdpState, a1);
		a1 = new Action("pickupPassenger");
		tmp.simulateAction(oomdpState, a1);
		a1 = new Action("dropOffPassenger");
		tmp.simulateAction(oomdpState, a1);
	}
	
}
