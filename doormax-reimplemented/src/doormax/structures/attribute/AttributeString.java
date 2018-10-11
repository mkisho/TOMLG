package doormax.structures.attribute;

public class AttributeString  extends Attribute {
	String value;

	public AttributeString(String name) {
		super(name, "String");
		this.value = null;
	}

	public AttributeString(String name, String value) {
		this(name);
		this.value = value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public AttributeString copy() {
		if(this.value != null)
			return new AttributeString(this.getName(), this.value);
		else return new AttributeString(this.getName());

	}

	@Override
	public boolean sameValue(Attribute o) {
		if (o instanceof AttributeString) {
			return this.value.equals(((AttributeString) o).value);
		} else
			return false;
	}

	@Override
	public Double getDoubleValue() {
		return null;
	}
	
}
