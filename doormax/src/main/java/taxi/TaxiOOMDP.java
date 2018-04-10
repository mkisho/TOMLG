package taxi;

/*
//TODO Ler as definições de classes de arquivos
public class TaxiOOMDP {

	public TaxiOOMDP() {
		List<ObjectClass> classs = new ArrayList<ObjectClass>(4);
		classs.add(makeTaxi());
		classs.add(makePassenger());
		classs.add(makeDestination());
		classs.add(makeWall());

		OOMDP oomdpTaxi = new OOMDP(makeActions(), classs);
	}

	public ObjectClass makeTaxi() {
		ObjectAttribute x = new ObjectAttribute("x", new IntegerDomain());
		ObjectAttribute y = new ObjectAttribute("y", new IntegerDomain());

		List<ObjectAttribute> attributes = new ArrayList<ObjectAttribute>(2);
		attributes.add(x);
		attributes.add(y);

		return new ObjectClass(attributes, "Taxi");
	}

	public ObjectClass makeDestination() {
		ObjectAttribute x = new ObjectAttribute("x", new IntegerDomain(0, 100));
		ObjectAttribute y = new ObjectAttribute("y", new IntegerDomain(0, 100));

		List<ObjectAttribute> attributes = new ArrayList<ObjectAttribute>(2);
		attributes.add(x);
		attributes.add(y);

		return new ObjectClass(attributes, "Destination");
	}

	public ObjectClass makePassenger() {
		ObjectAttribute x = new ObjectAttribute("x", new IntegerDomain(0, 100));
		ObjectAttribute y = new ObjectAttribute("y", new IntegerDomain(0, 100));
		ObjectAttribute inTaxi = new ObjectAttribute("in-taxi", new BooleanDomain());

		List<ObjectAttribute> attributes = new ArrayList<ObjectAttribute>(2);
		attributes.add(x);
		attributes.add(y);
		attributes.add(inTaxi);

		return new ObjectClass(attributes, "Passenger");
	}

	public List<Action> makeActions() {
		List<Action> actions = new ArrayList<Action>(4);

		actions.add(new Action("UP"));
		actions.add(new Action("DOWN"));
		actions.add(new Action("LEFT"));
		actions.add(new Action("RIGHT"));

		return actions;
	}

	public ObjectClass makeWall() {
		ObjectAttribute x = new ObjectAttribute("x", new IntegerDomain(0, 100));
		ObjectAttribute y = new ObjectAttribute("y", new IntegerDomain(0, 100));

		List<ObjectAttribute> attributes = new ArrayList<ObjectAttribute>(2);
		attributes.add(x);
		attributes.add(y);

		return new ObjectClass(attributes, "Wall");
	}

}
*/