package buildingviewer;

import java.util.*;

import processing.core.*;

public class Crane {
	
	PVector location;
	PVector hookLocation;
	List<SteelMaterial> schedule;
	List<PVector> scheduleLocations;
	int second = 0;
	float crane_speed = 3; //3 feet
	int destination = 0;
		
	public Crane(float x, float y) {
		location = new PVector(x, y, 100);
		hookLocation = location;
		scheduleLocations = new ArrayList<>();
	}
	
	public PVector getLocation() {
		return location;
	}
	
	public void setSchedule(List<SteelMaterial> schedule) {
		this.schedule = schedule;
	}
	
	public void addScheduleLocations(PVector newLocation) {
		
		scheduleLocations.add(newLocation);
	}
	
	public void draw(PApplet applet) {
		
		applet.stroke(0, 200, 0);
		applet.strokeWeight(30);
		
		// draw crane
		applet.line(location.x, location.y, location.z, location.x, location.y, location.z + 90);
		
		applet.stroke(127, 0, 0);
		applet.strokeWeight(50);
		// draw crane hook
		applet.line(hookLocation.x, hookLocation.y, hookLocation.z, hookLocation.x, hookLocation.y, hookLocation.z + 10);
		
		if (!location.equals(hookLocation)) {
			applet.stroke(153, 51, 0);
			applet.strokeWeight(10);
			// draw line connecting crane with the hook
			applet.line(location.x, location.y, location.z, hookLocation.x, hookLocation.y, hookLocation.z);
		}
		
	}
	
	public void move(BuildingViewer viewer) {
		
		PVector newLocation = scheduleLocations.get(destination); 
		PVector moving_direction = PVector.sub(newLocation, hookLocation);
		moving_direction.normalize();
		
		if ((viewer.frameCount - viewer.animationStartFrame) % 10 == 0)
		{
			second = second + 1;
			hookLocation = new PVector (hookLocation.x + crane_speed * moving_direction.x, hookLocation.y + crane_speed * moving_direction.y, hookLocation.z);
		}
		System.out.println(second + " seconds: " + hookLocation.toString());
		
		if (PVector.dist(hookLocation, newLocation) < 2) {
			if (scheduleLocations.size() <= destination + 1) // stop after all scheduled materials are installed
				viewer.toggleAnimation(); 
			else
				destination++; // get next destination location
		}
	}

}