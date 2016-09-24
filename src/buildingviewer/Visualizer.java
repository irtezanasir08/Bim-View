package buildingviewer;

import processing.core.*;

//this class is to visualize construction sequence.
public class Visualizer {
	
	public BuildingViewer viewer;
	public PApplet applet;
	public int second = 0;
	
	//crane hook moves and pick up steels, this is crane hook location
	public PVector crane_hook;
	public float crane_speed = 3; //3 feet
	
	public Visualizer (BuildingViewer _viewer)
	{
		viewer = _viewer;
		
	}
	
	public void showAnimation ()
	{
		//this is an example: (1) draw a line between a crane and a storage area
		PVector loc1 = new PVector (this.viewer.cranes.get(0).xLocation, this.viewer.cranes.get(0).yLocation, 100);
		PVector loc2 = new PVector (this.viewer.storages.get(0).xLocation, this.viewer.storages.get(0).yLocation, 100);
		
		PVector moving_direction = PVector.sub(loc2, loc1);
		moving_direction.normalize();
		
		viewer.stroke(153, 51, 0);
		viewer.strokeWeight(10);
		viewer.line(loc1.x, loc1.y, 100, loc2.x, loc2.y, 100);
		
		
		
		if (viewer.frameCount == viewer.animationStartFrame || viewer.frameCount == viewer.animationStartFrame + 1)
		{
			crane_hook = loc1;
			second = second + 1;
		}
		if ((viewer.frameCount - viewer.animationStartFrame) % 10 == 0)
		{
			second = second + 1;
			crane_hook = new PVector (loc1.x + crane_speed * moving_direction.x * second, loc1.y + crane_speed * moving_direction.y * second, 100);
		}
		System.out.println(second + " seconds: " + crane_hook.toString());
		viewer.strokeWeight(50);
		
		//move crane hook from loc1 to loc2
		//if crane hook does not pass over loc2 
		if (PVector.dist(loc1, loc2) >= PVector.dist(crane_hook, loc1))
		{
			viewer.line(crane_hook.x, crane_hook.y, crane_hook.z, crane_hook.x, crane_hook.y, crane_hook.z + 10);
		}
		
		
		
		
		
	}

}
