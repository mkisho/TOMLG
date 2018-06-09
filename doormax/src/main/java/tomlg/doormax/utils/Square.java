package tomlg.doormax.utils;

public class Square {
	public Boolean wallRight;
	public Boolean wallDown;
	public Boolean wallUp;
	public Boolean wallLeft;
	public Boolean visited;
	
	public Square() {
		wallRight = false;
		wallDown = false;
		wallUp = false;
		wallLeft = false;
		visited = false;
	}
}
