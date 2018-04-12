package tomlg.doormax;

import java.io.FileNotFoundException;
import java.io.IOException;

import tomlg.doormax.utils.OOMDPReaderFromFile;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.out.printf("Mi estas cxi tie");
		OOMDPReaderFromFile a = new OOMDPReaderFromFile();
		a.leitura();
	}
	
}
