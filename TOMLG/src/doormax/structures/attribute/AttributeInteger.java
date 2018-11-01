package doormax.structures.attribute;

public class AttributeInteger extends Attribute {
	private static final long serialVersionUID = -4040666887892702892L;
	private Integer value;

	public AttributeInteger(String name) {
		super(name, "Integer");
		this.value = null;
	}

	public AttributeInteger(String name, int value) {
		this(name);
//		super(name, domain);
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public AttributeInteger copy() {
		if (this.value != null)
			return new AttributeInteger(this.getName(), new Integer(this.value));
		else
			return new AttributeInteger(this.getName());
	}

	@Override
	public boolean sameValue(Attribute o) {
		if (o instanceof AttributeInteger) {
			return this.value == ((AttributeInteger) o).value;
		} else
			return false;
	}

	@Override
	public Double getDoubleValue() {
		return this.value + 0.0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Integer [");
		builder.append(getName());
		
		if(value != null)builder.append(", value="+value);
		builder.append("]");
		return builder.toString();
	}

	
}
