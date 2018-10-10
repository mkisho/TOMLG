package doormax.structures;

public class AttributeInteger extends Attribute {
	private Integer value;

	public AttributeInteger(String name, String domain) {
		super(name, "Integer");
		this.value = null;
	}

	public AttributeInteger(String name, String domain, int value) {
		super(name, domain);
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public AttributeInteger copy() {
		return new AttributeInteger(this.getName(), this.domain, new Integer(this.value));
	}

	@Override
	public boolean sameValue(Attribute o) {
		if (o instanceof AttributeInteger) {
			return this.value == ((AttributeInteger)o).value;
		}else return false;
	}

	@Override
	public Double getDoubleValue() {
		return this.value + 0.0;
	}
	
	
}
