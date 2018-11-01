package doormax.structures.attribute;

public class AttributeString  extends Attribute {
	private static final long serialVersionUID = -3283904474509851471L;
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("String [");
		builder.append(getName());
		
		if(value != null)builder.append(", value="+value);
		builder.append("]");
		return builder.toString();
	}
}
