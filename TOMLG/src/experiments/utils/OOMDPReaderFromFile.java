package experiments.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import doormax.OOMDP;
import doormax.OOMDPState;
import doormax.ObjectClass;
import doormax.ObjectInstance;
import doormax.PropositionalFunction;
import doormax.structures.Action;
import doormax.structures.attribute.Attribute;
import doormax.structures.attribute.AttributeBoolean;
import doormax.structures.attribute.AttributeInteger;
import doormax.structures.attribute.AttributeString;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class OOMDPReaderFromFile {
	private BufferedReader in;
	private String arquivo;
	private String[] auxStringArray;
	// private String[] class_attributes;
	private String[] propositions;
	private String gamma_string;
	private float gamma;
	List<Action> actions_list = new ArrayList<Action>();
	List<ObjectClass> createdClasses = new ArrayList<ObjectClass>();

	Pattern actions_pattern = Pattern.compile("((?!\\s*Actions\\s*\\{)\\s*(\\w*[\\,,\\s])+(?=\\}\\s+))"); //
	Pattern classes_pattern = Pattern
			.compile("(Classes\\s*\\{(\\s*(\\w+\\s*\\(\\s*(\\s*\\w+\\s*:\\s*\\w+[\\,,\\s])+\\s*\\))\\s)*\\s*\\})"); //
	Pattern class_pattern = Pattern.compile("(((\\w)+\\s*\\(\\s*)(\\s*\\w+\\s*:\\s*\\w+[\\,,\\s])+\\s*(?!\\)))");
	Pattern propositions_pattern = Pattern.compile("(Propositions\\s*\\{\\s*(\\w*[\\,,\\s])*\\})"); //
	Pattern gamma_pattern = Pattern.compile("(gamma\\s*:\\s*\\d.\\d*)"); //

	public OOMDPReaderFromFile() {
		arquivo = "";
	}

	public OOMDP leitura(String name, PropositionalFunction[] pfs) throws FileNotFoundException, IOException {

		String line;

		// Pattern class_name_pattern= Pattern.compile("((\\w)+(?!\\)))");
		in = new BufferedReader(new FileReader(name));
		line = in.readLine();

		while (line != null) {
			arquivo = arquivo.concat(line);
			line = in.readLine();
		}
		in.close();

		Matcher actions_matcher = actions_pattern.matcher(arquivo);
		if (actions_matcher.find()) {
			auxStringArray = actions_matcher.group().split(",");

			for (String strTemp : auxStringArray) {
				actions_list.add(new Action(strTemp.trim()));
			}
			System.out.println(actions_matcher.group());
		}

		Matcher classes_matcher = classes_pattern.matcher(arquivo);
		if (classes_matcher.find()) {
			Matcher class_matcher = class_pattern.matcher(classes_matcher.group());
			while (class_matcher.find()) {

				String className = getclassName(class_matcher.group());
				auxStringArray = class_matcher.group().split(",");
				auxStringArray[0] = auxStringArray[0].split("\\(")[1];
				List<Attribute> attributes = new ArrayList<Attribute>();
				for (String strTemp : auxStringArray) {
					attributes.add(makeAttribute(strTemp));
				}
				createdClasses.add(new ObjectClass(className, attributes));
			}

			System.out.println(classes_matcher.group());
		}

		Matcher propositions_matcher = propositions_pattern.matcher(arquivo);
		if (propositions_matcher.find()) {
			propositions = propositions_matcher.group().split(",");
			// List<Action> proposition_list = new ArrayList<Proposition>(4);
			for (String strTemp : propositions) {
				// propositions_list.add(new Action("strTemp"));

				// propositions_matcher.group().split(",");
				System.out.println(strTemp);
			}
		}

		Matcher gamma_matcher = gamma_pattern.matcher(arquivo);
		if (gamma_matcher.find()) {
			gamma_string = gamma_matcher.group();
			gamma = Float.parseFloat(gamma_string.split(":")[1]);
			System.out.println(gamma);
		}

		OOMDP oomdp = new OOMDP(actions_list, createdClasses, pfs);
		System.out.println(oomdp);
		return oomdp;
	}

	public String getclassName(String str) {
		Pattern class_name_pattern = Pattern.compile("((\\w)+(?!\\)))");

		Matcher class_name_matcher = class_name_pattern.matcher(str);
		if (class_name_matcher.find()) {
			return (class_name_matcher.group());
		}
		return "erro";

	}

	public Attribute makeAttribute(String str) {

		Attribute attribute = null;
		String[] temp = str.split(":");
		String name = temp[0].trim();
		String type = temp[1].trim();
		if (type.matches("IntegerDomain")) {
			attribute = new AttributeInteger(name);
		} else if (type.matches("BooleanDomain")) {
			attribute = new AttributeBoolean(name);
		} else if (type.matches("StringDomain")) {
			attribute = new AttributeString(name);
		} else {
			assert (false);
		}

		return attribute;
	}

	public OOMDPState stateReader(String fileName, OOMDP oomdp) throws FileNotFoundException, IOException {
		String line;
		OOMDPState oomdpState = new OOMDPState(oomdp);
		in = new BufferedReader(new FileReader(fileName));

		line = in.readLine();
		arquivo = "";

		while (line != null) {
			arquivo = arquivo.concat(line);
			line = in.readLine();
		}
		in.close();

		List<ObjectClass> objectClasses = oomdp.getObjectClasses();

		Matcher class_matcher = class_pattern.matcher(arquivo);
		while (class_matcher.find()) {
			String className = getclassName(class_matcher.group());
			auxStringArray = class_matcher.group().split(",");
			auxStringArray[0] = auxStringArray[0].split("\\(")[1];

			for (ObjectClass objClass : objectClasses) {
				if (objClass.getName().equals(className)) {
					ObjectInstance newObjInst = new ObjectInstance(objClass, objClass.getAttributesCopy());
					List<Attribute> attributes = newObjInst.getAttributes();
					Iterator<Attribute> e = attributes.iterator();

					for (String strTemp : auxStringArray) {
						Attribute attribute ;
						String[] temp = strTemp.split(":");
						String val = temp[1].trim();
						do  {
							attribute = e.next();
						} while(!attribute.getName().equals(temp[0].trim()));
						
						

						if (attribute instanceof AttributeInteger) {
							((AttributeInteger) attribute).setValue(Integer.parseInt(val));
						} else if (attribute instanceof AttributeBoolean) {
							((AttributeBoolean) attribute).setValue(Boolean.parseBoolean(val));
						} else if (attribute instanceof AttributeString) {
							((AttributeString) attribute).setValue(val);
						} else
							assert (false);
						
						e = attributes.iterator();
					}
					System.out.println(newObjInst.toString());
					oomdpState.addObjectInstance(newObjInst);
				}
			}

		}
		return oomdpState;

	}
}
