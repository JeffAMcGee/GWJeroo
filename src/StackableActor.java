import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

/** 
 * @author Jeffrey McGee
 *
 */
public abstract class StackableActor extends Actor {
	/** actorBelow is the actor that is underneath this actor.
	 * actorBelow.getGrid() will always return null.  A StackableActor cannot
	 * have anything underneath it if it is not in a grid.
	 */
	private Actor actorBelow;
	
	/**
	 * Makes a StackableActor that is not in a grid.
	 */
	public StackableActor() {
		actorBelow = null;
	}

	/** Returns the actor underneath this actor.  It will return null if it is
	 * not in a grid or if there is nothing underneath it.
	 * @return the actorBelow
	 */
	public Actor getActorBelow() {
		return actorBelow;
	}

	/** Puts an Actor underneath this Actor.  It does nothing if the Actor is not in a grid.
	 * @param actorBelow the actorBelow to set
	 */
	public void setActorBelow(Actor actorBelow) {
		if(getGrid() != null)
			this.actorBelow = actorBelow;
	}

	/* (non-Javadoc)
	 * @see info.gridworld.actor.Actor#act()
	 */
	@Override
	public abstract void act();

	/* (non-Javadoc)
	 * @see info.gridworld.actor.Actor#moveTo(info.gridworld.grid.Location)
	 */
	@Override
	public void moveTo(Location newLoc) {
		if(newLoc.equals(getLocation())) 
				return;
		
		Grid<Actor> grid = getGrid();
		Actor oldBelow = actorBelow;
		Location oldLoc = getLocation();
		Actor newBelow = grid.get(newLoc);
		
		super.moveTo(newLoc);
		
		actorBelow = newBelow;
		if(oldBelow!=null)
			oldBelow.putSelfInGrid(grid, oldLoc);
	}

	/* (non-Javadoc)
	 * @see info.gridworld.actor.Actor#putSelfInGrid(info.gridworld.grid.Grid, info.gridworld.grid.Location)
	 */
	@Override
	public void putSelfInGrid(Grid<Actor> grid, Location newLoc) {
		Actor newBelow = grid.get(newLoc);
		super.putSelfInGrid(grid, newLoc);
		actorBelow = newBelow;
	}

	/* (non-Javadoc)
	 * @see info.gridworld.actor.Actor#removeSelfFromGrid()
	 */
	@Override
	public void removeSelfFromGrid() {
		Grid<Actor> grid = getGrid();
		Location oldLoc = getLocation();
		
		super.removeSelfFromGrid();
		
		if(actorBelow != null) {
			actorBelow.putSelfInGrid(grid, oldLoc);
			actorBelow = null;
		}
	}
}
