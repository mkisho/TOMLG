package doormax.structures.attribute;

public class AttributeBoolean extends Attribute {
	private static final long serialVersionUID = 6625684648059190734L;

	public AttributeBoolean(String name) {
		super(name, "Boolean");
		this.value = null;
	}

	public AttributeBoolean(String name, boolean value) {
		this(name);
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
		if(this.value != null)
			return new AttributeBoolean(this.getName(), new Boolean(this.value));
		else return new AttributeBoolean(this.getName());

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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Boolean [");
		builder.append(getName());
		
		if(value != null)builder.append(", value="+value);
		builder.append("]");
		return builder.toString();
	}
}
