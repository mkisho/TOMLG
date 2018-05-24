package taxi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import pf.PassengerInTaxi;
import pf.TaxiAtPassenger;
import pf.WallToEastOfTaxi;
import pf.WallToNorthOfTaxi;
import pf.WallToSouthOfTaxi;
import pf.WallToWestOfTaxi;
import tomlg.doormax.Doormax;
import tomlg.doormax.PropositionalFunction;
import tomlg.doormax.TaxiEnvironment;
import tomlg.doormax.oomdpformalism.OOMDP;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.utils.OOMDPReaderFromFile;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		OOMDPReaderFromFile a = new OOMDPReaderFromFile();
		List<PropositionalFunction> pfs = new ArrayList<PropositionalFunction>();
		pfs.add(new PassengerInTaxi());
		pfs.add(new TaxiAtPassenger());
		pfs.add(new WallToEastOfTaxi());
		pfs.add(new WallToNorthOfTaxi());
		pfs.add(new WallToSouthOfTaxi());
		pfs.add(new WallToWestOfTaxi());
		
		PropositionalFunction [] pfss = new PropositionalFunction[pfs.size()];
		for (int i=0; i< pfs.size(); i++)
			pfss[i] = pfs.get(i);
		
		OOMDP oomdp = a.leitura(pfss);
		OOMDPState oomdpState = a.stateReader(oomdp);
		TaxiEnvironment evs = new TaxiEnvironment("Taxi");
		Doormax doormax = new Doormax(oomdp, oomdpState, 100, 0.1);
	
		doormax.step(evs);
	}
}
