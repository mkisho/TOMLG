package doormax.structures.attribute;

import java.io.Serializable;

public abstract class Attribute implements Comparable<Attribute>, Serializable {
	private static final long serialVersionUID = 3838991671603327823L;
	private String name;
	public final String domain;

	public Attribute(String name, String domain) {
		super();
		this.name = name;
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDomain() {
		return domain;
	}

	public abstract Attribute copy();

	@Override
	public int compareTo(Attribute o) {
		return this.name.compareTo(o.name);
	}

	public abstract boolean sameValue(Attribute o);

	public abstract Double getDoubleValue();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attribute other = (Attribute) obj;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[" + domain + ":" + name + "]";
	}

}