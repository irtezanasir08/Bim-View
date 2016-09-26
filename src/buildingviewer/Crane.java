package buildingviewer;

import java.util.List;

import processing.core.PApplet;

public class Crane {
	
	float xLocation;
	float yLocation;
	List<SteelMaterial> schedule;
		
	public Crane(float x, float y) {
		xLocation = x;
		yLocation = y;
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
		
		//applet.beginShape();
		applet.line(xLocation, yLocation, 100, xLocation, yLocation, 190);
		//applet.endShape();
		
	}
	
	

}