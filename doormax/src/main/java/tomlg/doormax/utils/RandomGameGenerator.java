package tomlg.doormax.utils;

//import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.ObjectClass;

public class RandomGameGenerator {
	// private static final int WIDHT = 0;
	// private BufferedReader in;
	// private String nomeArquivo;
	// private String[] auxStringArray;
	// private String[] class_attributes;
	// private String[] propositions;
	List<Action> actions_list = new ArrayList<Action>();
	List<ObjectClass> createdClasses = new ArrayList<ObjectClass>();
	Square[][] houses;
	int HEIGHT;
	int WIDTH;
	PrintWriter writer;

	public RandomGameGenerator() {
		super();

	}

	public void printObjectToFile(String name, List<String> attributes) {
		// System.out.println(name +"(");
		// for(String str : attributes) {
		// System.out.println(str+",");
		//
		// }
		// System.out.println(")");
		writer.println(name + "(");

/*		String data = "";
		for (String str : attributes) {
			data += str + ",\n";
			// writer.println(str + ",");

		}*/
		String data = String.join(",\n",attributes);
		//data = data.substring(0, data.length() - 2);
		writer.println(data + "\n )");
	}

	public boolean gerar(String arquivo, final int size) {
		try {
			// String line;
			String className;
			List<String> attributesList = new ArrayList<String>();
			String attribute;
			String tmp = null;
			writer = new PrintWriter(arquivo, "UTF-8");
			HEIGHT = size//ThreadLocalRandom.current().nextInt(4, 11);
			;
			WIDTH = size//ThreadLocalRandom.current().nextInt(4, 11);
			;

			// in = new BufferedReader(new FileReader(""+nomeArquivo));
			// line = in.readLine();

			int randInt;
			houses = new Square[HEIGHT][WIDTH];
			for (int i = 0; i < HEIGHT; i++) {
				for (int j = 0; j < WIDTH; j++) {
					houses[i][j] = new Square();
				}
			}
			// int rand = ThreadLocalRandom.current().nextInt(0, validIntentions.size());

			// Generate goal
			className = "destination";

			randInt = ThreadLocalRandom.current().nextInt(0, WIDTH);
			tmp = String.valueOf(randInt);
			attribute = "xLocation :" + tmp;
			attributesList.add(attribute);

			randInt = ThreadLocalRandom.current().nextInt(0, HEIGHT);
			tmp = String.valueOf(randInt);
			attribute = "yLocation :" + tmp;
			attributesList.add(attribute);

//			tmp = "destination";
//			attribute = "goalType :" + tmp;
//			attributesList.add(attribute);

			printObjectToFile(className, attributesList);
			attributesList.clear();

			// Generate taxi
			className = "taxi";
			randInt = ThreadLocalRandom.current().nextInt(0, WIDTH);
			tmp = String.valueOf(randInt);
			attribute = "xLocation :" + tmp;
			attributesList.add(attribute);

			randInt = ThreadLocalRandom.current().nextInt(0, HEIGHT);
			tmp = String.valueOf(randInt);
			attribute = "yLocation :" + tmp;
			attributesList.add(attribute);

			tmp = "false";
			attribute = "passengerInTaxi :" + tmp;
			attributesList.add(attribute);

			printObjectToFile(className, attributesList);
			attributesList.clear();

			// Generate passenger
			className = "passenger";
			randInt = ThreadLocalRandom.current().nextInt(0, WIDTH);
			tmp = String.valueOf(randInt);
			attribute = "xLocation :" + tmp;
			attributesList.add(attribute);

			randInt = ThreadLocalRandom.current().nextInt(0, HEIGHT);
			tmp = String.valueOf(randInt);
			attribute = "yLocation :" + tmp;
			attributesList.add(attribute);

//			tmp = "Passenger";
//			attribute = "goalType :" + tmp;
//			attributesList.add(attribute);

			tmp = "false";
			attribute = "inTaxi :" + tmp;
			attributesList.add(attribute);

			printObjectToFile(className, attributesList);
			attributesList.clear();

			// Generate vertical Walls
			className = "verticalWall";

			tmp = String.valueOf(0);
			attribute = "wallOffSet :" + tmp;
			attributesList.add(attribute);
			tmp = String.valueOf(0);
			attribute = "bottomOfWall :" + tmp;
			attributesList.add(attribute);
			tmp = String.valueOf(HEIGHT);
			attribute = "topOfWall :" + tmp;
			attributesList.add(attribute);
			printObjectToFile(className, attributesList);
			attributesList.clear();

			tmp = String.valueOf(WIDTH);
			attribute = "wallOffSet :" + tmp;
			attributesList.add(attribute);
			tmp = String.valueOf(0);
			attribute = "bottomOfWall :" + tmp;
			attributesList.add(attribute);
			tmp = String.valueOf(HEIGHT);
			attribute = "topOfWall :" + tmp;
			attributesList.add(attribute);
			printObjectToFile(className, attributesList);
			attributesList.clear();

			// Random vertical walls
			int numberOfWalls = ThreadLocalRandom.current().nextInt(0, WIDTH);
			for (int i = 0; i < numberOfWalls/4; i++) {
				tmp = String.valueOf(ThreadLocalRandom.current().nextInt(1, WIDTH));
				attribute = "wallOffSet :" + tmp;
				attributesList.add(attribute);

				randInt = (ThreadLocalRandom.current().nextInt(0, HEIGHT));
				tmp = String.valueOf(randInt);
				attribute = "bottomOfWall :" + tmp;
				attributesList.add(attribute);
				tmp = String.valueOf(ThreadLocalRandom.current().nextInt(0, HEIGHT - randInt) + randInt);
				attribute = "topOfWall :" + tmp;
				attributesList.add(attribute);
				printObjectToFile(className, attributesList);
				addWallForTest(attributesList, false);
				attributesList.clear();
			}

			// Generate horizontal Wall
			className = "horizontalWall";

			tmp = String.valueOf(0);
			attribute = "wallOffSet :" + tmp;
			attributesList.add(attribute);
			tmp = String.valueOf(0);
			attribute = "leftStartOfWall :" + tmp;
			attributesList.add(attribute);
			tmp = String.valueOf(WIDTH);
			attribute = "rightStartOfWall :" + tmp;
			attributesList.add(attribute);
			printObjectToFile(className, attributesList);
			attributesList.clear();

			tmp = String.valueOf(HEIGHT);
			attribute = "wallOffSet :" + tmp;
			attributesList.add(attribute);
			tmp = String.valueOf(0);
			attribute = "leftStartOfWall :" + tmp;
			attributesList.add(attribute);
			tmp = String.valueOf(WIDTH);
			attribute = "rightStartOfWall :" + tmp;
			attributesList.add(attribute);
			printObjectToFile(className, attributesList);
			attributesList.clear();

			numberOfWalls = ThreadLocalRandom.current().nextInt(0, HEIGHT);
			for (int i = 0; i < numberOfWalls/4; i++) {
				tmp = String.valueOf(ThreadLocalRandom.current().nextInt(1, HEIGHT));
				attribute = "wallOffSet :" + tmp;
				attributesList.add(attribute);
				randInt = (ThreadLocalRandom.current().nextInt(0, WIDTH));
				tmp = String.valueOf(randInt);
				attribute = "leftStartOfWall :" + tmp;
				attributesList.add(attribute);
				tmp = String.valueOf(ThreadLocalRandom.current().nextInt(0, WIDTH - randInt) + randInt);
				attribute = "rightStartOfWall :" + tmp;
				attributesList.add(attribute);
				printObjectToFile(className, attributesList);
				addWallForTest(attributesList, true);
				attributesList.clear();
			}

			writer.close();

			return testar();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			// TODO: handle exception
		}
	}

	private void addWallForTest(List<String> attributes, boolean horizontal) {

		int offset;
		int begin;
		int end;
		offset = Integer.parseInt(attributes.get(0).split(":")[1]);
		begin = Integer.parseInt(attributes.get(1).split(":")[1]);
		end = Integer.parseInt(attributes.get(2).split(":")[1]);
		for (int i = begin; i <= end; i++) {
			if (horizontal) {
				houses[offset][i].wallLeft = true;
				houses[offset - 1][i].wallRight = true;
			} else {
				houses[i][offset].wallDown = true;
				houses[i][offset - 1].wallUp = true;
			}
		}
	}

	private boolean testar() {
		// Square[][] houses = new Square [HEIGHT][WIDTH] ;
		ArrayList<Integer> listX = new ArrayList<Integer>();
		ArrayList<Integer> listY = new ArrayList<Integer>();

		listX.add(0);
		listY.add(0);
		int x;
		int y;

		while (!listX.isEmpty()) {
			x = listX.remove(0);
			y = listY.remove(0);
			
			if (x > 0) {
				if (!houses[x - 1][y].visited && !houses[x][y].wallLeft) {
					listX.add(x - 1);
					listY.add(y);
					houses[x-1][y].visited = true;
				}
			}
			if (y > 0) {
				if (!houses[x][y - 1].visited && !houses[x][y].wallDown) {
					listX.add(x);
					listY.add(y - 1);
					houses[x][y-1].visited = true;
				}
			}
			if (x < HEIGHT - 1) {
				if (!houses[x + 1][y].visited && !houses[x][y].wallUp) {
					listX.add(x + 1);
					listY.add(y);
					houses[x+1][y].visited = true;
				}
			}
			if (y < WIDTH - 1) {
				if (!houses[x][y + 1].visited && !houses[x][y].wallRight) {
					listX.add(x);
					listY.add(y + 1);
					houses[x][y+1].visited = true;
				}
			}

		}

		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (houses[i][j].visited == false) {
					return false;
				}
			}
		}

		return true;
	}
}
