package buildingviewer;

import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class Crane {
	
	PVector location;
	PVector hookLocation;
	List<SteelMaterial> schedule;
	int second = 0;
	float crane_speed = 3; //3 feet
		
	public Crane(float x, float y) {
		location = new PVector(x, y, 100);
		hookLocation = location;
	}
	
	public PVector getHook_location() {
		return hookLocation;
	}

	public void setHook_location(PVector hook_location) {
		this.hookLocation = hook_location;
	}
	
	public List<SteelMaterial> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<SteelMaterial> schedule) {
		this.schedule = schedule;
	}
	
	public void draw(PApplet applet) {
		
		applet.stroke(0, 200, 0);
		applet.strokeWeight(30);
		
		applet.line(location.x, location.y, location.z, location.x, location.y, location.z + 90);
		
		applet.stroke(127, 0, 0);
		applet.strokeWeight(50);
		applet.line(hookLocation.x, hookLocation.y, hookLocation.z, hookLocation.x, hookLocation.y, hookLocation.z + 10);
		
		if (!location.equals(hookLocation)) {
			applet.stroke(153, 51, 0);
			applet.strokeWeight(10);
			applet.line(location.x, location.y, location.z, hookLocation.x, hookLocation.y, hookLocation.z);
		}
		
	}
	
	public void move(PVector newLocation, BuildingViewer viewer) {
		
		PVector moving_direction = PVector.sub(newLocation, hookLocation);
		moving_direction.normalize();
		
		if ((viewer.frameCount - viewer.animationStartFrame) % 10 == 0)
		{
			second = second + 1;
			hookLocation = new PVector (hookLocation.x + crane_speed * moving_direction.x, hookLocation.y + crane_speed * moving_direction.y, hookLocation.z);
		}
		System.out.println(second + " seconds: " + hookLocation.toString());
		
	}
	
	

}