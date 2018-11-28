package doormax.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import doormax.OOMDP;
import doormax.OOMDPState;
import doormax.ObjectClass;
import doormax.ObjectInstance;
import doormax.structures.attribute.Attribute;
import doormax.structures.attribute.AttributeBoolean;
import doormax.structures.attribute.AttributeInteger;

public class Util {

	public static OOMDPState readOOMDPStateFromFile(String fileName, OOMDP oomdp) {
//        String content = new String(Files.readAllBytes(Paths.get("duke.java")));
		String fileData = null;
		try {
			fileData = String.join("\n", Files.readAllLines(Paths.get(fileName)));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
		ArrayList<ObjectInstance> objects = new ArrayList<ObjectInstance>();

		for (String instance : fileData.split("\\)")) {

			String className = instance.split("\\(")[0].trim();

			// checa se a classe é definida no OOMDP
			Optional<ObjectClass> obj = oomdp.getObjectClasses().stream().filter(p -> p.getName().equals(className))
					.findFirst();
			assert (obj.isPresent()==true);
				ObjectClass objClass = obj.get();
			List<Attribute> attributes = new ArrayList<Attribute>();

			for (String attribute : instance.split("\\(")[1].trim().split(",")) {
				String attName = attribute.split(":")[0].trim();
				String value = attribute.split(":")[1].trim();
				assert (attName != null && value != null);

				Optional<Attribute> att = objClass.getAttributes().stream().filter(p -> p.getName().equals(attName))
						.findFirst();
				assert (att.isPresent()==true);

				Attribute objAttribute = att.get().copy();
				if (objAttribute instanceof AttributeBoolean) {
					assert (value.equals("false") || value.equals("true"));
					((AttributeBoolean) objAttribute).setValue(Boolean.parseBoolean(value));
				} else if (objAttribute instanceof AttributeInteger) {
					((AttributeInteger) objAttribute).setValue(Integer.parseInt(value));
				}
				attributes.add(objAttribute);
			}
			// checa se todos os atributos foram instanciados
			
			assert(attributes.size() == objClass.getAttributes().size());

			ObjectInstance newInstance = new ObjectInstance(objClass, attributes);
			objects.add(newInstance);
		}

		return new OOMDPState(oomdp, objects);
	}

}
