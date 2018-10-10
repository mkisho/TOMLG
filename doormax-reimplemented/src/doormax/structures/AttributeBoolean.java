package doormax.structures;

public class AttributeBoolean extends Attribute {

	public AttributeBoolean(String name, String domain) {
		super(name, "Boolean");
		this.value = null;
	}

	public AttributeBoolean(String name, String domain, boolean value) {
		super(name, domain);
		this.value = value;
	}

	private Boolean value;

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public AttributeBoolean copy() {
		return new AttributeBoolean(this.getName(), this.domain, new Boolean(this.value));
	}

	@Override
	public boolean sameValue(Attribute o) {
		if (o instanceof AttributeBoolean) {
			return this.value == ((AttributeBoolean) o).value;
		} else
			return false;
	}

	@Override
	public Double getDoubleValue() {
		return (this.value ? 1.0 : 0.0);
	}
	
}
