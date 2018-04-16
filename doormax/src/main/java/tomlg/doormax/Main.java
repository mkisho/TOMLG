package tomlg.doormax;

import java.io.FileNotFoundException;
import java.io.IOException;

import tomlg.doormax.utils.OOMDPReaderFromFile;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		OOMDPReaderFromFile a = new OOMDPReaderFromFile();
		a.leitura();
	}
	
}
