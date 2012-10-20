import info.gridworld.grid.Location;

public class SimpleJeroo extends Jeroo {
	public static void main(String []args) {
		JerooWorld world = new JerooWorld("Angel.jev");
		Jeroo dude = new Jeroo();		
		world.add(new Location(0,0),dude);
		world.show();
		
		//get flower
		dude.hop(3);
		dude.turn(RIGHT);
		dude.hop(3);
		dude.turn(LEFT);
		dude.hop();
		dude.pick();

		//go to net
		dude.turn(RIGHT);
		dude.hop();
		dude.turn(LEFT);
		dude.hop();
		dude.toss();
		
		//go home
		dude.hop(4);
		dude.turn(LEFT);
		dude.hop(3);
	}
}
