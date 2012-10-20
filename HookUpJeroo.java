import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;


/** 
 * @author Mr. McGee
 * @author Your Name Here
 */
public class HookUpJeroo extends Jeroo {
	private boolean male;
	private int myStandard;
	private int myQuality;
	private boolean dating;

	/** Creates a HookupJeroo.
	 * @param maleGender true if the Jeroo is male
	 * @param quality how attractive this jeroo is
	 * @param standard how good of a date this jeroo wants
	 */
	public HookUpJeroo(boolean maleGender, int quality, int standard) {
		//you probably do not want to edit this method.
		super(1);
		male = maleGender;
		myQuality = quality;
		myStandard = standard;
		dating = false;
	}
	
	/** If a Jeroo does not find a date, they will become more desperate, and
	 * accept a lower quality date.
	 */
	public void lowerStandard() {
		

	}

	/** Returns true if this Jeroo is male, and false otherwise.
	 */
	public boolean isMale() {


		return false;
	}

	/** Returns true if this Jeroo has a date, and false otherwise.
	 */
	public boolean hasDate() {


		return false;
	}

	/** Returns the color of this Jeroo.  Boys should be blue, and girls should
	 * be pink.
	 * @return the color
	 */
	public Color getColor() {
		
		
		return Color.YELLOW;
	}

	/** This string is displayed as a tooltip in GridWorld.
	 * @return a String containing this Jeroo's quality and standard, and whether or not this Jeroo has a date.
	 */
	public String toString() {
		
		
		return "fix toString()";
	}

	/** Checks if the other Jeroo's quality is greater than our standard.
	 * @param friend the Jeroo to compare to.
	 * @return true if this Jeroo is willing to date the other Jeroo.
	 */
	public boolean willingToDate(HookUpJeroo friend) {


		return true;
	}
	
	/** This is called when two Jeroos hook-up.
	 * @param value true if this Jeroo now has a date.
	 */
	public void setDate(boolean value) {
	
	
	}

	/** Looks at the Jeroo in front of this Jeroo and decides if they want to go out. 
	 */
	public void attemptHookUp() {
		//get the neighbor in front of this Jeroo
		Location loc = getLocation();
		loc = loc.getAdjacentLocation(getDirection());
		HookUpJeroo neighbor = (HookUpJeroo)getGrid().get(loc);

		//ask them out
		if(willingToDate(neighbor) && neighbor.willingToDate(this)) {
			//whoop!
			setDate(true);
			neighbor.setDate(true);
		}
	}

	/** Wander aimlessly and if we run into a Jeroo, attemptHookUp().
	 */
	public void searchForDate() {
		if(!hasDate()) {
			if(Math.random()<.5 && isClear(AHEAD)) {
				hop();
			} else {
				turn(RIGHT);
			}
			if(isJeroo(AHEAD)) {
				attemptHookUp();
			}
			lowerStandard();
		}
	}

	/** Runs the program.
	 * @param args ignored
	 */
	public static void main(String[] args) {
		//You do not need to edit this method.
		JerooWorld world = new JerooWorld();
		
		//puts Jeroos in the world.
		int i = 0;
		while(i<20) {
			//isMale is true if i is odd, false otherwise
			boolean isMale = (i%2==1);
			//qual is a random number between 0 and 100
			int qual = (int)(Math.random()*101);
			//stand is a random number between 0 and 100
			int stan = (int)(Math.random()*51+50);
			HookUpJeroo jeroo = new HookUpJeroo(isMale,qual,stan);
			world.add(jeroo);

			i++;
		}
		
		world.show();
		
		//This calls searchForDate() for each of the actors forever.
		while(true) {
			Grid<Actor> grid = world.getGrid();
			for(Location loc:world.getGrid().getOccupiedLocations()) {
				((HookUpJeroo)grid.get(loc)).searchForDate();
			}
		}
	}
}
