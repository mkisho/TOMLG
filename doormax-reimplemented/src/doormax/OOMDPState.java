package doormax;
import java.util.Collections;
import java.util.List;

public class OOMDPState {
	private OOMDP oomdp;
	private List<ObjectInstance> objects;

	public OOMDPState(OOMDP oomdp, List<ObjectInstance> objects) {
		this.oomdp = oomdp;
		this.objects = objects;
	}
	
	/**
	 * Returns sorted objects list by id
	 * @return
	 */
	public List<ObjectInstance> getObjects(){
		Collections.sort(this.objects);
		return this.objects;
	}

}