package tomlg;

public class Goal {
	private String name;
	private String motivation; //TODO FIX FIX FIX
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMotivation() {
		return motivation;
	}
	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Goal [name=");
		builder.append(name);
		builder.append(", motivation=");
		builder.append(motivation);
		builder.append("]");
		return builder.toString();
	}
	public Goal(String name, String motivation) {
		super();
		this.name = name;
		this.motivation = motivation;
	}
	
}
