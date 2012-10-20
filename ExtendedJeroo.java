import info.gridworld.grid.Location;

public class ExtendedJeroo extends Jeroo {
	//this is a constructor - it makes a Jeroo
	public ExtendedJeroo() {
		//This works somewhat like new Jeroo(100);	
		super(100);
	}
	
	//this is a Jeroo method
	public void turnAndGo() {
		hop();
		plant();
		turn(RIGHT);
	}
	
	//this is the main method
	public static void main(String[] args) {
		JerooWorld world = new JerooWorld();
		ExtendedJeroo kim = new ExtendedJeroo();
		world.add(new Location(0,0),kim);
		world.show();
		
		while(kim.isClear(AHEAD)) {
			kim.turnAndGo();
		}
		
		System.out.println("Goodbye");
	}
}
