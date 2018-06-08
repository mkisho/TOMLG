package tomlg.doormax.oomdpformalism;

import java.util.Arrays;

/**
 * Representa uma ação que pode ser executada no mundo
 * @author chronius
 *
 */
public class Action {
	public final String name;
	public final Object []params;
	
	public Action(String name, Object [] params) {
		super();
		this.name = name;
		this.params = params;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final int maxLen = 10;
		return "Action [name=" + name + ", params="
				+ (params != null ? Arrays.asList(params).subList(0, Math.min(params.length, maxLen)) : null) + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(params);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Action)) {
			return false;
		}
		Action other = (Action) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (!Arrays.equals(params, other.params)) {
			return false;
		}
		return true;
	}

 
	
}
   