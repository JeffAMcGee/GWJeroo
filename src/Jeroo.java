
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Jeroo extends StackableActor {
	/** This is an absolute direction.
	 * It is a little bit verbose so that getCompassDirection() can return a
	 * GridWorld style direction.
	 */
	public enum Direction {
		NORTH(Location.NORTH),  
		SOUTH(Location.SOUTH),
		EAST(Location.EAST),
		WEST(Location.WEST);
		
		Direction(int direction) {
			this.direction = direction;
		}
		public int getCompassDirection() {
			return direction;
		}
		private final int direction;
	}
	
	/** This is a relative direction.  It works just like it does in Jeroo.
	 */
	public enum RelDirection {
		LEFT,RIGHT,AHEAD,HERE;
	}

	//Syntactic sugar to make things a little more like Jeroo
	public final static Direction NORTH = Direction.NORTH;
	public final static Direction SOUTH = Direction.SOUTH;
	public final static Direction WEST = Direction.WEST;
	public final static Direction EAST = Direction.EAST;
	public final static RelDirection LEFT = RelDirection.LEFT;
	public final static RelDirection RIGHT = RelDirection.RIGHT;
	public final static RelDirection AHEAD = RelDirection.AHEAD;
	public final static RelDirection HERE = RelDirection.HERE;
	
	/** The number of flowers in the Jeroo's pouch.
	 */
	private int flowers;
	private JerooWorld world;
	
	/** Creates a Jeroo facing EAST with 0 flowers.
	 */
	public Jeroo() {
		this(EAST,0);
	}

	/** Creates a Jeroo with 0 flowers.
	 * @param dir the direction the Jeroo is facing.
	 */
	public Jeroo(Direction dir) {
		this(dir,0);
	}

	/** Creates a Jeroo facing EAST.
	 * @param flowers the number of flowers the Jeroo starts with.
	 */
	public Jeroo(int flowers) {
		this(EAST,flowers);
	}

	/** Creates a Jeroo.
	 * @param dir the direction the Jeroo is facing.
	 * @param flowers the number of flowers the Jeroo starts with.
	 */
	public Jeroo(Direction dir, int flowers) {
		setDirection(dir.getCompassDirection());
		this.flowers=flowers;
		world = null;

	}

	/** Gets the number of flowers.
	 * @return the number of flowers in the Jeroo's pouch.
	 */
    public int getFlowers() {
		return flowers;
	}

	/** Moves forward one space.
	 * @throws JerooException if the jeroo runs into something.
	 */
    public void hop() {
		hop(1);
	}

	/** Moves forward several spaces.
	 * @param spaces the number of spaces to hop
	 * @throws JerooException if the jeroo runs into something.
	 */
    public void hop(int spaces) {
    	if(spaces<=0)
    		return;
    	
		int direction = getDirection();
		Location loc = getLocation();
		Grid<Actor> grid = getGrid();
		for(int i=0;i<spaces;i++) {
			loc = loc.getAdjacentLocation(direction);
			if(!grid.isValid(loc))
				throw new JerooException(this,"hopped off of map");
			Actor dude = grid.get(loc);
			
			if(dude != null && !(dude instanceof Flower) ) {
				String obj = dude.getClass().getName();
				String msg = "hopped into " + obj + " at " + dude.getLocation();
				throw new JerooException(this,msg);
			}
		}
		moveTo(loc);
		waitForStep();
	}

	/** Turns in the indicated direction, but stays in the same space.
	 * If dir is not LEFT or RIGHT, this method does nothing.
	 * @param dir the direction to turn.
	 */
    public void turn(RelDirection dir) {
		if(dir==RIGHT)
			setDirection(getDirection()+Location.RIGHT);
		if(dir==LEFT)
			setDirection(getDirection()+Location.LEFT);
		waitForStep();
	}

	/** Plants a flower at the current location.
	 * This method does not do anything if this jeroo is out of flowers.
	 */
    public void plant() {
		if(flowers<1)
			return;
		setActorBelow(new Flower());
		flowers--;
		waitForStep();
	}

	/** Tosses a flower one space ahead.
	 * If there is a net ahead of this Jeroo, it is disabled.  This method does
	 * not do anything if this jeroo is out of flowers.
	 */
    public void toss() {
		if(flowers<1)
			return;
		Location loc = getLocation().getAdjacentLocation(getDirection());
		Actor dude = getGrid().get(loc); 
		//if this is a net, disable it
		if(dude instanceof Net) {
			dude.removeSelfFromGrid();
		}
		
		flowers--;
		waitForStep();
	}

	/** Picks a flower from the current location.
	 * This method does not do anything unless this Jeroo is standing on a
	 * flower.
	 */
    public void pick() {
    	Actor dude = getActorBelow();
    	if(dude instanceof Flower) {
    		setActorBelow(null); //this removes the flower from the grid
    		flowers++;
    	}
		waitForStep();
	}

	/** Receives a flower from another Jeroo.
	 * This method is only called by give.
	 */
    private void acceptFlower() {
		flowers++;
	}

	/** Gives a flower to a Jeroo in an adjacent space.
	 */
    public void give(RelDirection relDir) {
		Location loc = getLocation(relDir);
		if(loc==null)
			return;
		Actor dude = getGrid().get(loc);
		if(dude instanceof Jeroo) {
			flowers--;
			((Jeroo)dude).acceptFlower();
		}
		waitForStep();
	}

	/** Determines if this Jeroo has a flower.
	 * @return true if the Jeroo has one or more flowers
	 */
    public boolean hasFlower() {
		return flowers>0;
	}

    /** Checks if the object in a certain direction is a Flower.
     * @param relDir the direction to check
	 */
    public boolean isFlower(RelDirection relDir) {
    	if(relDir==HERE) {
    		return getActorBelow() instanceof Flower;
    	}
    	
		Location loc = getLocation(relDir);
		if(loc==null)
			return false;
		Actor dude = getGrid().get(loc);
		return dude instanceof Flower;
	}

	/**
	 */
    public boolean isJeroo(RelDirection relDir) {
		Location loc = getLocation(relDir);
		if(loc==null)
			return false;
		Actor dude = getGrid().get(loc);
		return dude instanceof Jeroo;
	}

	/**
	 */
    public boolean isWater(RelDirection relDir) {
		Location loc = getLocation(relDir);
		if(loc==null)
			return true;
		Actor dude = getGrid().get(loc);
		return dude instanceof Water;
	}

	/**
	 */
    public boolean isNet(RelDirection relDir) {
		Location loc = getLocation(relDir);
		if(loc==null)
			return false;
		Actor dude = getGrid().get(loc);
		return dude instanceof Net;
	}
    
    /**
	 */
    public boolean isClear(RelDirection relDir) {
		Location loc = getLocation(relDir);
		if(loc==null)
			return false;
		return getGrid().get(loc) == null;
	}

	/** Determines if the Jeroo is facing a certain direction.
	 * @param dir the direction to compare to.
	 * @return true if the Jeroo is facing dir
	 */
    public boolean isFacing(Direction dir) {
		return getDirection() == dir.getCompassDirection();
	}

	@Override
	public void act() {
	}

	/** JerooWorld calls this method to set up the communication between the
	 * GUI and main thread.
	 */
	void setWorld(JerooWorld world) {
		this.world = world;
	}

	/** Waits for a step to occur in the gui thread.
	 */
	void waitForStep() {
		world.waitForStep();
	}
	
	/** Turns a Jeroo style RelDirection into a Location.
	 * @param relDir the direction
	 * @return the Location, or null if the location is not valid.
	 */
	private Location getLocation(RelDirection relDir) {
		int direction = getDirection();
		Location loc = getLocation();
		Grid<Actor> grid = getGrid();
		Location newLoc = null;
		switch(relDir) {
case LEFT:
			newLoc = loc.getAdjacentLocation(direction+Location.LEFT);
			break;
case RIGHT:
			newLoc = loc.getAdjacentLocation(direction+Location.RIGHT);
			break;
case AHEAD:
			newLoc = loc.getAdjacentLocation(direction);
			break;
case HERE:
			newLoc = loc;
			break;
		}
		if(! grid.isValid(newLoc))
			return null;
		return newLoc;
	}
	
	public String toString() {
		return  getClass().getName() + " at " + getLocation() + " facing "
        		+ getDirection() + " with " + getFlowers() + " flowers";
	}
}

