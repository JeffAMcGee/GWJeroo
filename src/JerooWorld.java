import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;


public class JerooWorld extends ActorWorld {
	private Thread mainThread;
	private Semaphore stepPermit;
	private Semaphore guiPermit;
	public static final int SIZE = 24;
	
	public JerooWorld() {
		super(new BoundedGrid<Actor>(SIZE,SIZE));
		stepPermit = new Semaphore(0);
		guiPermit = new Semaphore(0);
		addOccupantClass("Flower");
		addOccupantClass("Net");
		addOccupantClass("Water");
	}
	
	public JerooWorld(String file) {
		this();
		load(file);
	}
	
	/** Loads a Jeroo Environment from a file.
	 * @param file the file to load
	 */
	public void load(String file) {
		Scanner in;
		try {
			in = new Scanner(new File(file));
		} catch (FileNotFoundException e) {
			System.out.println("Could not find file "+file);
			return;
		}
		for(int r=0;r<SIZE;r++) {
			String line = in.next();
			for(int c=0;c<SIZE;c++) {
				Actor thing = null;
				switch(line.charAt(c)){
				case 'W':
					thing = new Water();
					break;
				case 'N':
					thing = new Net();
					break;
				case 'F':
					thing = new Flower();
					break;
				case '.':
					continue;
				default:
					throw new RuntimeException("Error while loading environment "+file+" at ["+r+","+c+"]\n"+line);
				}
				add(new Location(r,c),thing);
			}
		}
	}
	
	/** Waits for something to happen in the main thread.
	 */
	@Override
	public void step() {
		if(!mainThread.isAlive())
			return;
		
		guiPermit.release();
		try {
			stepPermit.tryAcquire(1,TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			if(mainThread.isAlive()) {
				throw new RuntimeException("Your program is taking too long.  It is probably stuck in an infinite loop.",e);
			}
			//The thread is dead, and the timeout expired. Ignore the exception.
		}
	}
	
	/* (non-Javadoc)
	 * @see info.gridworld.actor.ActorWorld#show()
	 */
	@Override
	public void show() {
		// TODO fix show
		super.show();
		mainThread = Thread.currentThread();
		waitForStep();
	}
	
	/** Pauses the main thread to wait for the user to click start or step.
	 */
	public void waitForStep() {
		//don't pause the gui thread.
		if(mainThread!=Thread.currentThread())
			return; 
		
		//wait for the user to press step
		stepPermit.release();
		try {
			guiPermit.acquire();
		} catch (InterruptedException e) {
			throw new RuntimeException("waitForStep was interrupted.  Go bug Mr. McGee.",e);
		}
	}

	/* (non-Javadoc)
	 * @see info.gridworld.actor.ActorWorld#add(info.gridworld.actor.Actor)
	 */
	public void add(Jeroo occupant) {
		occupant.setWorld(this);
		super.add(occupant);
	}

	/* (non-Javadoc)
	 * @see info.gridworld.actor.ActorWorld#add(info.gridworld.grid.Location, info.gridworld.actor.Actor)
	 */
	public void add(Location loc, Jeroo occupant) {
		occupant.setWorld(this);
		super.add(loc, occupant);
	}
    
}
